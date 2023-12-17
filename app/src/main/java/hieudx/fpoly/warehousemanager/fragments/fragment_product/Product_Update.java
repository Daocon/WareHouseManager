package hieudx.fpoly.warehousemanager.fragments.fragment_product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;

import hieudx.fpoly.warehousemanager.Category.Dao.Category_Dao;
import hieudx.fpoly.warehousemanager.Category.Model.Category;
import hieudx.fpoly.warehousemanager.Product.Dao.Product_Dao;
import hieudx.fpoly.warehousemanager.Product.Fragment.Product_Fragment;
import hieudx.fpoly.warehousemanager.Product.Model.Product;
import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.FragmentProductUpdateBinding;


public class Product_Update extends Fragment {


    public Product_Update() {
        // Required empty public constructor
    }

    FragmentProductUpdateBinding binding;
    String nameProduct, imgProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductUpdateBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        binding.edtTenSanPhamUp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    validNameProduct();
                }
            }
        });
        binding.edtAnhSanPhamUp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    validImgProduct();
                }
            }
        });
        binding.edtGiaBanUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tilGiaBanUp.setError("Không được nhập trường này");
            }
        });
        binding.edtSoLuongUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tilNhapSolUp.setError("Không được nhập trường này");
            }
        });
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDsLoaiSP(),
                android.R.layout.simple_list_item_1,
                new String[]{"id"},
                new int[]{android.R.id.text1}
        );
        binding.spnLoaiSanPhamUp.setAdapter(simpleAdapter);
        Bundle bundle = getArguments();
        if (binding.tilNhapTenUp.getError() == null && binding.tilNhapAnhSP.getError() == null) {
            if (bundle != null) {
                int productId = bundle.getInt("productId", -1);
                if (productId != -1) {
                    Product_Dao dao = new Product_Dao(getContext());
                    Product product = dao.getProductById(productId);
                    if (product != null) {
                        binding.edtTenSanPhamUp.setText(product.getName());
                        binding.edtAnhSanPhamUp.setText(product.getImg());
                        binding.edtGiaBanUp.setText(String.valueOf(product.getPrice()));
                        binding.edtSoLuongUp.setText(String.valueOf(product.getQuantity()));
                        binding.btnXacNhanUp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String name = binding.edtTenSanPhamUp.getText().toString();
                                String urlImg = binding.edtAnhSanPhamUp.getText().toString();
                                HashMap<String, Object> hss = (HashMap<String, Object>) binding.spnLoaiSanPhamUp.getSelectedItem();
                                int maLoai = (int) hss.get("id");
                                product.setName(name);
                                product.setPrice(0);
                                product.setQuantity(1);
                                product.setImg(urlImg);
                                product.setId_category(maLoai);
                                dao.updateProduct(product);
                                Snackbar.make(getView(), "Cập nhật thành công", Snackbar.LENGTH_SHORT).show();
                                Product_Fragment productFragment = new Product_Fragment();

                                FragmentManager fragmentManager = getParentFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frag_container_main, productFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        });
                    }
                }
            }
        }

            binding.btnHuyUpProduct.setOnClickListener(new View.OnClickListener() {
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

    private void validNameProduct() {
        nameProduct = binding.edtTenSanPhamUp.getText().toString();
        if (nameProduct.isEmpty()) {
            binding.tilNhapTenUp.setError("Không được để trống");
        } else {
            binding.tilNhapTenUp.setError(null);
        }
    }

    private void validImgProduct() {
        imgProduct = binding.edtAnhSanPhamUp.getText().toString();
        if (imgProduct.isEmpty()) {
            binding.tilNhapAnhSP.setError("Không được để trống");
        } else {
            binding.tilNhapAnhSP.setError(null);
        }
    }
}