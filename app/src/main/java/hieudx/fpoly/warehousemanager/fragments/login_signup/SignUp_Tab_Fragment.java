package hieudx.fpoly.warehousemanager.fragments.login_signup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.dao.User_Dao;
import hieudx.fpoly.warehousemanager.databinding.FragmentSignUpTabBinding;
import hieudx.fpoly.warehousemanager.models.User;


public class SignUp_Tab_Fragment extends Fragment {
    private FragmentSignUpTabBinding binding;

    public SignUp_Tab_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpTabBinding.inflate(inflater, container, false);

        validForm();
        binding.btnSignUp.setOnClickListener(view -> {
            signUp();
        });
        return binding.getRoot();
    }

    private void signUp() {
        String username = binding.username.getEditText().getText().toString();
        String name = binding.name.getEditText().getText().toString();
        String email = binding.email.getEditText().getText().toString();
        String pass = binding.pass.getEditText().getText().toString();
        String repass = binding.repass.getEditText().getText().toString();
        String phone = binding.phoneNumber.getEditText().getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) || TextUtils.isEmpty(phone)) {
            Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            General.validPass(pass, repass, binding.repass);
            General.validName(name, binding.name);
            General.validUsername(username, binding.username);
            General.validEmail(email, binding.email);
            General.validPhone(phone, binding.phoneNumber);
            if (binding.username.getError() == null
                    && binding.name.getError() == null
                    && binding.email.getError() == null
                    && binding.pass.getError() == null
                    && binding.repass.getError() == null
                    && binding.phoneNumber.getError() == null) {
                User user = new User(username, repass, name, email, phone, 1);
                User_Dao user_dao = new User_Dao(getContext());
                int check = user_dao.insert(user);
                switch (check) {
                    case 1:
                        Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        binding.username.getEditText().setText("");
                        binding.name.getEditText().setText("");
                        binding.email.getEditText().setText("");
                        binding.pass.getEditText().setText("");
                        binding.repass.getEditText().setText("");
                        binding.phoneNumber.getEditText().setText("");
                        break;
                    case 0:
                        Toast.makeText(getContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(getContext(), "Tài khoản đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                    default:
                        break;
                }
            }
        }
    }

    private void validForm() {
        binding.edName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.name.setError("Vui lòng nhập tên");
                } else {
                    binding.name.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.edUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.username.setError("Vui lòng nhập tên");
                } else {
                    binding.username.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.email.setError("Vui lòng nhập email");
                } else {
                    binding.email.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        binding.edPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.pass.setError("Vui lòng nhập mật khẩu");
                } else {
                    binding.pass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        binding.edRePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.repass.setError("Vui lòng nhập lại mật khẩu");
                } else {
                    binding.repass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        binding.edPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.phoneNumber.setError("Vui lòng nhập số điện thoại");
                } else {
                    binding.phoneNumber.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}