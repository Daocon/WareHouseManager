package hieudx.fpoly.warehousemanager.fragments.statistic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import hieudx.fpoly.warehousemanager.databinding.FragmentStatisticBinding;

public class Statistic_Fragment extends Fragment {
    private FragmentStatisticBinding binding;

    public Statistic_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}