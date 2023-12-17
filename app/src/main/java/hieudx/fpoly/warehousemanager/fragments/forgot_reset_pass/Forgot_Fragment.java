package hieudx.fpoly.warehousemanager.fragments.forgot_reset_pass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.dao.User_Dao;
import hieudx.fpoly.warehousemanager.databinding.FragmentForgotBinding;
import hieudx.fpoly.warehousemanager.models.User;

public class Forgot_Fragment extends Fragment {
    private FragmentForgotBinding binding;
    private FragmentManager fragmentManager;
    private User_Dao userDao;
    float v = 0;
    int userID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDao = new User_Dao(getActivity());
    }

    public Forgot_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotBinding.inflate(inflater, container, false);

        fragmentManager = requireActivity().getSupportFragmentManager();

        onAnimation();

        binding.imgBack.setOnClickListener(view -> {
//            fragmentManager.popBackStack();
            getActivity().finish();
        });

        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyUser();
            }
        });
        return binding.getRoot();
    }

    private void verifyUser() {
        String email = binding.edEmail.getText().toString().trim();
        String phone = binding.edPhoneNumber.getText().toString().trim();

        boolean isUserValid = false;

        for (User user : userDao.getAllUser()) {
            if (user.getEmail().equals(email) && user.getPhone().equals(phone)) {
                isUserValid = true;
                userID = user.getId();
                break;
            }
        }

        if (isUserValid) {
            Reset_Fragment resetFragment = new Reset_Fragment();
            Bundle bundle = new Bundle();
            bundle.putInt("userID", userID);
            Toast.makeText(getActivity(), "Id: " + userID, Toast.LENGTH_SHORT).show();
            resetFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.frag_container, resetFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Toast.makeText(getActivity(), "Email hoặc số điện thoại ko đúng", Toast.LENGTH_SHORT).show();
        }
    }

    private void onAnimation() {
        binding.imgBack.setTranslationY(100);
        binding.tv1.setTranslationY(100);
        binding.tv2.setTranslationY(100);

        binding.edEmail.setTranslationX(800);
        binding.edEmail.setTranslationX(800);
        binding.btnVerify.setTranslationX(800);

        binding.imgBack.setAlpha(v);
        binding.tv2.setAlpha(v);
        binding.tv1.setAlpha(v);
        binding.edPhoneNumber.setAlpha(v);
        binding.edEmail.setAlpha(v);
        binding.btnVerify.setAlpha(v);

        binding.imgBack.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(400).start();
        binding.tv1.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(400).start();
        binding.tv2.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(400).start();

        binding.edEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        binding.edPhoneNumber.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        binding.btnVerify.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
    }
}