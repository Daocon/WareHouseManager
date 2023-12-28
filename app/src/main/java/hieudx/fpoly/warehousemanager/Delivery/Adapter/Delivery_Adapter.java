package hieudx.fpoly.warehousemanager.Delivery.Adapter;

import android.app.AlertDialog;
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

import hieudx.fpoly.warehousemanager.Delivery.Dao.Delivery_Dao;
import hieudx.fpoly.warehousemanager.Delivery.Model.Delivery;
import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.databinding.BotSheetDeliveryDetailBinding;
import hieudx.fpoly.warehousemanager.databinding.DialogAddEditDeliveryBinding;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvCategoryBinding;

public class Delivery_Adapter extends RecyclerView.Adapter<Delivery_Adapter.ViewHolder> implements Filterable {
    private ArrayList<Delivery> list;
    private ArrayList<Delivery> mlist;
    private Context context;
    private Delivery_Dao dao;
    private LayoutInflater layoutInflater;


    public Delivery_Adapter(ArrayList<Delivery> list, Context context, LayoutInflater layoutInflater) {
        this.list = list;
        this.mlist = list;
        this.context = context;
        dao = new Delivery_Dao(context);
        this.layoutInflater = layoutInflater;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvCategoryBinding binding = ItemRcvCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvId.setText("Mã số thuế: " + list.get(position).getTax_code());
        holder.binding.tvName.setText("Tên: " + list.get(position).getName());

        holder.itemView.setOnClickListener(view -> {
            onShowBotSheetDetail(list.get(holder.getAdapterPosition()));
        });


        holder.binding.btnDelete.setOnClickListener(view -> {
            onDelete(list.get(holder.getAdapterPosition()));
        });
    }

    private void onEdit(Delivery delivery) {
        DialogAddEditDeliveryBinding dialog_binding = DialogAddEditDeliveryBinding.inflate(LayoutInflater.from(context));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(dialog_binding.getRoot());
        AlertDialog dialog = builder.create();
        General.isEmptyValid(dialog_binding.edName, dialog_binding.name);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog_binding.btnAdd.setText("sửa");
        dialog_binding.tvTitleDialog.setText("sửa đơn vị vận chuyển");
        dialog_binding.edName.setText(delivery.getName());
        dialog_binding.edPrice.setText(delivery.getPrice() + "");
        dialog_binding.edPhone.setText(delivery.getPhone());
        dialog_binding.edTaxCode.setText(delivery.getTax_code());
        dialog.show();

        dialog_binding.btnAdd.setOnClickListener(view -> {
            String name = dialog_binding.edName.getText().toString().trim();
            String phone = dialog_binding.edPhone.getText().toString().trim();
            String price = dialog_binding.edPrice.getText().toString().trim();
            String taxCode = dialog_binding.edTaxCode.getText().toString().trim();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(price) || TextUtils.isEmpty(taxCode)) {
                Toast.makeText(context, "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
            } else {
                General.isContainSpecialChar(name, dialog_binding.name);
                General.validPhone(phone, dialog_binding.phone);

                if (price.contains(" "))
                    dialog_binding.price.setError("Không được chứa khoảng trắng");
                else if (price.matches("[a-zA-Z]+"))
                    dialog_binding.price.setError("Không được nhập chữ");
                else if (price.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?~].*"))
                    dialog_binding.price.setError("Không được chứa ký tự đặc biệt");
                else if (Double.parseDouble(price) < 0)
                    dialog_binding.price.setError("Giá phải > 0");
                else dialog_binding.price.setError(null);

                General.isContainSpecialChar(taxCode, dialog_binding.taxCode);
                if (dialog_binding.name.getError() == null
                        && dialog_binding.phone.getError() == null
                        && dialog_binding.price.getError() == null
                        && dialog_binding.taxCode.getError() == null) {
                    if (!dao.isExistDelivery(name)) {
                        dialog_binding.name.setError(null);
                        delivery.setName(name);
                        delivery.setPhone(phone);
                        delivery.setPrice(Double.parseDouble(price));
                        delivery.setTax_code(taxCode);
                        if (dao.updateDelivery(delivery) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dialog_binding.name.setError("Tên đvvc đã tồn tại");
                    }
                }
            }
        });
        dialog_binding.imgClose.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }

    private void onDelete(Delivery delivery) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa");
        builder.setNegativeButton("Có", (dialog, which) -> {
            switch (dao.deleteDelivery(delivery.getId())) {
                case 0:
                    General.showFailurePopup(context, "Thất bại", "Xóa thất bại", new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(android.app.Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
                    dialog.dismiss();
                    break;
                case 1:
                    General.showSuccessPopup(context, "Thành công", "Bạn đã xóa đvvc thành công", new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(android.app.Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
                    list.clear();
                    list.addAll(dao.getListDelivery());
                    notifyDataSetChanged();
                    dialog.dismiss();
                    break;
                case -1:
                    General.showFailurePopup(context, "Thất bại", "Tồn tại đvvc này trong phiếu xuất. Không được xóa", new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(android.app.Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
                    dialog.dismiss();
                    break;
            }
            dialog.dismiss();
        });

        builder.setPositiveButton("Không", null);
        builder.create();
        builder.show();
    }

    private void onShowBotSheetDetail(Delivery delivery) {
        BotSheetDeliveryDetailBinding btnBinding = BotSheetDeliveryDetailBinding.inflate(layoutInflater);

        btnBinding.tvName.setText("ID: " + delivery.getId());
        btnBinding.tvName.setText("Tên đvvc: " + delivery.getName());
        btnBinding.tvPhone.setText("SĐT: " + delivery.getPhone());
        btnBinding.tvPrice.setText("Giá cước: " + delivery.getPrice());
        btnBinding.tvTaxCode.setText("Mã số thuế: " + delivery.getTax_code());

        btnBinding.imgCall.setOnClickListener(view -> {
            Uri number = Uri.parse("tel:" + delivery.getPhone());
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            context.startActivity(callIntent);
        });

        btnBinding.imgSms.setOnClickListener(view -> {
            Uri smsUri = Uri.parse("smsto:" + delivery.getPhone());
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
            context.startActivity(smsIntent);
        });

        btnBinding.btnEdit.setOnClickListener(view -> {
            onEdit(delivery);
        });

        General.onSettingsBotSheet(context, btnBinding);
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
                    ArrayList<Delivery> listFilter = new ArrayList<>();
                    for (Delivery deli : mlist) {
                        if (deli.getName().toUpperCase().contains(search.toUpperCase())) {
                            listFilter.add(deli);
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
                list = (ArrayList<Delivery>) filterResults.values;
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
