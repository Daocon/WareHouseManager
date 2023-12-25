package hieudx.fpoly.warehousemanager.Supplier;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.Supplier.Supplier_Dao;
import hieudx.fpoly.warehousemanager.databinding.FragmentSupplierUpdateBinding;
import hieudx.fpoly.warehousemanager.Supplier.Supplier_Fragment;
import hieudx.fpoly.warehousemanager.Supplier.Supplier;


public class Supplier_Update extends Fragment {


    public Supplier_Update() {
        // Required empty public constructor
    }

    FragmentSupplierUpdateBinding binding;
    String name, sdt, diaChi, maSoThue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupplierUpdateBinding.inflate(inflater, container, false);

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

        Bundle bundle = getArguments();
        if (binding.tilTenSupplier.getError() == null &&
                binding.tilSdtSupplier.getError() == null &&
                binding.tilDiaChiSupplier.getError() == null &&
                binding.tilMaSoThueSupplier.getError() == null) {
            if (bundle != null) {
                int supplierId = bundle.getInt("supplierId", -1);
                if (supplierId != -1) {
                    Supplier_Dao dao = new Supplier_Dao(getContext());
                    Supplier supplier = dao.getSupplierById(supplierId);
                    if (supplier != null) {
                        binding.edtTenSupplier.setText(supplier.getName());
                        binding.edtSdtSupplier.setText(supplier.getPhone());
                        binding.edtDiaChiSupplier.setText(supplier.getAddress());
                        binding.edtMaSoThueSupplier.setText(supplier.getTax_code());
                        binding.btnUpdateSupplier.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String name1 = binding.edtTenSupplier.getText().toString();
                                String sdt1 = binding.edtSdtSupplier.getText().toString();
                                String diachi1 = binding.edtDiaChiSupplier.getText().toString();
                                String mst1 = binding.edtMaSoThueSupplier.getText().toString();

                                supplier.setName(name1);
                                supplier.setPhone(sdt1);
                                supplier.setAddress(diachi1);
                                supplier.setTax_code(mst1);
                                dao.updateSupplier(supplier);
                                Snackbar.make(getView(), "Cập nhật thành công", Snackbar.LENGTH_SHORT).show();
                                Supplier_Fragment supplierFragment = new Supplier_Fragment();

                                FragmentManager fragmentManager = getParentFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frag_container_main, supplierFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        });

                    }
                }
            }
        }
        // Inflate the layout for this fragment

        return binding.getRoot();
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