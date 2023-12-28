package hieudx.fpoly.warehousemanager.Category.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.Category.Dao.Category_Dao;
import hieudx.fpoly.warehousemanager.Category.Model.Category;
import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.databinding.DialogAddEditCategoryBinding;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvCategoryBinding;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.ViewHolder> implements Filterable {
    private ArrayList<Category> list;
    private ArrayList<Category> mlist;
    private Context context;
    private Category_Dao dao;
    private FragmentManager fragmentManager;

    public Category_Adapter(Context context, ArrayList<Category> list, FragmentManager fragmentManager) {
        this.context = context;
        this.list = list;
        this.mlist = list;
        dao = new Category_Dao(context);
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvCategoryBinding binding = ItemRcvCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvId.setText("Mã loại: " + list.get(position).getId());
        holder.binding.tvName.setText(list.get(position).getName());

        holder.binding.btnDelete.setOnClickListener(view -> onDeleteCategory(list.get(position)));
        holder.itemView.setOnClickListener(view -> onEditCategory(list.get(position)));

    }

    private void onEditCategory(Category cat) {
        DialogAddEditCategoryBinding dialog_binding = DialogAddEditCategoryBinding.inflate(LayoutInflater.from(context));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(dialog_binding.getRoot());
        AlertDialog dialog = builder.create();
        General.isEmptyValid(dialog_binding.edName, dialog_binding.name);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog_binding.btnAdd.setText("sửa");
        dialog_binding.tvTitleDialog.setText("Chi tiết thể loại");
        dialog_binding.edName.setText(cat.getName());
        dialog.show();

        dialog_binding.btnAdd.setOnClickListener(view -> {
            String name = dialog_binding.edName.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(context, "Hãy nhập tên thể loại", Toast.LENGTH_SHORT).show();
            } else {
                General.isContainSpecialChar(name, dialog_binding.name);
                if (dialog_binding.name.getError() == null) {
                    if (!dao.isExistCategory(name)) {
                        dialog_binding.name.setError(null);
                        cat.setName(name);
                        if (dao.editCategory(cat) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(dao.getListCategory());
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dialog_binding.name.setError("Tên thể loại đã tồn tại");
                    }
                }
            }
        });

        dialog_binding.imgClose.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }

    private void onDeleteCategory(Category cat) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa");
        builder.setNegativeButton("Có", (dialog, which) -> {
            switch (dao.deleteCategory(cat.getId())) {
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
                    General.showSuccessPopup(context, "Thành công", "Bạn đã xóa thành công", new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(android.app.Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
                    list.clear();
                    list.addAll(dao.getListCategory());
                    notifyDataSetChanged();
                    dialog.dismiss();
                    break;
                case -1:
                    General.showFailurePopup(context, "Thất bại", "Tồn tại sản phẩm của thể loại này. Không được xóa", new OnDialogButtonClickListener() {
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
                    ArrayList<Category> listFilter = new ArrayList<>();
                    for (Category cat : mlist) {
                        if (cat.getName().toUpperCase().contains(search.toUpperCase())) {
                            listFilter.add(cat);
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
                list = (ArrayList<Category>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRcvCategoryBinding binding;

        public ViewHolder(@NonNull ItemRcvCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
