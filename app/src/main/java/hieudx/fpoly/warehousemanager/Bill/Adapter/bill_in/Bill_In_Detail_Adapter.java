package hieudx.fpoly.warehousemanager.Bill.Adapter.bill_in;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.Product.Dao.Product_Dao;
import hieudx.fpoly.warehousemanager.Supplier.Dao.Supplier_Dao;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvDetailBillInBinding;
import hieudx.fpoly.warehousemanager.Bill.Model.Bill_in_detail;

public class Bill_In_Detail_Adapter extends RecyclerView.Adapter<Bill_In_Detail_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Bill_in_detail> list;

    public Bill_In_Detail_Adapter(Context context, ArrayList<Bill_in_detail> list) {
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
        Supplier_Dao supplier_dao = new Supplier_Dao(context);
        int sup_id = product_dao.getProductById(list.get(position).getId_product()).getId_supplier();
        int id_cat = product_dao.getProductById(list.get(position).getId_product()).getId_category();
        Log.d("sup_id", "onBindViewHolder: "+sup_id);
        Log.d("cat_id", "onBindViewHolder: "+id_cat);


        String nameSupplier = supplier_dao.getSupplierById(sup_id).getName();
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
