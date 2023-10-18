package hieudx.fpoly.warehousemanager.fragments.forgot_reset_pass;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import hieudx.fpoly.warehousemanager.databinding.FragmentResetBinding;
import hieudx.fpoly.warehousemanager.view.Login_SignUp_Activity;

public class Reset_Fragment extends Fragment {
    private FragmentResetBinding binding;

    public Reset_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResetBinding.inflate(inflater, container, false);

        binding.imgBack.setOnClickListener(view -> {
//            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        binding.btnResetPass.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), Login_SignUp_Activity.class));
        });
        return binding.getRoot();
    }
}