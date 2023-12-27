package hieudx.fpoly.warehousemanager.Staff.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.Staff.Dao.Staff_Dao;
import hieudx.fpoly.warehousemanager.databinding.FragmentUpdateStaffBinding;
import hieudx.fpoly.warehousemanager.Staff.Model.Staff;

public class Staff_Update extends Fragment {

   public Staff_Update(){}

    private Staff_Dao dao;

    FragmentUpdateStaffBinding binding;

    String name, sdt, diaChi, salary, work_day, coefficient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUpdateStaffBinding.inflate(inflater,container,false);

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

        binding.edtWorkStaff.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                validWorkDay();
            }
        });

        binding.edtCoefficientStaff.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                validCoefficient();
            }
        });


        Bundle bundle = getArguments();
        if (bundle != null) {
            int staffID = bundle.getInt("staffID", -1);
            if (staffID != -1) {
                dao = new Staff_Dao(getContext());
                Staff staff = dao.getStaffById(staffID);
                if (staff != null) {
                    binding.edtTenStaff.setText(staff.getName());
                    binding.edtSdtStaff.setText(staff.getPhone());
                    binding.edtDiaChiStaff.setText(staff.getAddress());
                    binding.edtSalaryStaff.setText(String.valueOf(staff.getSalary()));
                    binding.edtWorkStaff.setText(String.valueOf(staff.getWork_day()));
                    binding.edtCoefficientStaff.setText(String.valueOf(staff.getCoefficient()));

                    binding.btnUpdateStaff.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (isValid()) {
                                updateStaffData(staff);
                            } else {
                                Toast.makeText(getContext(), "Vui lòng điền đúng thông tin", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }
        return binding.getRoot();
    }

    private boolean isValid() {
        validName();
        validPhone();
        validAddress();
        validSalary();
        validWorkDay();
        validCoefficient();

        return binding.tilTenStaff.getError() == null &&
                binding.tilSdtStaff.getError() == null &&
                binding.tilDiaChiStaff.getError() == null &&
                binding.tilSalaryStaff.getError() == null &&
                binding.tilWorkStaff.getError() == null &&
                binding.tilCoefficientStaff.getError() == null;
    }

    private void updateStaffData(Staff staff) {
        String name1 = binding.edtTenStaff.getText().toString();
        String sdt1 = binding.edtSdtStaff.getText().toString();
        String diaChi1 = binding.edtDiaChiStaff.getText().toString();
        int salary1 = Integer.parseInt(binding.edtSalaryStaff.getText().toString());
        int work_day1 = Integer.parseInt(binding.edtWorkStaff.getText().toString());
        int coefficient1 = Integer.parseInt(binding.edtCoefficientStaff.getText().toString());

        staff.setName(name1);
        staff.setPhone(sdt1);
        staff.setAddress(diaChi1);
        staff.setSalary(salary1);
        staff.setWork_day(work_day1);
        staff.setCoefficient(coefficient1);
        dao.updateStaff(staff);
        General.showSuccessPopup(getContext(), "Thành công", "Bạn đã sửa thành công", new OnDialogButtonClickListener() {
            @Override
            public void onDismissClicked(android.app.Dialog dialog) {
                super.onDismissClicked(dialog);
            }
        });
        Staff_Fragment staff_fragment = new Staff_Fragment();


        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.frag_container_main, new Staff_Fragment()).commit();
    }

    public void validName() {
        name = binding.edtTenStaff.getText().toString();
        if (name.isEmpty()) {
            binding.tilTenStaff.setError("Không được để trống");
        } else {
            binding.tilTenStaff.setError(null);
        }
    }

    private void validPhone() {
        sdt = binding.edtSdtStaff.getText().toString();
        if (sdt.isEmpty()) {
            binding.tilSdtStaff.setError("Không được để trống");
        } else if (!sdt.matches("^0\\d{9}$")) {
            binding.tilSdtStaff.setError("Số điện thoại không hợp lệ");
        } else {
            binding.tilSdtStaff.setError(null);
        }
    }

    private void validAddress() {
        diaChi = binding.edtDiaChiStaff.getText().toString();
        if (diaChi.isEmpty()) {
            binding.tilDiaChiStaff.setError("Không được để trống");
        } else {
            binding.tilDiaChiStaff.setError(null);
        }
    }

    private void validSalary() {
        salary = binding.edtSalaryStaff.getText().toString();
        if (salary.isEmpty()) {
            binding.tilDiaChiStaff.setError("Không được để trống dữ liệu");
        } else {
            try {

                int salaryInt = Integer.parseInt(salary);
                if (salaryInt < 0) {
                    binding.tilDiaChiStaff.setError("Lương phải lớn hơn 0");
                } else {
                    binding.tilSalaryStaff.setError(null);
                }
            } catch (Exception e) {
                binding.tilDiaChiStaff.setError("Vui lòng nhập đúng định dạng số");
            }
        }
    }

    private void validWorkDay() {
        work_day = binding.edtWorkStaff.getText().toString();
        if (work_day.isEmpty()) {
            binding.tilWorkStaff.setError("Không được để trống");
        } else {
            try {

                int workDay_int = Integer.parseInt(work_day);
                if (workDay_int < 0) {
                    binding.tilWorkStaff.setError("Số ngày làm phải phải lớn hơn hoặc bằng 0");
                } else {
                    binding.tilWorkStaff.setError(null);
                }
            } catch (Exception e) {
                binding.tilWorkStaff.setError("Vui lòng nhập đúng định dạng số");
            }
        }
    }

    private void validCoefficient() {
        coefficient = binding.edtCoefficientStaff.getText().toString();
        if (coefficient.isEmpty()) {
            binding.tilCoefficientStaff.setError("Không được để trống dữ liệu");
        } else {
            try {
                int coefficient_int = Integer.parseInt(coefficient);
                if (coefficient_int < 0) {
                    binding.tilCoefficientStaff.setError("hệ số làm phải phải lớn hơn 0");
                } else {
                    binding.tilCoefficientStaff.setError(null);
                }
            } catch (Exception e) {
                binding.tilCoefficientStaff.setError("Vui lòng nhập đúng định dạng số");
            }
        }
    }

}
