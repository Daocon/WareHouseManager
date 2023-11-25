package hieudx.fpoly.warehousemanager.adapters;

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

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.dao.Category_Dao;
import hieudx.fpoly.warehousemanager.databinding.DialogDeleteCategoryBinding;
import hieudx.fpoly.warehousemanager.databinding.DialogUpdateCategoryBinding;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvCategoryBinding;
import hieudx.fpoly.warehousemanager.models.Category;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.ViewHolder> {
    private final ArrayList<Category> list;
    private final Context context;
    Category_Dao dao;


    public Category_Adapter(Context context, ArrayList<Category> list) {
        this.context = context;
        this.list = list;
        dao = new Category_Dao(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvCategoryBinding binding = ItemRcvCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.txtMaCategory.setText("Mã loại: " + String.valueOf(list.get(position).getId()));
        holder.binding.txtNameCategory.setText(list.get(position).getName());
        Category cCategory = list.get(position);
        holder.binding.btnDeleteItemCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();

                DialogDeleteCategoryBinding deleteCategoryBinding = DialogDeleteCategoryBinding.inflate(inflater);
                builder.setView(deleteCategoryBinding.getRoot());

                Dialog dialog = builder.create();
                dialog.show();

                deleteCategoryBinding.btnOutDeleteCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                deleteCategoryBinding.btnConfilmDeleteCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int check = dao.deleteCategory(list.get(holder.getAdapterPosition()).getId());
                        switch (check) {
                            case 1:
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(dao.getListCategory());
                                notifyDataSetChanged();
                                dialog.dismiss();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                break;
                            case -1:
                                Toast.makeText(context, "Không thể xóa vì loại sản phẩm này còn sản phẩm", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        });
        holder.binding.linItemCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();

                DialogUpdateCategoryBinding updateCategoryBinding = DialogUpdateCategoryBinding.inflate(inflater);
                builder.setView(updateCategoryBinding.getRoot());

                Dialog dialog = builder.create();
                dialog.show();
                updateCategoryBinding.btnComfilmUpdateCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nameCategory = updateCategoryBinding.edtUpdateTenLoaiSp.getText().toString();
                        cCategory.setName(nameCategory);
                        if (nameCategory.isEmpty()) {
                            updateCategoryBinding.tbUpdateCategory.setError("Không được để trông");

                        } else if (dao.updateCategory(cCategory)) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(dao.getListCategory());
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                updateCategoryBinding.btnHuyUpdateItemCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateCategoryBinding.edtUpdateTenLoaiSp.setText("");
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
        private ItemRcvCategoryBinding binding;

        public ViewHolder(@NonNull ItemRcvCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public boolean checkAdd() {
        return getItemCount() == 0;
    }
}
