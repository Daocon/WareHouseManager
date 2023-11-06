package hieudx.fpoly.warehousemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.dao.Staff_Dao;
import hieudx.fpoly.warehousemanager.dao.User_Dao;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvBillInBinding;
import hieudx.fpoly.warehousemanager.databinding.ItemRycMemberBinding;
import hieudx.fpoly.warehousemanager.models.Bill_In;

public class Bill_In_Adapter extends RecyclerView.Adapter<Bill_In_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Bill_In> list;

    public Bill_In_Adapter(Context context, ArrayList<Bill_In> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvBillInBinding binding = ItemRcvBillInBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvIdBillIn.setText(list.get(position).getId() + "");
        User_Dao user_dao = new User_Dao(context);
        holder.binding.tvNameUser.setText(user_dao.getUserById(list.get(position).getId_user()).getName());
        holder.binding.tvDateTime.setText(list.get(position).getDate_time());
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
        private final ItemRcvBillInBinding binding;

        public ViewHolder(@NonNull ItemRcvBillInBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
