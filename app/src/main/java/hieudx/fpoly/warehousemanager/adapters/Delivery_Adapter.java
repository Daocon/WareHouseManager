package hieudx.fpoly.warehousemanager.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.dao.Delivery_Dao;
import hieudx.fpoly.warehousemanager.databinding.DialogDeleteDeliveryBinding;
import hieudx.fpoly.warehousemanager.databinding.DialogUpdateCategoryBinding;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvDeliveryBinding;
import hieudx.fpoly.warehousemanager.models.Delivery;
import hieudx.fpoly.warehousemanager.models.Product;

public class Delivery_Adapter extends RecyclerView.Adapter<Delivery_Adapter.ViewHolder> {
    private ArrayList<Delivery> list;
    private Context context;
    private Delivery_Dao dao;

    public Delivery_Adapter(ArrayList<Delivery> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new Delivery_Dao(context);
    }
    public interface OnItemClick{
        void onItemClick(int position);
    }
    private Product_Adapter.OnItemClick mListener;
    public void setOnItemClick(Product_Adapter.OnItemClick listener){
        mListener = listener;
    }
    public Delivery getDeliveryAtPosition(int position) {
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvDeliveryBinding binding = ItemRcvDeliveryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Delivery delivery = list.get(position);
        holder.binding.txtNameDelivery.setText("Tên đvvc: "+ list.get(position).getName());
        holder.binding.txtMaDelivery.setText("Mã: " + String.valueOf(list.get(position).getId()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mListener != null){
                    mListener.onItemClick(holder.getAdapterPosition());


                }
            }
        });
        holder.binding.btnDeleteItemDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();

                DialogDeleteDeliveryBinding deliveryBinding = DialogDeleteDeliveryBinding.inflate(inflater);
                builder.setView(deliveryBinding.getRoot());

                Dialog dialog = builder.create();
                dialog.show();
                deliveryBinding.btnOutDeleteDelivery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                deliveryBinding.btnConfilmDeleteDelivery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int check = dao.deleteDelivery(list.get(holder.getAdapterPosition()).getId());
                        switch (check) {
                            case 1:
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(dao.getListDelivery());
                                notifyDataSetChanged();
                                dialog.dismiss();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                break;
                            case -1:
                                Toast.makeText(context, "Không thể xóa vì đvvc này có trong phiếu xuất", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRcvDeliveryBinding binding;

        public ViewHolder(ItemRcvDeliveryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public boolean isListEmpty() {
        return list.isEmpty();
    }
}
