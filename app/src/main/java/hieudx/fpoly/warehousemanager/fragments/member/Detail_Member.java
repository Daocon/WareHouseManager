package hieudx.fpoly.warehousemanager.fragments.member;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hieudx.fpoly.warehousemanager.databinding.FragmentDetailMemberBinding;

public class Detail_Member extends Fragment {
    private FragmentDetailMemberBinding binding;
    public Detail_Member(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailMemberBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }
}