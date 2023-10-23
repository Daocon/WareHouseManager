package hieudx.fpoly.warehousemanager.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hieudx.fpoly.warehousemanager.R;


public class Category_Fragment extends Fragment {
    public static Fragment newInstance() {
        Category_Fragment fragment = new Category_Fragment();
        return fragment;
    }
    public Category_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_, container, false);
    }
}