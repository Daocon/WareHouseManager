package hieudx.fpoly.warehousemanager.fragments.Bill.Bill_In;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.adapters.Bill_In_Adapter;
import hieudx.fpoly.warehousemanager.dao.Bill.Bill_In_Dao;
import hieudx.fpoly.warehousemanager.databinding.FragmentBillBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentBillInBinding;
import hieudx.fpoly.warehousemanager.models.Bill_In;

public class Bill_In_Fragment extends Fragment {
    private FragmentBillInBinding binding;
    private Bill_In_Dao dao;
    private ArrayList<Bill_In> list;
    private Bill_In_Adapter adapter;

    public Bill_In_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBillInBinding.inflate(inflater, container, false);

        dao = new Bill_In_Dao(getContext());
        list = dao.getAll();
        if (!list.isEmpty()) {
            binding.btnAdd.setVisibility(View.GONE);
            binding.imgSort.setVisibility(View.VISIBLE);
            binding.rcv.setVisibility(View.VISIBLE);
            binding.fltAdd.setVisibility(View.VISIBLE);
            adapter = new Bill_In_Adapter(getContext(),list);
            binding.rcv.setAdapter(adapter);
        } else {
            binding.btnAdd.setVisibility(View.VISIBLE);
            binding.imgSort.setVisibility(View.GONE);
            binding.rcv.setVisibility(View.GONE);
            binding.fltAdd.setVisibility(View.GONE);
        }

        binding.btnAdd.setOnClickListener(view -> {
            loadFragment();
        });

        binding.fltAdd.setOnClickListener(view -> {
            loadFragment();
        });



        return binding.getRoot();
    }

    private void loadFragment() {
        Fragment newFragment = new Add_Bill_In_Fragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frag_container_main, newFragment)
                .addToBackStack(null) // Cho phép quay lại fragment trước đó nếu cần
                .commit();
    }
}