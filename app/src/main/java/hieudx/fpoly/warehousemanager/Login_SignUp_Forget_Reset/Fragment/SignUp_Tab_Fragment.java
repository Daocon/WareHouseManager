package hieudx.fpoly.warehousemanager.Login_SignUp_Forget_Reset.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.Login_SignUp_Forget_Reset.Activity.Login_SignUp_Activity;
import hieudx.fpoly.warehousemanager.Member.Dao.User_Dao;
import hieudx.fpoly.warehousemanager.Member.account.Edit_UserLogin;
import hieudx.fpoly.warehousemanager.databinding.FragmentSignUpTabBinding;
import hieudx.fpoly.warehousemanager.Member.Model.User;

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
        String name = binding.edName.getText().toString().trim();
        String username = binding.edUsername.getText().toString().trim();
        String email = binding.edEmail.getText().toString().trim();
        String pass = binding.edPass.getText().toString().trim();
        String repass = binding.edRePass.getText().toString().trim();
        String phone = binding.edPhoneNumber.getText().toString().trim();

        General.isContainNumber(name, binding.name);
        General.isContainSpecialChar(name, binding.name);
        General.isContainSpace(username, binding.username);
        General.isContainSpecialChar(username, binding.username);
        General.validEmail(email, binding.email);
        General.passValid(pass, repass, binding.repass);
        General.validPhone(phone, binding.phoneNumber);

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) || TextUtils.isEmpty(phone)) {
            Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
        } else if (binding.username.getError() == null
                && binding.name.getError() == null
                && binding.email.getError() == null
                && binding.pass.getError() == null
                && binding.repass.getError() == null
                && binding.phoneNumber.getError() == null) {
            User user = new User(username, md5(repass), name, email, phone, 1, "https://vectorified.com/images/avatar-icon-png-3.png");
            User_Dao user_dao = new User_Dao(getContext());
            int check = user_dao.insert(user);
            switch (check) {
                case 1:
                    General.showSuccessPopup(getContext(), "Đăng ký thành công", "Bạn đã đăng ký thành công tài khoản ", new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(Dialog dialog) {
                            super.onDismissClicked(dialog);
                            Login_SignUp_Activity.binding.viewPager.setCurrentItem(0);

                        }
                    });
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

    private void validForm() {
        General.isEmptyValid(binding.edName, binding.name);
        General.isEmptyValid(binding.edUsername, binding.username);
        General.isEmptyValid(binding.edEmail, binding.email);
        General.isEmptyValid(binding.edPass, binding.pass);
        General.isEmptyValid(binding.edRePass, binding.repass);
        General.isEmptyValid(binding.edPhoneNumber, binding.phoneNumber);
    }

    public static String md5(String text) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                int number = b & 0xff; // add salt
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    sb.append("0"+hex);
                } else {
                    sb.append(hex);
                }
            }
            Log.i("Chuoi md5: ",sb.toString());
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}