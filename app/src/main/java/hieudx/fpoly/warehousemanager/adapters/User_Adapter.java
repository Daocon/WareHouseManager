package hieudx.fpoly.warehousemanager.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.dao.User_Dao;
import hieudx.fpoly.warehousemanager.databinding.ItemDialogDetailMemberBinding;
import hieudx.fpoly.warehousemanager.databinding.ItemRycMemberBinding;
import hieudx.fpoly.warehousemanager.models.User;

public class User_Adapter extends RecyclerView.Adapter<User_Adapter.viewholer> {
    private final Context context;
    private final ArrayList<User> listUser;
    User_Dao userDao;
    User user;

    public User_Adapter(Context context, ArrayList<User> listUser) {
        this.context = context;
        this.listUser = listUser;
        userDao = new User_Dao(context);
    }

    @NonNull
    @Override
    public viewholer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRycMemberBinding binding = ItemRycMemberBinding.inflate(LayoutInflater.from(parent.getContext())
                , parent, false);

        return new viewholer(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholer holder, int position) {
        user = listUser.get(position);

        holder.binding.nameMember.setText(user.getName());
        holder.binding.phoneMember.setText(user.getPhone());
        holder.binding.emailMember.setText(user.getEmail());

        holder.binding.switchLimit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Toast.makeText(context, "Bật hạn chế", Toast.LENGTH_SHORT).show();
            }
        });
        holder.binding.cardViewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickGoToDetail(user);
            }
        });
    }

    private void OnClickGoToDetail(User user) {
        // Sử dụng ViewBinding để inflate layout
        ItemDialogDetailMemberBinding binding = ItemDialogDetailMemberBinding.inflate(LayoutInflater.from(context));
        View view = binding.getRoot();

        // Khởi tạo AlertDialog.Builder với view đã inflate
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        binding.txtHoVaTen.setText(user.getName());
        binding.txtEmail.setText(user.getEmail());
        binding.txtPhone.setText(user.getPhone());

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (userDao.deleteUser(user.getId())){
                            listUser.clear();
                            listUser.addAll(userDao.getAllUser());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
                builder.setNegativeButton("Không", null);
                builder.show();
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

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
