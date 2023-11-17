package hieudx.fpoly.warehousemanager.fragments.Bill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;

import hieudx.fpoly.warehousemanager.adapters.bill.Bill_Adapter;
import hieudx.fpoly.warehousemanager.databinding.FragmentBillBinding;

public class Bill_Fragment extends Fragment {
    private FragmentBillBinding binding;
    private TabLayoutMediator tabLayoutMediator;
    private Bill_Adapter adapter;

    public Bill_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBillBinding.inflate(inflater, container, false);

        adapter = new Bill_Adapter(requireActivity().getSupportFragmentManager(), getLifecycle());
        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) ->
        {
            tab.setText(adapter.getHeader(position));
        });
//setAdapter
        binding.viewPager.setAdapter(adapter);
//AttachMediator
        if (!tabLayoutMediator.isAttached()) tabLayoutMediator.attach();

        return binding.getRoot();
    }
}