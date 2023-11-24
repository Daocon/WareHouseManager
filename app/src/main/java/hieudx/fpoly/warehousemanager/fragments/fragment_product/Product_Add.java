package hieudx.fpoly.warehousemanager.fragments.fragment_product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.dao.Category_Dao;
import hieudx.fpoly.warehousemanager.databinding.FragmentProductAddBinding;
import hieudx.fpoly.warehousemanager.fragments.Product_Fragment;
import hieudx.fpoly.warehousemanager.models.Category;


public class Product_Add extends Fragment {

    FragmentProductAddBinding binding;
    String nameProduct, imgProduct;

    public Product_Add() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductAddBinding.inflate(inflater, container, false);

        // Inflate the layout for this fragment
        binding.edtTenSanPham.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    validNameProduct();
                }
            }
        });
        binding.edtAnhSanPham.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    validImgProduct();
                }
            }
        });
        binding.edtGiaBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tilGiaBan.setError("Không được nhập trường này");
            }
        });
        binding.edtSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tilNhapSol.setError("Không được nhập trường này");
            }
        });
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDsLoaiSP(),
                android.R.layout.simple_list_item_1,
                new String[]{"id"},
                new int[]{android.R.id.text1}
        );
        binding.spnLoaiSanPham.setAdapter(simpleAdapter);
        binding.btnXacNhanThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putData();
            }
        });
        binding.btnHuyThemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product_Fragment productFragment = new Product_Fragment();

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container_main, productFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return binding.getRoot();
    }

    private void putData() {

        validNameProduct();
        HashMap<String, Object> hss = (HashMap<String, Object>) binding.spnLoaiSanPham.getSelectedItem();
        int maLoai = (int) hss.get("id");
        if (binding.tilNhapTenSP.getError() == null && binding.tilNhapAnhSP.getError() == null) {
            Bundle bundle = new Bundle();
            bundle.putString("nameproduct", nameProduct);
            bundle.putInt("idcategory", maLoai);
            bundle.putString("imgproduct",imgProduct);
            Product_Fragment productFragment = new Product_Fragment();
            productFragment.setArguments(bundle);


            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag_container_main, productFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }


    }

    private void validNameProduct() {
        nameProduct = binding.edtTenSanPham.getText().toString();
        if (nameProduct.isEmpty()) {
            binding.tilNhapTenSP.setError("Không được để trống");
        } else {
            binding.tilNhapTenSP.setError(null);
        }
    }
    private void validImgProduct() {
        imgProduct = binding.edtAnhSanPham.getText().toString();
        if (imgProduct.isEmpty()) {
            binding.tilNhapAnhSP.setError("Không được để trống");
        } else {
            binding.tilNhapAnhSP.setError(null);
        }
    }

    private ArrayList<HashMap<String, Object>> getDsLoaiSP() {
        Category_Dao categoryDao = new Category_Dao(getContext());
        ArrayList<Category> list = categoryDao.getListCategory();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Category category : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("id", category.getId());
            hs.put("name", category.getName());
            listHM.add(hs);
        }
        return listHM;
    }
}