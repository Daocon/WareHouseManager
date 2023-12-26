package hieudx.fpoly.warehousemanager.Staff.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.Staff.Model.Staff;
import hieudx.fpoly.warehousemanager.Staff.Dao.Staff_Dao;
import hieudx.fpoly.warehousemanager.databinding.BotSheetProductDetailBinding;
import hieudx.fpoly.warehousemanager.databinding.DialogDeleteStaffBinding;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvStaffBinding;

public class Staff_Adapter extends RecyclerView.Adapter<Staff_Adapter.ViewHolderStaff> {


    private ArrayList<Staff> list;

    private Context context;

    private Staff_Dao dao;

    public Staff_Adapter(ArrayList<Staff> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new Staff_Dao(context);
    }

    public interface OnItemClick {
        void onItemClick(int position);
    }

    private OnItemClick mListener;

    public void setOnItemClick(OnItemClick listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolderStaff onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvStaffBinding binding = ItemRcvStaffBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return  new ViewHolderStaff(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderStaff holder, int position) {
        Staff staff = list.get(position);
        holder.binding.txtNameStaff.setText("Tên: " + staff.getName());
        holder.binding.txtMaStaff.setText("Mã: " + String.valueOf(staff.getId()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mListener != null) {
                    mListener.onItemClick(holder.getAdapterPosition());

                }
            }
        });

        holder.binding.btnDeleteItemStaff.setOnClickListener(v -> {
            deleteStaff(staff);
        });

    }


    private void deleteStaff(Staff staff){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa");
        builder.setNegativeButton("Có", (dialog, which) -> {
            int check  = dao.deleteStaff(staff.getId());
            Log.i("Check",check + " ");
            switch (check){
                case 1:
                    Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(dao.getStaffList());
                    notifyDataSetChanged();
                    dialog.dismiss();
                    break;
                case 0:
                    Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                default:
                    Toast.makeText(context, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    break;
            }
            dialog.dismiss();
        });

        builder.setPositiveButton("Không", null);
        builder.create();
        builder.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderStaff extends RecyclerView.ViewHolder {

        ItemRcvStaffBinding binding;
        public ViewHolderStaff(ItemRcvStaffBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public boolean isListEmpty() {
        return list.isEmpty();
    }

    public Staff getStaffAtPosition(int position) {
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }

}
