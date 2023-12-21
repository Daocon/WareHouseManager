package hieudx.fpoly.warehousemanager.Bill.Adapter.bill_in;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.Bill.Fragment.Bill_In.Detail_Bill_In_Fragment;
import hieudx.fpoly.warehousemanager.Bill.Model.Bill_In;
import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.Member.Dao.User_Dao;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvBillBinding;

public class Bill_In_Adapter extends RecyclerView.Adapter<Bill_In_Adapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Bill_In> list;
    private ArrayList<Bill_In> mlist;
    private FragmentManager fragmentManager;

    public Bill_In_Adapter(Context context, ArrayList<Bill_In> list, FragmentManager fragmentManager) {
        this.context = context;
        this.list = list;
        this.mlist = list;
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
        String sum = General.formatSumVND(list.get(position).getSum());
        holder.binding.tvTotal.setText(sum+"đ");

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", list.get(position));
            bundle.putString("sum", sum);
            General.loadFragment(fragmentManager, new Detail_Bill_In_Fragment(), bundle);
        });
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
                    ArrayList<Bill_In> listFilter = new ArrayList<>();
                    for (Bill_In bill_in : mlist) {
                        if (bill_in.getId().toUpperCase().contains(search.toUpperCase())) {
                            listFilter.add(bill_in);
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
                list = (ArrayList<Bill_In>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRcvBillBinding binding;
        public ViewHolder(@NonNull ItemRcvBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
