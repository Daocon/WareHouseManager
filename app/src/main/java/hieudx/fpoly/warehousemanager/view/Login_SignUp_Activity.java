package hieudx.fpoly.warehousemanager.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import hieudx.fpoly.warehousemanager.adapters.Login_SignUp_Adapter;
import hieudx.fpoly.warehousemanager.databinding.ActivityLoginSignUpBinding;

public class Login_SignUp_Activity extends AppCompatActivity {
    private ActivityLoginSignUpBinding binding;
    private Login_SignUp_Adapter adapter;
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Login"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Sign Up"));
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new Login_SignUp_Adapter(getSupportFragmentManager(), this, binding.tabLayout.getTabCount());
        binding.viewPager.setAdapter(adapter);

        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        onAnimation();
    }

    private void onAnimation() {
        binding.fabFb.setTranslationY(300);
        binding.fabGoogle.setTranslationY(300);
        binding.fabTele.setTranslationY(300);
        binding.tabLayout.setTranslationY(300);

        binding.fabFb.setAlpha(v);
        binding.fabGoogle.setAlpha(v);
        binding.fabTele.setAlpha(v);
        binding.tabLayout.setAlpha(v);

        binding.fabFb.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(400).start();
        binding.fabGoogle.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(600).start();
        binding.fabTele.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(800).start();
        binding.tabLayout.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(1000).start();
    }

}