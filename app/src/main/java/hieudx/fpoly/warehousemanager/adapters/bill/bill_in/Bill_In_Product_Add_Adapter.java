package hieudx.fpoly.warehousemanager.adapters.bill.bill_in;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.databinding.ItemRcvProductBillBinding;
import hieudx.fpoly.warehousemanager.models.Product;

public class Bill_In_Product_Add_Adapter extends RecyclerView.Adapter<Bill_In_Product_Add_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> list;

    public Bill_In_Product_Add_Adapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvProductBillBinding binding = ItemRcvProductBillBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvNameProduct.setText(list.get(position).getName());
        holder.binding.cbAddProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(context, "Checked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRcvProductBillBinding binding;

        public ViewHolder(@NonNull ItemRcvProductBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
