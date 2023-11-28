package hieudx.fpoly.warehousemanager.fragments.statistic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;

import hieudx.fpoly.warehousemanager.adapters.Statistic_Adapter;
import hieudx.fpoly.warehousemanager.databinding.FragmentStatisticBinding;

public class Statistic_Fragment extends Fragment {
    private FragmentStatisticBinding binding;
    private TabLayoutMediator tabLayoutMediator;
    private Statistic_Adapter adapter;

    public Statistic_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new Statistic_Adapter(this);
        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Tổng quan"); break;
                case 1: tab.setText("Báo cáo"); break;
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