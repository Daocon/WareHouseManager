package hieudx.fpoly.warehousemanager.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.adapters.Category_Adapter;
import hieudx.fpoly.warehousemanager.adapters.Delivery_Adapter;
import hieudx.fpoly.warehousemanager.adapters.Product_Adapter;
import hieudx.fpoly.warehousemanager.dao.Category_Dao;
import hieudx.fpoly.warehousemanager.dao.Delivery_Dao;
import hieudx.fpoly.warehousemanager.dao.Product_Dao;
import hieudx.fpoly.warehousemanager.databinding.DialogDeleteCategoryBinding;
import hieudx.fpoly.warehousemanager.databinding.DialogUpdateDeliveryBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentDeliveryBinding;
import hieudx.fpoly.warehousemanager.fragments.delivery.Delevery_update;
import hieudx.fpoly.warehousemanager.fragments.delivery.Delivery_add;
import hieudx.fpoly.warehousemanager.fragments.fragment_product.Product_Add;
import hieudx.fpoly.warehousemanager.fragments.fragment_product.Product_Update;
import hieudx.fpoly.warehousemanager.models.Category;
import hieudx.fpoly.warehousemanager.models.Delivery;
import hieudx.fpoly.warehousemanager.models.Product;


public class Delivery_Fragment extends Fragment {
    public Delivery_Fragment() {
    }

    FragmentDeliveryBinding binding;
    private Delivery_Dao dao;
    private Delivery_Adapter adapter;
    private ArrayList<Delivery> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDeliveryBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        dao = new Delivery_Dao(getContext());
        list = dao.getListDelivery();
        RecyclerView rcvListDeligery = binding.rcvListDeligery;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvListDeligery.setLayoutManager(linearLayoutManager);
        adapter = new Delivery_Adapter(list, getContext());
        rcvListDeligery.setAdapter(adapter);
        updateAddButtonVisibility();
        adapter.setOnItemClick(new Product_Adapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                showDialogDetail(adapter.getDeliveryAtPosition(position));
            }
        });
        binding.btnAddDeligery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delivery_add deliveryAdd = new Delivery_add();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frag_container_main, deliveryAdd);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        binding.fladdDeligery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delivery_add deliveryAdd = new Delivery_add();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frag_container_main, deliveryAdd);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            String tenDvvc = bundle.getString("namedvvc");
            int giaDvvc = bundle.getInt("giadvvc");
            String sdtdvvc = bundle.getString("sdtdvvc");
            String mst = bundle.getString("masothue");
            Delivery newDelivery = new Delivery(tenDvvc, sdtdvvc, giaDvvc, mst);
            if (dao.insertDelivery(newDelivery)) {
                list.clear();
                list.addAll(dao.getListDelivery());
                adapter.notifyDataSetChanged();
                updateAddButtonVisibility();
                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
        }



        return binding.getRoot();
    }

    private void updateAddButtonVisibility() {
        boolean isListEmpty = adapter.isListEmpty();
        if (isListEmpty) {
            binding.btnAddDeligery.setVisibility(View.VISIBLE);
            binding.fladdDeligery.setVisibility(View.GONE);
        } else {
            binding.btnAddDeligery.setVisibility(View.GONE);
            binding.fladdDeligery.setVisibility(View.VISIBLE);
        }
    }
    private void showDialogDetail(Delivery delivery){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity) getActivity()).getLayoutInflater();

        DialogUpdateDeliveryBinding updateDeliveryBinding = DialogUpdateDeliveryBinding.inflate(inflater);
        builder.setView(updateDeliveryBinding.getRoot());

        Dialog dialog = builder.create();
        dialog.show();
        if (delivery != null) {
            updateDeliveryBinding.txtTenDvvc.setText(delivery.getName());
            updateDeliveryBinding.txtSdtDvvc.setText(delivery.getPhone());
            updateDeliveryBinding.txtGiaDvvc.setText(String.valueOf(delivery.getPrice()));
            updateDeliveryBinding.txtMaSoThue.setText(delivery.getTax_code());
        }
        updateDeliveryBinding.btnUpdateDvvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToDeliveryUpdateFragment(delivery);
                dialog.dismiss();
            }
        });


    }
    private void navigateToDeliveryUpdateFragment(Delivery delivery) {
        Delevery_update editFragment = new Delevery_update();
        Bundle bundle = new Bundle();
        bundle.putInt("deliveryId", delivery.getId());

        editFragment.setArguments(bundle);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container_main, editFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}