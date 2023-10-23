package hieudx.fpoly.warehousemanager.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hieudx.fpoly.warehousemanager.R;

public class Statistic_Fragment extends Fragment {
    public Statistic_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistic_, container, false);
    }
}