package hieudx.fpoly.warehousemanager.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.dao.User_Dao;
import hieudx.fpoly.warehousemanager.databinding.ItemRycMemberBinding;
import hieudx.fpoly.warehousemanager.models.User;

public class User_Adapter extends RecyclerView.Adapter<User_Adapter.viewholer> {
    private final Context context;
    private final ArrayList<User> listUser;
    User_Dao userDao;

    public User_Adapter(Context context, ArrayList<User> listUser) {
        this.context = context;
        this.listUser = listUser;
        userDao = new User_Dao(context);
    }

    @NonNull
    @Override
    public viewholer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRycMemberBinding binding = ItemRycMemberBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new viewholer(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholer holder, int position) {
        holder.binding.nameMember.setText(listUser.get(position).getName());
        holder.binding.phoneMember.setText(listUser.get(position).getPhone());
        holder.binding.emailMember.setText(listUser.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class viewholer extends RecyclerView.ViewHolder {
        private final ItemRycMemberBinding binding;

        public viewholer(ItemRycMemberBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
