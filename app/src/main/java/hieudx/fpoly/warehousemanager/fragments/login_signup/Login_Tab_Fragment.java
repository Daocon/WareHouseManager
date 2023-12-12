package hieudx.fpoly.warehousemanager.fragments.login_signup;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import hieudx.fpoly.warehousemanager.MainActivity;
import hieudx.fpoly.warehousemanager.dao.User_Dao;
import hieudx.fpoly.warehousemanager.databinding.FragmentLoginTabBinding;
import hieudx.fpoly.warehousemanager.models.User;
import hieudx.fpoly.warehousemanager.view.Forgot_Reset_Pass_Activity;


public class Login_Tab_Fragment extends Fragment {
    private FragmentLoginTabBinding binding;
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    public Login_Tab_Fragment() {
    }

    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginTabBinding.inflate(inflater, container, false);

        checkRemember();
        animation();

        binding.tvForgotPass.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), Forgot_Reset_Pass_Activity.class));
        });

        binding.btnLogin.setOnClickListener(view -> {
            String username = binding.edUsername.getText().toString();
            String pass = binding.edPass.getText().toString();
            if (username.isEmpty() || pass.isEmpty()) {
                Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
            } else {
                User_Dao user_dao = new User_Dao(getContext());
                if (user_dao.checkLogin(username, pass)) {
                    User user = user_dao.getUserByUsernameAndPassword(username,pass);
                    if (user.getRole() == -1){
                        Toast.makeText(getContext(), "Tài khoản bị hạn chế!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (binding.cbRemember.isChecked()) {
                            editor.putString("username", username);
                            editor.putString("password", pass);
                            editor.putBoolean("isChecked", binding.cbRemember.isChecked());
                            editor.apply();
                        } else {
                            editor.clear();
                            editor.apply();
                        }
                        binding.edUsername.setText("");
                        binding.edPass.setText("");
                        Intent i = new Intent(getContext(), MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        getActivity().finish();
                    }
                } else {
                    Toast.makeText(getContext(), "Tài khoản và mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return binding.getRoot();
    }

    private void checkRemember() {
        shared = requireActivity().getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        editor = shared.edit(); // gọi dòng trên và edit vào nó
        boolean isCheck = shared.getBoolean("isChecked", false);
        if (isCheck) {
            binding.edUsername.setText(shared.getString("username", ""));
            binding.edPass.setText(shared.getString("password", ""));
            binding.cbRemember.setChecked(isCheck);
        }
    }

    private void animation() {
        binding.edUsername.setTranslationX(800);
        binding.pass.setTranslationX(800);
        binding.cbRemember.setTranslationX(800);
        binding.tvForgotPass.setTranslationX(800);
        binding.btnLogin.setTranslationX(800);
//
        binding.edUsername.setAlpha(0);
        binding.pass.setAlpha(0);
        binding.cbRemember.setAlpha(0);
        binding.tvForgotPass.setAlpha(0);
        binding.btnLogin.setAlpha(0);

        binding.edUsername.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        binding.pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        binding.cbRemember.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        binding.tvForgotPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        binding.btnLogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
    }
}