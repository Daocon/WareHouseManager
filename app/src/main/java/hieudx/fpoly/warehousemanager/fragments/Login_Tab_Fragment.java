package hieudx.fpoly.warehousemanager.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.FragmentLoginTabBinding;


public class Login_Tab_Fragment extends Fragment {
    private FragmentLoginTabBinding binding;

    public Login_Tab_Fragment() {
    }
float v = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginTabBinding.inflate(inflater, container, false);

        binding.edUsername.setTranslationX(800);
        binding.pass.setTranslationX(800);
        binding.cbRemember.setTranslationX(800);
        binding.tvForgotPass.setTranslationX(800);
        binding.btnLogin.setTranslationX(800);
//
        binding.edUsername.setAlpha(v);
        binding.pass.setAlpha(v);
        binding.cbRemember.setAlpha(v);
        binding.tvForgotPass.setAlpha(v);
        binding.btnLogin.setAlpha(v);

        binding.edUsername.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        binding.pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        binding.cbRemember.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        binding.tvForgotPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        binding.btnLogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return binding.getRoot();
    }
}