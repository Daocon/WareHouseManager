package hieudx.fpoly.warehousemanager.fragments.login_signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import hieudx.fpoly.warehousemanager.databinding.FragmentSignUpTabBinding;


public class SignUp_Tab_Fragment extends Fragment {
    private FragmentSignUpTabBinding binding;

    public SignUp_Tab_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpTabBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }
}