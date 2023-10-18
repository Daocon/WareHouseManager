package hieudx.fpoly.warehousemanager.fragments.plash_screen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.view.Login_SignUp_Activity;

public class First_OnBoarding_Fragment extends Fragment {

    public First_OnBoarding_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first__on_boarding_, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvSkipFirst = v.findViewById(R.id.tvSkipFirst);
        tvSkipFirst.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), Login_SignUp_Activity.class));
        });
        return v;
    }
}