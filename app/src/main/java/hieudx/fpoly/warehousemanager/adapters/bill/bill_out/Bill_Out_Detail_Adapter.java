package hieudx.fpoly.warehousemanager.adapters.bill.bill_out;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.dao.Product_Dao;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvDetailBillInBinding;
import hieudx.fpoly.warehousemanager.models.bill.Bill_out_detail;

public class Bill_Out_Detail_Adapter extends RecyclerView.Adapter<Bill_Out_Detail_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Bill_out_detail> list;

    public Bill_Out_Detail_Adapter(Context context, ArrayList<Bill_out_detail> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvDetailBillInBinding binding = ItemRcvDetailBillInBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int index = position + 1;
        Product_Dao product_dao = new Product_Dao(context);
        String nameProduct = product_dao.getProductById(list.get(position).getId_product()).getName();
        holder.binding.tvNameProduct.setText(index + ". " + list.get(position).getId_product() + " - " + nameProduct);
        holder.binding.tvPrice.setText(list.get(position).getPrice() + " x " + list.get(position).getQuantity());
        holder.binding.tvTotal.setText(list.get(position).getTotal() + "");
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRcvDetailBillInBinding binding;

        public ViewHolder(@NonNull ItemRcvDetailBillInBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
