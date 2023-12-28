package hieudx.fpoly.warehousemanager.Bill.Adapter.bill_out;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.Bill.Dao.Bill_Out_Dao;
import hieudx.fpoly.warehousemanager.Bill.Fragment.Bill_Out.Detail_Bill_Out_Fragment;
import hieudx.fpoly.warehousemanager.Bill.Model.Bill_Out;
import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.Member.Dao.User_Dao;
import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvBillBinding;

public class Bill_Out_Adapter extends RecyclerView.Adapter<Bill_Out_Adapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Bill_Out> list;
    private ArrayList<Bill_Out> mlist;
    private FragmentManager fragmentManager;

    public Bill_Out_Adapter(Context context, ArrayList<Bill_Out> list, FragmentManager fragmentManager) {
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
        Bill_Out_Dao dao = new Bill_Out_Dao(context);
        holder.binding.tvNameUser.setText(user_dao.getUserById(list.get(position).getId_user()).getName());
        holder.binding.tvDateTime.setText(list.get(position).getDate_time());
        String sum = General.formatSumVND(list.get(position).getSum());
        holder.binding.tvTotal.setText(sum);

        int status = list.get(position).getStatus();
        String state = (status == 0) ? "Done" : "Doing";
        if (status == 0) {
            holder.binding.swStatus.setChecked(true);
            holder.binding.swStatus.setClickable(false);
            holder.binding.swStatus.setFocusable(false);
            holder.binding.swStatus.setFocusableInTouchMode(false);
            holder.binding.swStatus.setLongClickable(false);
            holder.binding.cvItemBill.setForeground(ContextCompat.getDrawable(context, R.drawable.custom_bgr_bill_fade));
        } else holder.binding.swStatus.setChecked(false);
        holder.binding.tvStatus.setText(state);

        holder.binding.swStatus.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) holder.binding.cvItemBill.setOnClickListener(view -> Toast.makeText(context, "Bill was done, don't change", Toast.LENGTH_SHORT).show());
            else holder.binding.tvStatus.setText("Done");
            dao.updateStatus(0, list.get(position).getId());
//            notifyDataSetChanged();
            list.clear();
            list.addAll(dao.getAll());
            notifyDataSetChanged();
        });

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", list.get(position));
            bundle.putString("sum", sum);
            General.loadFragment(fragmentManager, new Detail_Bill_Out_Fragment(), bundle);
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
                    ArrayList<Bill_Out> listFilter = new ArrayList<>();
                    for (Bill_Out bill_out : mlist) {
                        if (bill_out.getId().toUpperCase().contains(search.toUpperCase())) {
                            listFilter.add(bill_out);
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
                list = (ArrayList<Bill_Out>) filterResults.values;
                notifyDataSetChanged();
            }
        };    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRcvBillBinding binding;

        public ViewHolder(@NonNull ItemRcvBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
