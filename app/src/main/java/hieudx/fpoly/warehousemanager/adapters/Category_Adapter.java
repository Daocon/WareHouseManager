package hieudx.fpoly.warehousemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.databinding.ItemRcvCategoryBinding;
import hieudx.fpoly.warehousemanager.models.Category;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.ViewHolder> {
private final ArrayList<Category> list;
private final Context context;

    public Category_Adapter(Context context,ArrayList<Category> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvCategoryBinding binding = ItemRcvCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.txtMaCategory.setText("Mã loại: "+String.valueOf(list.get(position).getId()));
        holder.binding.txtNameCategory.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
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
