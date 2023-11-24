package hieudx.fpoly.warehousemanager.fragments.Bill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;

import hieudx.fpoly.warehousemanager.adapters.bill.Bill_Adapter;
import hieudx.fpoly.warehousemanager.databinding.FragmentBillBinding;

public class Bill_Fragment extends Fragment {
    private FragmentBillBinding binding;
    private TabLayoutMediator tabLayoutMediator;
    private Bill_Adapter adapter;

    public Bill_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBillBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new Bill_Adapter(this);
        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Phiếu nhập"); break;
                case 1: tab.setText("Phiếu xuất"); break;
            }
        });
        binding.viewPager.setAdapter(adapter);
        if (!tabLayoutMediator.isAttached()) tabLayoutMediator.attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tabLayoutMediator.detach();
        binding = null;
    }
}