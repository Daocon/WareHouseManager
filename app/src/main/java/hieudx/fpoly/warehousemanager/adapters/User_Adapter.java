package hieudx.fpoly.warehousemanager.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.dao.User_Dao;
import hieudx.fpoly.warehousemanager.databinding.ItemDialogDetailMemberBinding;
import hieudx.fpoly.warehousemanager.databinding.ItemRycMemberBinding;
import hieudx.fpoly.warehousemanager.models.User;

public class User_Adapter extends RecyclerView.Adapter<User_Adapter.viewholer> {
    private final Context context;
    private final ArrayList<User> listUser;
    User_Dao userDao;
    private AlertDialog detailDialog;

    public User_Adapter(Context context, ArrayList<User> listUser) {
        this.context = context;
        this.listUser = listUser;
        userDao = new User_Dao(context);
    }

    @NonNull
    @Override
    public viewholer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemRycMemberBinding binding = ItemRycMemberBinding.inflate(inflater, parent, false);
        return new viewholer(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull viewholer holder, int position) {
        User muser = listUser.get(position);
        int role = muser.getRole();
        if (role != 0) {
            holder.binding.getRoot().setVisibility(View.VISIBLE);

            Picasso.get().load(muser.getAvatar()).into(holder.binding.imgMember);
            holder.binding.nameMember.setText(muser.getName());
            holder.binding.phoneMember.setText(muser.getPhone());
            holder.binding.emailMember.setText(muser.getEmail());

            holder.binding.switchLimit.setChecked(muser.getRole() == -1);
            holder.binding.switchLimit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        userDao.updateUserRoleById(muser.getId(), -1);
                        Toast.makeText(context, muser.getName() + " đã bị hạn chế", Toast.LENGTH_SHORT).show();
                    } else {
                        userDao.updateUserRoleById(muser.getId(), 1);
                        Toast.makeText(context, muser.getName() + " đã bỏ hạn chế", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.binding.cardViewMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnClickGoToDetail(muser);
                }
            });

        } else {
            holder.binding.getRoot().setVisibility(View.GONE);
        }
    }

    private void OnClickGoToDetail(User muser) {
        // Sử dụng ViewBinding để inflate layout
        ItemDialogDetailMemberBinding binding = ItemDialogDetailMemberBinding.inflate(LayoutInflater.from(context));
        View view = binding.getRoot();

        // Khởi tạo AlertDialog.Builder với view đã inflate
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        detailDialog = builder.create();
        detailDialog.setCanceledOnTouchOutside(false);
        detailDialog.show();
        detailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Picasso.get().load(muser.getAvatar()).into(binding.imgAvatar);
        binding.txtHoVaTen.setText(muser.getName());
        binding.txtEmail.setText(muser.getEmail());
        binding.txtPhone.setText(muser.getPhone());

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = userDao.deleteUser(muser.getId());
                        switch (check) {
                            case 1:
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                listUser.clear();
                                listUser.addAll(userDao.getAllUser());
                                notifyDataSetChanged();
                                dialog.dismiss();
                                detailDialog.dismiss();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Không được xóa", Toast.LENGTH_SHORT).show();
                                break;
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
                detailDialog.dismiss();
            }
        });
    }


    @Override
    public int getItemCount() {
        int count = 0;
        for (User user : listUser){
            if (user.getRole() != 0){
                count++;
            }
        }
        return count;
    }

    public class viewholer extends RecyclerView.ViewHolder {
        private final ItemRycMemberBinding binding;


        public viewholer(@NonNull View itemView) {
            super(itemView);
            binding = ItemRycMemberBinding.bind(itemView);
        }
    }
}
