package hieudx.fpoly.warehousemanager.Supplier;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.databinding.DialogDeleteSupplierBinding;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvSupplierBinding;

public class Supplier_Adapter extends RecyclerView.Adapter<Supplier_Adapter.ViewHolder> {
    private ArrayList<Supplier> list;
    private Context context;
    private Supplier_Dao dao;

    public Supplier_Adapter(ArrayList<Supplier> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new Supplier_Dao(context);
    }

    public interface OnItemClick {
        void onItemClick(int position);
    }

//    private Product_Adapter.OnItemClick mListener;

//    public void setOnItemClick(Product_Adapter.OnItemClick listener) {
//        mListener = listener;
//    }

    public Supplier getSupplierAtPosition(int position) {
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvSupplierBinding binding = ItemRcvSupplierBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Supplier supplier = list.get(position);
        holder.binding.txtNameSupplier.setText("Tên: " + supplier.getName());
        holder.binding.txtMaSupplier.setText("Mã: " + String.valueOf(supplier.getId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (mListener != null) {
//                    mListener.onItemClick(holder.getAdapterPosition());
//
//                }
            }
        });

        holder.binding.btnDeleteItemSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();

                DialogDeleteSupplierBinding supplierBinding = DialogDeleteSupplierBinding.inflate(inflater);
                builder.setView(supplierBinding.getRoot());

                Dialog dialog = builder.create();
                dialog.show();

                supplierBinding.btnConfilmDeleteSupplier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int check = dao.deleteSupplier(list.get(holder.getAdapterPosition()).getId());
                        switch (check) {
                            case 1:
                                General.showSuccessPopup(context, "Thành công", "Bạn đã xóa thành công", new OnDialogButtonClickListener() {
                                    @Override
                                    public void onDismissClicked(Dialog dialog) {
                                        super.onDismissClicked(dialog);
                                    }
                                });
                                list.clear();
                                list.addAll(dao.getAll());
                                notifyDataSetChanged();
                                dialog.dismiss();
                                break;
                            case 0:
                                General.showSuccessPopup(context, "Thất bại", "Bạn đã xóa thất bại", new OnDialogButtonClickListener() {
                                    @Override
                                    public void onDismissClicked(Dialog dialog) {
                                        super.onDismissClicked(dialog);
                                    }
                                });
                                dialog.dismiss();
                                break;
                            case -1:
                                General.showSuccessPopup(context, "Thất bại", "Không thể xóa vì nhà cung cấp này đã có sản phẩm", new OnDialogButtonClickListener() {
                                    @Override
                                    public void onDismissClicked(Dialog dialog) {
                                        super.onDismissClicked(dialog);
                                    }
                                });
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                });

                supplierBinding.btnOutDeleteSupplier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRcvSupplierBinding binding;

        public ViewHolder(ItemRcvSupplierBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public boolean isListEmpty() {
        return list.isEmpty();
    }
}
