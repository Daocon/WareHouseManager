package hieudx.fpoly.warehousemanager.fragments.Staff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.FragmentStaffAddBinding;
import hieudx.fpoly.warehousemanager.fragments.Staff_Fragment;

public class Staff_Add extends Fragment {

    public Staff_Add() {
    }

    FragmentStaffAddBinding binding;

    String name, sdt, diaChi, salary, work_day, coefficient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStaffAddBinding.inflate(inflater, container, false);

        binding.edtTenStaff.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                validName();
            }
        });

        binding.edtDiaChiStaff.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                validAddress();
            }
        });

        binding.edtSdtStaff.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                validPhone();
            }
        });

        binding.edtSalaryStaff.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                validSalary();
            }
        });

        binding.edtWorkDayStaff.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                validWorkDay();
            }
        });

        binding.edtCoefficientStaff.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                validCoefficient();
            }
        });



        binding.btnThemStaff.setOnClickListener(v -> {
            putData();
        });

        return binding.getRoot();
    }


    private void putData() {
        validName();
        validAddress();
        validPhone();
        validSalary();
        validWorkDay();
        validCoefficient();
        if (binding.titleTenStaff.getError() == null &&
                binding.titleSdtStaff.getError() == null &&
                binding.titleDiaChiStaff.getError() == null &&
                binding.titleSalaryStaff.getError() == null &&
                binding.titleWorkDayStaff.getError() == null &&
                binding.titleCoefficientStaff.getError() == null) {
            Bundle bundle = new Bundle();
            bundle.putString("namenv", name);
            bundle.putString("sdtnnv", sdt);
            bundle.putString("diachinnv", diaChi);
            bundle.putString("luongnv", salary);
            bundle.putString("work_day", work_day);
            bundle.putString("coefficient", coefficient);
            Staff_Fragment staffFragment = new Staff_Fragment();
            staffFragment.setArguments(bundle);
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag_container_main, staffFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void validName() {
        name = binding.edtTenStaff.getText().toString();
        if (name.isEmpty()) {
            binding.titleTenStaff.setError("Không được để trống");
        } else {
            binding.titleTenStaff.setError(null);
        }
    }

    private void validPhone() {
        sdt = binding.edtSdtStaff.getText().toString();
        if (sdt.isEmpty()) {
            binding.titleSdtStaff.setError("Không được để trống");
        } else if (!sdt.matches("^0\\d{9}$")) {
            binding.titleSdtStaff.setError("Số điện thoại không hợp lệ");
        } else {
            binding.titleSdtStaff.setError(null);
        }
    }

    private void validAddress() {
        diaChi = binding.edtDiaChiStaff.getText().toString();
        if (diaChi.isEmpty()) {
            binding.titleDiaChiStaff.setError("Không được để trống");
        } else {
            binding.titleDiaChiStaff.setError(null);
        }
    }

    private void validSalary() {
        salary = binding.edtSalaryStaff.getText().toString();
        if (salary.isEmpty()) {
            Toast.makeText(getContext(), "Khoong được để trống", Toast.LENGTH_SHORT).show();
        } else {
            try {

                int salaryInt = Integer.parseInt(salary);
                if (salaryInt < 0) {
                    Toast.makeText(getContext(), "Lương phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                } else {
                    binding.titleSalaryStaff.setError(null);
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "Vui Lòng nhập đúng định dạng số", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void validWorkDay() {
        work_day = binding.edtWorkDayStaff.getText().toString();
        if (work_day.isEmpty()) {
            Toast.makeText(getContext(), "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            try {

                int workDay_int = Integer.parseInt(work_day);
                if (workDay_int < 0) {
                    Toast.makeText(getContext(), "Số ngày làm phải phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                } else {
                    binding.titleWorkDayStaff.setError(null);
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void validCoefficient() {
        coefficient = binding.edtCoefficientStaff.getText().toString();
        if (coefficient.isEmpty()) {
            Toast.makeText(getContext(), "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            try {
                int coefficient_int = Integer.parseInt(coefficient);
                if (coefficient_int < 0) {
                    Toast.makeText(getContext(), "hệ số làm phải phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                } else {
                    binding.titleWorkDayStaff.setError(null);
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
