package hieudx.fpoly.warehousemanager.Supplier.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.Supplier.Dao.Supplier_Dao;
import hieudx.fpoly.warehousemanager.Supplier.Model.Supplier;
import hieudx.fpoly.warehousemanager.databinding.BotSheetDeliveryDetailBinding;
import hieudx.fpoly.warehousemanager.databinding.DialogAddEditDeliveryBinding;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvCategoryBinding;

public class Supplier_Adapter extends RecyclerView.Adapter<Supplier_Adapter.ViewHolder> implements Filterable {
    private ArrayList<Supplier> list;
    private ArrayList<Supplier> mlist;
    private Context context;
    private Supplier_Dao dao;
    private LayoutInflater layoutInflater;

    public Supplier_Adapter(ArrayList<Supplier> list, Context context, LayoutInflater layoutInflater) {
        this.list = list;
        this.mlist = list;
        this.context = context;
        this.layoutInflater = layoutInflater;
        dao = new Supplier_Dao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvCategoryBinding binding = ItemRcvCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvName.setText("Tên: " + list.get(position).getName());
        holder.binding.tvId.setText("Mã số thuế: " + list.get(position).getTax_code());

        holder.binding.btnDelete.setOnClickListener(view -> {
            onDelete(list.get(holder.getAdapterPosition()));
        });

        holder.itemView.setOnClickListener(view -> {
            onShowBotSheetDetail(list.get(holder.getAdapterPosition()));
        });
    }

    private void onShowBotSheetDetail(Supplier supplier) {
        BotSheetDeliveryDetailBinding btnBinding = BotSheetDeliveryDetailBinding.inflate(layoutInflater);

        btnBinding.tvName.setText("ID: " + supplier.getId());
        btnBinding.tvName.setText("Tên nhà cung cấp: " + supplier.getName());
        btnBinding.tvPhone.setText("SĐT: " + supplier.getPhone());
        btnBinding.tvPrice.setText("Địa chỉ: " + supplier.getAddress());
        btnBinding.tvTaxCode.setText("Mã số thuế: " + supplier.getTax_code());

        btnBinding.imgCall.setOnClickListener(view -> {
            Uri number = Uri.parse("tel:" + supplier.getPhone());
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            context.startActivity(callIntent);
        });

        btnBinding.imgSms.setOnClickListener(view -> {
            Uri smsUri = Uri.parse("smsto:" + supplier.getPhone());
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
            context.startActivity(smsIntent);
        });

        btnBinding.btnEdit.setOnClickListener(view -> {
            onEdit(supplier);
        });

        General.onSettingsBotSheet(context, btnBinding);


    }

    private void onEdit(Supplier supplier) {
        DialogAddEditDeliveryBinding dialog_binding = DialogAddEditDeliveryBinding.inflate(LayoutInflater.from(context));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(dialog_binding.getRoot());
        AlertDialog dialog = builder.create();
        General.isEmptyValid(dialog_binding.edName, dialog_binding.name);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog_binding.tvTitleDialog.setText("Thêm nhà cung cấp");
        dialog_binding.tvName.setText("Tên nhà cung cấp");
        dialog_binding.tvPrice.setText("Địa chỉ");

        dialog_binding.edName.setText(supplier.getName());
        dialog_binding.edPhone.setText(supplier.getPhone());
        dialog_binding.edPrice.setText(supplier.getAddress());
        dialog_binding.edTaxCode.setText(supplier.getTax_code());

        General.isEmptyValid(dialog_binding.edName, dialog_binding.name);
        General.isEmptyValid(dialog_binding.edPhone, dialog_binding.phone);
        General.isEmptyValid(dialog_binding.edPrice, dialog_binding.price);
        General.isEmptyValid(dialog_binding.edTaxCode, dialog_binding.taxCode);

        dialog.show();

        dialog_binding.btnAdd.setOnClickListener(view -> {
            String name = dialog_binding.edName.getText().toString().trim();
            String phone = dialog_binding.edPhone.getText().toString().trim();
            String add = dialog_binding.edPrice.getText().toString().trim();
            String taxCode = dialog_binding.edTaxCode.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(add) || TextUtils.isEmpty(taxCode)) {
                Toast.makeText(context, "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
            } else {
                General.validPhone(phone, dialog_binding.phone);
                General.isContainSpecialChar(name, dialog_binding.name);
                General.isContainSpecialChar(add, dialog_binding.price);
                General.isContainSpecialChar(taxCode, dialog_binding.taxCode);
                if (dialog_binding.name.getError() == null
                        && dialog_binding.phone.getError() == null
                        && dialog_binding.price.getError() == null
                        && dialog_binding.taxCode.getError() == null) {
                    if (!dao.isExistSupplier(name)) {
                        dialog_binding.name.setError(null);
                        supplier.setName(name);
                        supplier.setPhone(phone);
                        supplier.setAddress(add);
                        supplier.setTax_code(taxCode);
                        if (dao.insert(supplier) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dialog_binding.name.setError("Tên nhà cung cấp đã tồn tại");
                    }
                }
            }
        });
        dialog_binding.imgClose.setOnClickListener(view -> dialog.dismiss());
    }

    private void onDelete(Supplier supplier) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa");
        builder.setNegativeButton("Có", (dialog, which) -> {
            switch (dao.deleteSupplier(supplier.getId())) {
                case 0:
                    General.showSuccessPopup(context, "Thành công", "Bạn đã xóa thành công", new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
                    dialog.dismiss();
                    break;
                case 1:
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(dao.getAll());
                    notifyDataSetChanged();
                    dialog.dismiss();
                    break;
                case -1:
                    Toast.makeText(context, "Không thể xóa vì nhà cung cấp đã có sản phẩm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
            }
            dialog.dismiss();
        });

        builder.setPositiveButton("Không", null);
        builder.create();
        builder.show();
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if (TextUtils.isEmpty(search)) {
                    list = mlist;
                } else {
                    ArrayList<Supplier> listFilter = new ArrayList<>();
                    for (Supplier sup : mlist) {
                        if (sup.getName().toUpperCase().contains(search.toUpperCase())) {
                            listFilter.add(sup);
                        }
                    }
                    list = listFilter;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<Supplier>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRcvCategoryBinding binding;

        public ViewHolder(ItemRcvCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
