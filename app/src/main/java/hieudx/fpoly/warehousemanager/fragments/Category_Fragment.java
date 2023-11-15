package hieudx.fpoly.warehousemanager.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.adapters.Category_Adapter;
import hieudx.fpoly.warehousemanager.adapters.User_Adapter;
import hieudx.fpoly.warehousemanager.dao.Category_Dao;
import hieudx.fpoly.warehousemanager.databinding.FragmentCategoryBinding;
import hieudx.fpoly.warehousemanager.models.Category;


public class Category_Fragment extends Fragment {
    private FragmentCategoryBinding binding;
    View cView;
    private Category_Dao categoryDao;
    private Category_Adapter adapter;
    private ArrayList<Category> list = new ArrayList<>();
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
        binding = FragmentCategoryBinding.inflate(inflater,container,false);
        cView = binding.getRoot();
        categoryDao = new Category_Dao(getContext());
        list = categoryDao.getListCategory();
        RecyclerView rcvListCategory = binding.rcvListCategory;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvListCategory.setLayoutManager(linearLayoutManager);
        adapter = new Category_Adapter(getContext(), list);
        rcvListCategory.setAdapter(adapter);
        if (adapter.checkAdd()) {
            binding.btnAddCategory.setVisibility(View.VISIBLE);
        } else {
            binding.btnAddCategory.setVisibility(View.GONE);
        }
        if (adapter.checkAdd()) {
            binding.fladdCategory.setVisibility(View.GONE);
        } else {
            binding.fladdCategory.setVisibility(View.VISIBLE);
        }
        return cView;
    }
}