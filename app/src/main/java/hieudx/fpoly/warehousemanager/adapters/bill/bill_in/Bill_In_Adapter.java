package hieudx.fpoly.warehousemanager.adapters.bill.bill_in;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.dao.Bill.Bill_In_Dao;
import hieudx.fpoly.warehousemanager.dao.User_Dao;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvBillBinding;
import hieudx.fpoly.warehousemanager.fragments.Bill.Bill_In.Detail_Bill_In_Fragment;
import hieudx.fpoly.warehousemanager.models.bill.Bill_In;

public class Bill_In_Adapter extends RecyclerView.Adapter<Bill_In_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Bill_In> list;
    private FragmentManager fragmentManager;

    public Bill_In_Adapter(Context context, ArrayList<Bill_In> list, FragmentManager fragmentManager) {
        this.context = context;
        this.list = list;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvBillBinding binding = ItemRcvBillBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvIdBill.setText(list.get(position).getId());
        User_Dao user_dao = new User_Dao(context);
        holder.binding.tvNameUser.setText(user_dao.getUserById(list.get(position).getId_user()).getName());
        holder.binding.tvDateTime.setText(list.get(position).getDate_time());
        Bill_In_Dao bill_in_dao = new Bill_In_Dao(context);
        holder.binding.tvTotal.setText(bill_in_dao.getSumTotal(list.get(position).getId()));

        holder.itemView.setOnClickListener(view -> {
            Fragment fragment = new Detail_Bill_In_Fragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", list.get(position));
            bundle.putString("sumTotal", bill_in_dao.getSumTotal(list.get(position).getId()));
            fragment.setArguments(bundle);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frag_container_main, fragment).addToBackStack(null).commit();
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
        private final ItemRcvBillBinding binding;

        public ViewHolder(@NonNull ItemRcvBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
