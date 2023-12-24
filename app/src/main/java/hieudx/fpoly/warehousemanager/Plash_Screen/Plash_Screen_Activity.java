package hieudx.fpoly.warehousemanager.Plash_Screen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import hieudx.fpoly.warehousemanager.databinding.ActivityPlashScreenBinding;

public class Plash_Screen_Activity extends AppCompatActivity {
    private ActivityPlashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvAppName.animate().translationY(350).setDuration(2000).setStartDelay(0);
        new Thread(() -> {
            try {
                Thread.sleep(11500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            runOnUiThread(() -> binding.tvAppName.animate().translationY(1600).setDuration(2000));
        }).start();
        binding.animLottie.animate().translationY(1400).setDuration(1000).setStartDelay(10000);
        binding.imgBgr.animate().translationY(-1600).setDuration(1000).setStartDelay(11000);

        SlidePager_Adapter slideAdapter =  new SlidePager_Adapter(getSupportFragmentManager());
        binding.liquidPager.setAdapter(slideAdapter);
    }
}