package hieudx.fpoly.warehousemanager.adapters;

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

import hieudx.fpoly.warehousemanager.dao.Staff_Dao;
import hieudx.fpoly.warehousemanager.databinding.DialogDeleteStaffBinding;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvStaffBinding;
import hieudx.fpoly.warehousemanager.models.Staff;

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
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            DialogDeleteStaffBinding dialogDeleteStaffBinding = DialogDeleteStaffBinding.inflate(inflater);
            builder.setView(dialogDeleteStaffBinding.getRoot());

            Dialog dialog = builder.create();
            dialog.show();

            dialogDeleteStaffBinding.btnConfilmDeleteStaff.setOnClickListener(v1 -> {
                int check  = dao.deleteStaff(list.get(holder.getAdapterPosition()).getId());
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
            });

            dialogDeleteStaffBinding.btnOutDeleteStaff.setOnClickListener(v1 -> {
                dialog.dismiss();
            });

        });
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
