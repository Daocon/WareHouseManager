package hieudx.fpoly.warehousemanager.Supplier;

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
import hieudx.fpoly.warehousemanager.databinding.BottomSheetSupplierBinding;
import hieudx.fpoly.warehousemanager.databinding.DialogUpdateSupplierBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentSupplierBinding;

public class Supplier_Fragment extends Fragment {

    public Supplier_Fragment() {
    }

    FragmentSupplierBinding binding;
    private Supplier_Dao dao;
    private Supplier_Adapter adapter;
    private ArrayList<Supplier> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupplierBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        dao = new Supplier_Dao(getContext());
        list = dao.getAll();
        RecyclerView rcvListSupplier = binding.rcvListSupplier;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvListSupplier.setLayoutManager(linearLayoutManager);
        adapter = new Supplier_Adapter(list, getContext());
        rcvListSupplier.setAdapter(adapter);
        updateAddButtonVisibility();
//        adapter.setOnItemClick(new Product_Adapter.OnItemClick() {
//            @Override
//            public void onItemClick(int position) {
//                showDialogDetail(adapter.getSupplierAtPosition(position));
//            }
//        });
        binding.btnSheetSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBottomSheet();
            }
        });
        binding.btnAddSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragmentAdd();
            }
        });
        binding.fladdSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               replaceFragmentAdd();
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            String namencc = bundle.getString("namencc");
            String sdtncc = bundle.getString("sdtncc");
            String diachincc = bundle.getString("diachincc");
            String masothue = bundle.getString("masothue");

            Supplier newSupplier = new Supplier(namencc,sdtncc,diachincc,masothue);
            if (dao.inserSupplier(newSupplier)) {
                list.clear();
                list.addAll(dao.getAll());
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
            binding.btnAddSupplier.setVisibility(View.VISIBLE);
            binding.fladdSupplier.setVisibility(View.GONE);
        } else {
            binding.btnAddSupplier.setVisibility(View.GONE);
            binding.fladdSupplier.setVisibility(View.VISIBLE);
        }
    }

    private void showDialogBottomSheet() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        BottomSheetSupplierBinding layoutBinding = BottomSheetSupplierBinding.inflate(getLayoutInflater());
        dialog.setContentView(layoutBinding.getRoot());
        layoutBinding.radioGroupSupplier.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.sxA_ZSupplier) {
                    Toast.makeText(getContext(), "Sắp xếp từ A-Z", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<Supplier>() {
                        @Override
                        public int compare(Supplier supplier, Supplier t1) {
                            return supplier.getName().compareToIgnoreCase(t1.getName());
                        }
                    });
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Sắp xếp từ Z-A", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<Supplier>() {
                        @Override
                        public int compare(Supplier supplier, Supplier t1) {
                            return t1.getName().compareToIgnoreCase(supplier.getName());
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
    private void replaceFragmentAdd(){
        Supplier_Add supplierAdd = new Supplier_Add();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container_main, supplierAdd);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void navigateToSupplierUpdateFragment(Supplier supplier) {
        Supplier_Update editFragment = new Supplier_Update();
        Bundle bundle = new Bundle();
        bundle.putInt("supplierId", supplier.getId());

        editFragment.setArguments(bundle);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container_main, editFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showDialogDetail(Supplier supplier) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity) getActivity()).getLayoutInflater();

        DialogUpdateSupplierBinding updateSupplierBinding = DialogUpdateSupplierBinding.inflate(inflater);
        builder.setView(updateSupplierBinding.getRoot());

        Dialog dialog = builder.create();
        dialog.show();
        if (supplier != null) {
            updateSupplierBinding.txtNameSupplier.setText("Tên: " + supplier.getName());
            updateSupplierBinding.txtSdtSupplier.setText("Phone: " + supplier.getPhone());
            updateSupplierBinding.txtDiachiSupplier.setText("Địa chỉ: " + supplier.getAddress());
            updateSupplierBinding.txtMaSoThueSupplier.setText("Mã số thuế: " + supplier.getTax_code());

        }
        updateSupplierBinding.btnUpdateSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSupplierUpdateFragment(supplier);
                dialog.dismiss();
            }
        });


    }

}