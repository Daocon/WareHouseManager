package hieudx.fpoly.warehousemanager.fragments.Bill.Bill_In;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Collections;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.adapters.bill.bill_in.Bill_In_Adapter;
import hieudx.fpoly.warehousemanager.dao.Bill.Bill_In_Dao;
import hieudx.fpoly.warehousemanager.databinding.BotSheetSortBillBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentBillInBinding;
import hieudx.fpoly.warehousemanager.models.bill.Bill_In;

public class Bill_In_Fragment extends Fragment {
    private FragmentBillInBinding binding;
    private Bill_In_Dao dao;
    private ArrayList<Bill_In> list;
    private Bill_In_Adapter adapter;
    private FragmentManager fragmentManager;

    public Bill_In_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBillInBinding.inflate(inflater, container, false);

        init();
        onClickSort();

        return binding.getRoot();
    }

    private void onClickSort() {
        binding.imgSort.setOnClickListener(view -> {
            Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            BotSheetSortBillBinding btnBinding = BotSheetSortBillBinding.inflate(getLayoutInflater());
            dialog.setContentView(btnBinding.getRoot());

            btnBinding.rdGr.setOnCheckedChangeListener(((radioGroup, i) -> {
                if (i == R.id.rd_sort_asc) {
                    Collections.sort(list, (bill_in, bill_in1) -> Integer.compare(bill_in.getTotal(), bill_in1.getTotal()));
                    adapter.notifyDataSetChanged();

                } else {
                    Collections.sort(list, (bill_in, bill_in1) -> Integer.compare(bill_in1.getTotal(), bill_in.getTotal()));
                    adapter.notifyDataSetChanged();
                }
            }));

            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        });
    }

    private void init() {
        fragmentManager = requireActivity().getSupportFragmentManager();
        dao = new Bill_In_Dao(getContext());
        list = dao.getAll();
        if (!list.isEmpty()) {
            binding.btnAdd.setVisibility(View.GONE);
            binding.imgSort.setVisibility(View.VISIBLE);
            binding.rcv.setVisibility(View.VISIBLE);
            binding.fabAdd.setVisibility(View.VISIBLE);
            adapter = new Bill_In_Adapter(getContext(), list, fragmentManager);
            binding.rcv.setAdapter(adapter);
        } else {
            binding.btnAdd.setVisibility(View.VISIBLE);
            binding.imgSort.setVisibility(View.GONE);
            binding.rcv.setVisibility(View.GONE);
            binding.fabAdd.setVisibility(View.GONE);
        }

        binding.btnAdd.setOnClickListener(view -> {
            loadFragment();
        });

        binding.fabAdd.setOnClickListener(view -> {
            loadFragment();
        });
    }

    private void loadFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.frag_container_main, new Add_Bill_In_Fragment())
                .addToBackStack(null) // Cho phép quay lại fragment trước đó nếu cần
                .commit();
    }
}