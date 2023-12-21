package hieudx.fpoly.warehousemanager.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.adapters.Delivery_Adapter;
import hieudx.fpoly.warehousemanager.dao.Delivery_Dao;
import hieudx.fpoly.warehousemanager.databinding.BottomSheetDeliveryBinding;
import hieudx.fpoly.warehousemanager.databinding.DialogUpdateDeliveryBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentDeliveryBinding;
import hieudx.fpoly.warehousemanager.fragments.delivery.Delevery_update;
import hieudx.fpoly.warehousemanager.fragments.delivery.Delivery_add;
import hieudx.fpoly.warehousemanager.models.Delivery;


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
//        adapter.setOnItemClick(new Product_Adapter.OnItemClick() {
//            @Override
//            public void onItemClick(int position) {
//                showDialogDetail(adapter.getDeliveryAtPosition(position));
//            }
//        });
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
        binding.btnSheetDeligery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBottomSheet();
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
    public void showDialogBottomSheet(){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        BottomSheetDeliveryBinding layoutBinding = BottomSheetDeliveryBinding.inflate(getLayoutInflater());
        dialog.setContentView(layoutBinding.getRoot());
        layoutBinding.radioGroupDelivery.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.sxA_ZDelivery){
                    Toast.makeText(getContext(), "Sắp xếp từ A-Z", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<Delivery>() {
                        @Override
                        public int compare(Delivery delivery, Delivery t1) {
                            return delivery.getName().compareToIgnoreCase(t1.getName());
                        }
                    });
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(), "Sắp xếp từ Z-A", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<Delivery>() {
                        @Override
                        public int compare(Delivery delivery, Delivery t1) {
                            return t1.getName().compareToIgnoreCase(delivery.getName());
                        }
                    });
                    adapter.notifyDataSetChanged();
                }
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}