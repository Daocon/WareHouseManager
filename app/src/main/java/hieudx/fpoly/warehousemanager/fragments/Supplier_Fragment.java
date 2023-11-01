package hieudx.fpoly.warehousemanager.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hieudx.fpoly.warehousemanager.R;

public class Supplier_Fragment extends Fragment {

    public Supplier_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_supplier_, container, false);
    }
}