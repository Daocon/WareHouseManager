package hieudx.fpoly.warehousemanager.Bill.Fragment.Bill_Out;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import hieudx.fpoly.warehousemanager.R;

public class Add_Bill_Out_Fragment extends Fragment {

    public Add_Bill_Out_Fragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add__bill__out_, container, false);
    }
}