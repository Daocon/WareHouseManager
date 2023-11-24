package hieudx.fpoly.warehousemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.dao.Product_Dao;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvProductBinding;
import hieudx.fpoly.warehousemanager.models.Product;

public class Product_Adapter extends RecyclerView.Adapter<Product_Adapter.Viewholder> {
    private final ArrayList<Product> list;
    private final Context context;
    Product_Dao dao;


    public Product_Adapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
        dao = new Product_Dao(context);
    }
    public interface OnItemClick{
        void onItemClick(int position);
    }
    private OnItemClick mListener;
    public void setOnItemClick(OnItemClick listener){
        mListener = listener;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvProductBinding binding = ItemRcvProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.binding.txtNameProduct.setText(list.get(position).getName());
        holder.binding.txtMaProduct.setText("Mã :"+String.valueOf(list.get(position).getId()));
        holder.binding.txtGiaProduct.setText("Giá: "+String.valueOf(list.get(position).getPrice())+"đ");
        holder.binding.txtTonKhoProduct.setText("Số lượng: "+String.valueOf(list.get(position).getQuantity()));
        Picasso.get().load(list.get(position).getImg()).into(holder.binding.imgProduct);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ItemRcvProductBinding binding;

        public Viewholder(ItemRcvProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public boolean isListEmpty() {
        return list.isEmpty();
    }
}