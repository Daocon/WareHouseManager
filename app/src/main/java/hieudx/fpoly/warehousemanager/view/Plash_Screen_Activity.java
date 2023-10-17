package hieudx.fpoly.warehousemanager.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import hieudx.fpoly.warehousemanager.adapters.SlidePager_Adapter;
import hieudx.fpoly.warehousemanager.databinding.ActivityPlashScreenBinding;

public class Plash_Screen_Activity extends AppCompatActivity {
    private ActivityPlashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvAppName.animate().translationY(350).setDuration(2000).setStartDelay(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(11000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.tvAppName.animate().translationY(1600).setDuration(2000);

                    }
                });
            }
        }).start();
        binding.animLottie.animate().translationY(1400).setDuration(1000).setStartDelay(10000);
        binding.imgBgr.animate().translationY(-1600).setDuration(1000).setStartDelay(11000);

        SlidePager_Adapter slideAdapter =  new SlidePager_Adapter(getSupportFragmentManager());
        binding.liquidPager.setAdapter(slideAdapter);
//        binding.tvAppName.animate().translationY(1400).setDuration(2000).setStartDelay(11000);

//        binding.tvAppName.animate().translationY(1400).setDuration(1000).setStartDelay(10000);

//        Animation animation = AnimationUtils.loadAnimation(this,R.anim.plash_screen_anim);
//        binding.tvAppName.startAnimation(animation);
//        binding.animLottie.startAnimation(animation);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            }
//        }, 10000);
    }
}