package hieudx.fpoly.warehousemanager.adapters.bill.bill_in;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.databinding.ItemRcvDetailBillInBinding;
import hieudx.fpoly.warehousemanager.models.bill.Bill_product_in;

public class Bill_In_Detail_Adapter extends RecyclerView.Adapter<Bill_In_Detail_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<Bill_product_in> list;

    public Bill_In_Detail_Adapter(Context context, ArrayList<Bill_product_in> list) {
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
        int index = position+1;
        holder.binding.tvNameProduct.setText(index + ". " + list.get(position).getId() + " - " + list.get(position).getName());
        holder.binding.tvPrice.setText(list.get(position).getPrice()+" x "+list.get(position).getQuantity());
        holder.binding.tvTotal.setText(list.get(position).getTotal()+"");
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRcvDetailBillInBinding binding;

        public ViewHolder(@NonNull ItemRcvDetailBillInBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
