package hieudx.fpoly.warehousemanager.fragments.member;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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
    private ArrayList<User> list = new ArrayList<>();
    public Member_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMemberBinding.inflate(inflater,container,false);
        view = binding.getRoot();
        userDao = new User_Dao(getActivity());
        list = userDao.getAllUser();
        RecyclerView rcvUser = binding.rycMember;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvUser.setLayoutManager(linearLayoutManager);
        User_Adapter adapter = new User_Adapter(getActivity(),list);
        rcvUser.setAdapter(adapter);


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
        binding.btSheetMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        return view;
    }



    private void showDialog() {

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        BottomSheetLayoutBinding layoutBinding = BottomSheetLayoutBinding.inflate(getLayoutInflater());
        dialog.setContentView(layoutBinding.getRoot());

        layoutBinding.radioGroup.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == R.id.rbAZ){
                Toast.makeText(getContext(), "Sắp xếp từ A - Z", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Sắp xếp từ Z - A", Toast.LENGTH_SHORT).show();
            }
        }));

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}