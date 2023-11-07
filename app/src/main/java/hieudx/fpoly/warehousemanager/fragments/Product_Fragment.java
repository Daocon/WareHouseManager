package hieudx.fpoly.warehousemanager.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.FragmentProductBinding;

public class Product_Fragment extends Fragment {



    public Product_Fragment() {
    }
    private  FragmentProductBinding fragmentProductBinding;
    private View bView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProductBinding = FragmentProductBinding.inflate(inflater,container,false);
        bView = fragmentProductBinding.getRoot();




        return bView;
    }
}