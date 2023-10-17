package hieudx.fpoly.warehousemanager.fragments.plash_screen_frag;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.view.Login_SignUp_Activity;

public class Third_OnBoarding_Fragment extends Fragment {

    public Third_OnBoarding_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_third__on_boarding_, container, false);

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Login_SignUp_Activity.class));
            }
        });
        return v;

    }
}