package hieudx.fpoly.warehousemanager.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.ActivityForgotResetPassBinding;
import hieudx.fpoly.warehousemanager.fragments.forgot_reset_pass.Forgot_Fragment;

public class Forgot_Reset_Pass_Activity extends AppCompatActivity {
    private ActivityForgotResetPassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotResetPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().add(R.id.frag_container,
                new Forgot_Fragment()).addToBackStack(null).commit();
    }
}