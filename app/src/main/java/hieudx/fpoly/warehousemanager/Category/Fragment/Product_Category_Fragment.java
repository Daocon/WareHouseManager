package hieudx.fpoly.warehousemanager.Category.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import hieudx.fpoly.warehousemanager.R;

public class Product_Category_Fragment extends Fragment {

    public Product_Category_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_, container, false);
    }
}