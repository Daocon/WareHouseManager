package hieudx.fpoly.warehousemanager.fragments.Supplier;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.FragmentSupplierAddBinding;
import hieudx.fpoly.warehousemanager.fragments.Delivery_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Supplier_Fragment;


public class Supplier_Add extends Fragment {


    public Supplier_Add() {
        // Required empty public constructor
    }

    FragmentSupplierAddBinding binding;
    String name, sdt, diaChi, maSoThue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupplierAddBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        binding.edtTenSupplier.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    validName();
                }
            }
        });
        binding.edtSdtSupplier.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    validPhone();
                }
            }
        });
        binding.edtDiaChiSupplier.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    validAddress();
                }
            }
        });
        binding.edtMaSoThueSupplier.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    validMaSoThue();
                }
            }
        });
        binding.btnThemSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putData();
            }
        });
        return binding.getRoot();
    }

    private void putData() {
        validName();
        validAddress();
        validPhone();
        validMaSoThue();
        if (binding.tilTenSupplier.getError() == null &&
                binding.tilSdtSupplier.getError() == null &&
                binding.tilDiaChiSupplier.getError() == null &&
                binding.tilMaSoThueSupplier.getError() == null) {
            Bundle bundle = new Bundle();
            bundle.putString("namencc", name);
            bundle.putString("sdtncc", sdt);
            bundle.putString("diachincc", diaChi);
            bundle.putString("masothue", maSoThue);
            Supplier_Fragment supplierFragment = new Supplier_Fragment();
            supplierFragment.setArguments(bundle);
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag_container_main, supplierFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void validName() {
        name = binding.edtTenSupplier.getText().toString();
        if (name.isEmpty()) {
            binding.tilTenSupplier.setError("Không được để trống");
        } else {
            binding.tilTenSupplier.setError(null);
        }
    }

    private void validPhone() {
        sdt = binding.edtSdtSupplier.getText().toString();
        if (sdt.isEmpty()) {
            binding.tilSdtSupplier.setError("Không được để trống");
        } else if (!sdt.matches("^0\\d{9}$")) {
            binding.tilSdtSupplier.setError("Số điện thoại không hợp lệ");
        } else {
            binding.tilSdtSupplier.setError(null);
        }
    }

    private void validAddress() {
        diaChi = binding.edtDiaChiSupplier.getText().toString();
        if (diaChi.isEmpty()) {
            binding.tilDiaChiSupplier.setError("Không được để trống");
        } else {
            binding.tilDiaChiSupplier.setError(null);
        }
    }

    private void validMaSoThue() {
        maSoThue = binding.edtMaSoThueSupplier.getText().toString();
        if (maSoThue.isEmpty()) {
            binding.tilMaSoThueSupplier.setError("Không được để trống");
        } else {
            binding.tilMaSoThueSupplier.setError(null);
        }
    }

}