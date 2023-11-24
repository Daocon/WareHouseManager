package hieudx.fpoly.warehousemanager.fragments.member;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.adapters.User_Adapter;
import hieudx.fpoly.warehousemanager.dao.User_Dao;
import hieudx.fpoly.warehousemanager.databinding.BottomSheetLayoutBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentMemberBinding;
import hieudx.fpoly.warehousemanager.models.User;

public class Member_Fragment extends Fragment {
    private FragmentMemberBinding binding;
    private View view;
    User_Dao userDao;
    User_Adapter adapter;
    private ArrayList<User> list;

    public Member_Fragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDao = new User_Dao(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMemberBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        for (User user : userDao.getAllUser()) {
            if (user.getRole() != 0) {
                list.add(user);
            }
        }
        RecyclerView rcvUser = binding.rycMember;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvUser.setLayoutManager(linearLayoutManager);
        adapter = new User_Adapter(getActivity(), list);
        rcvUser.setAdapter(adapter);
        checkListAndPerformActions();


        binding.btSheetMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            // Lấy dữ liệu từ Bundle
            String avatar = bundle.getString("avatar");
            String name = bundle.getString("name");
            String username = bundle.getString("username");
            String phone = bundle.getString("phone");
            String email = bundle.getString("email");
            String password = bundle.getString("password");

            // Thêm user vào danh sách hoặc cập nhật danh sách hiển thị
            User userNew = new User(username, password, name, email, phone, 1, avatar);
            if (userDao.insertUser(userNew)) {
                list.clear();
                list.addAll(userDao.getAllUser());
                adapter.notifyDataSetChanged();
                // Hiển thị thông báo
                Toast.makeText(getActivity(), "Đã thêm user thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkListAndPerformActions() {
        if (list.isEmpty()) {
            // Không có phần tử trong danh sách
            // Hiển thị viewButtonAddNumber
            binding.viewMember.setVisibility(View.GONE);
            binding.viewButtonAddNumber.setVisibility(View.VISIBLE);
            binding.btnAddMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Tạo một instance của AddMemberFragment
                    Add_Member addMember = new Add_Member();
                    // Chuyển sang AddMemberFragment
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.frag_container_main, addMember);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        } else {
            // Có phần tử trong danh sách
            // Hiển thị viewMember
            binding.viewButtonAddNumber.setVisibility(View.GONE);
            binding.viewMember.setVisibility(View.VISIBLE);
            binding.fltAddMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Tạo một instance của AddMemberFragment
                    Add_Member addMember = new Add_Member();
                    // Chuyển sang AddMemberFragment
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.frag_container_main, addMember);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }
    }

    private void showDialog() {

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        BottomSheetLayoutBinding layoutBinding = BottomSheetLayoutBinding.inflate(getLayoutInflater());
        dialog.setContentView(layoutBinding.getRoot());

        layoutBinding.radioGroup.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == R.id.rbAZ) {
                Toast.makeText(getContext(), "Sắp xếp từ A - Z", Toast.LENGTH_SHORT).show();
                Collections.sort(list, new Comparator<User>() {
                    @Override
                    public int compare(User user, User t1) {
                        return user.getName().compareToIgnoreCase(t1.getName());
                    }
                });
                adapter.notifyDataSetChanged();

            } else {
                Toast.makeText(getContext(), "Sắp xếp từ Z - A", Toast.LENGTH_SHORT).show();
                Collections.sort(list, new Comparator<User>() {
                    @Override
                    public int compare(User user, User t1) {
                        return t1.getName().compareToIgnoreCase(user.getName());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        }));

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}