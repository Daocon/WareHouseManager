package hieudx.fpoly.warehousemanager.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hieudx.fpoly.warehousemanager.R;

public class Product_Fragment extends Fragment {

    public static Fragment newInstance() {
        Product_Fragment fragment = new Product_Fragment();
        return fragment;
    }

    public Product_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_, container, false);
    }
}