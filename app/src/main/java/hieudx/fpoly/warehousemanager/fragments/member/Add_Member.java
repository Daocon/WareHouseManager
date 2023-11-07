package hieudx.fpoly.warehousemanager.fragments.member;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.intellij.lang.annotations.Pattern;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.FragmentAddMemberBinding;

public class Add_Member extends Fragment {
    private FragmentAddMemberBinding binding;
    String name, username, phone, email, password;

    public Add_Member() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddMemberBinding.inflate(inflater, container, false);
        binding.txtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validationName();
                }
            }
        });
        binding.txtUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validationUsername();
                }
            }
        });
        binding.txtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validationPhone();
                }
            }
        });
        binding.txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validationEmail();
                }
            }
        });
        binding.txtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validationPassword();
                }
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Member_Fragment memberFragment = new Member_Fragment();

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container_main, memberFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return binding.getRoot();
    }

    private void validateData() {
        validationName();
        validationUsername();
        validationPhone();
        validationEmail();
        validationPassword();

        // Kiểm tra xem có bất kỳ lỗi nào không
        if (binding.tilName.getError() == null &&
                binding.tilUsername.getError() == null &&
                binding.tilPhone.getError() == null &&
                binding.tilEmail.getError() == null &&
                binding.tilPassword.getError() == null) {

            // Tất cả các trường dữ liệu hợp lệ, tiến hành gửi Bundle và chuyển fragment
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("username", username);
            bundle.putString("phone", phone);
            bundle.putString("email", email);
            bundle.putString("password", password);

            Member_Fragment memberFragment = new Member_Fragment();
            memberFragment.setArguments(bundle);

            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag_container_main, memberFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            // Hiển thị thông báo lỗi nếu có lỗi
            Toast.makeText(getContext(), "Vui lòng kiểm tra lại các trường dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }

    private void validationPassword() {
        password = binding.txtPassword.getText().toString();
        int passwordLength = password.length();
        if (password.isEmpty()) {
            binding.tilPassword.setError("Vui lòng nhập mật khẩu");
        } else if (password.contains(" ")) {
            binding.tilPassword.setError("Mật khẩu không được chứa khoảng trắng");
        } else if (passwordLength < 5 || passwordLength >= 10) {
            binding.tilPassword.setError("Mật khẩu phải có ít nhất 5 ký tự và nhỏ hơn 10 ký tự");
        } else {
            binding.tilPassword.setError(null); // Xóa thông báo lỗi nếu dữ liệu hợp lệ
        }
    }

    private void validationEmail() {
        email = binding.txtEmail.getText().toString();
        if (email.isEmpty()) {
            binding.tilEmail.setError("Vui lòng nhập email");
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            binding.tilEmail.setError("Vui lòng nhập đúng định dạng email");
        } else {
            binding.tilEmail.setError(null); // Xóa thông báo lỗi nếu dữ liệu hợp lệ
        }
    }

    private void validationPhone() {
        phone = binding.txtPhone.getText().toString();
        int phoneLength = phone.length();
        if (phone.isEmpty()) {
            binding.tilPhone.setError("Vui lòng nhập số điện thoại");
        } else if (phoneLength < 10){
            binding.tilPhone.setError("Số điện thoại phải chứa 10 số");
        } else if (phone.contains(" ")) {
            binding.tilPhone.setError("Số điện thoại không được chứa khoảng trắng");
        } else if (!phone.startsWith("0")) {
            binding.tilPhone.setError("Số điện thoại phải bắt đầu bằng số 0");
        } else {
            binding.tilPhone.setError(null); // Xóa thông báo lỗi nếu dữ liệu hợp lệ
        }
    }

    private void validationUsername() {
        username = binding.txtUsername.getText().toString();
        int usernameLength = username.length();
        if (username.isEmpty()) {
            binding.tilUsername.setError("Vui lòng nhập tên đăng nhập");
        } else if (username.contains(" ")) {
            binding.tilUsername.setError("Tên đăng nhập không được chứa khoảng trắng");
        } else if (usernameLength < 5 || usernameLength > 15) {
            binding.tilUsername.setError("Tên đăng nhập phải có từ 5 đến 15 ký tự");
        } else {
            binding.tilUsername.setError(null); // Xóa thông báo lỗi nếu dữ liệu hợp lệ
        }
    }

    private void validationName() {
        name = binding.txtName.getText().toString();
        if (name.isEmpty()) {
            binding.tilName.setError("Vui lòng nhập họ và tên");
        } else if (name.length() < 2) {
            binding.tilName.setError("Tên phải có ít nhất 2 ký tự");
        } else if (name.matches(".*\\d.*")) {
            binding.tilName.setError("Tên không được chứa số");
        } else {
            binding.tilName.setError(null); // Xóa thông báo lỗi nếu dữ liệu hợp lệ
        }
    }
}