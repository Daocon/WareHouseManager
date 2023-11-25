package hieudx.fpoly.warehousemanager.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.adapters.Product_Adapter;
import hieudx.fpoly.warehousemanager.dao.Product_Dao;
import hieudx.fpoly.warehousemanager.databinding.BottomSheetProductBinding;
import hieudx.fpoly.warehousemanager.databinding.BottomSheetProductDetailsBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentProductBinding;
import hieudx.fpoly.warehousemanager.fragments.fragment_product.Product_Add;
import hieudx.fpoly.warehousemanager.fragments.fragment_product.Product_Update;
import hieudx.fpoly.warehousemanager.models.Product;

public class Product_Fragment extends Fragment {


    public Product_Fragment() {
    }

    private FragmentProductBinding fragmentProductBinding;
    private View bView;
    private Product_Dao productDao;
    private Product_Adapter adapter;
    private ArrayList<Product> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProductBinding = FragmentProductBinding.inflate(inflater, container, false);
        bView = fragmentProductBinding.getRoot();

        productDao = new Product_Dao(getContext());
        list = productDao.getProductList();
        RecyclerView rcvListProduct = fragmentProductBinding.rcvListProduct;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvListProduct.setLayoutManager(linearLayoutManager);
        adapter = new Product_Adapter(getContext(), list);
        rcvListProduct.setAdapter(adapter);
        updateAddButtonVisibility();
        fragmentProductBinding.btnSheetProduct.setOnClickListener(view -> showDialogBottomSheet());
        fragmentProductBinding.btnAddProduct.setOnClickListener(view -> {
            Product_Add productAdd = new Product_Add();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.frag_container_main, productAdd);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            String nameProduct = bundle.getString("nameproduct");
            int id_category = bundle.getInt("idcategory");
            String imgProduct = bundle.getString("imgproduct");
            Product newProduct = new Product(nameProduct, 0, 1, imgProduct, id_category, 1);
            if (productDao.insertProduct(newProduct)) {
                list.clear();
                list.addAll(productDao.getProductList());
                adapter.notifyDataSetChanged();
                updateAddButtonVisibility();
                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
        }
        adapter.setOnItemClick(new Product_Adapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                showDialogDetail(adapter.getProductAtPosition(position));
            }
        });
        return bView;
    }

    private void updateAddButtonVisibility() {
        boolean isListEmpty = adapter.isListEmpty();
        if (isListEmpty) {
            fragmentProductBinding.btnAddProduct.setVisibility(View.VISIBLE);
        } else {
            fragmentProductBinding.btnAddProduct.setVisibility(View.GONE);
        }
    }

    private void showDialogDetail(Product product) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        BottomSheetProductDetailsBinding detailsBinding = BottomSheetProductDetailsBinding.inflate(getLayoutInflater());
        dialog.setContentView(detailsBinding.getRoot());
//        detailsBinding.txtNameProductDetail.setText(list.get());
        if (product != null) {
            detailsBinding.txtNameProductDetail.setText(product.getName());
            detailsBinding.txtNamePriceProductDetail.setText("Giá: " + String.valueOf(product.getId()));
            detailsBinding.txtQuantityProductDetail.setText("Tồn kho"+String.valueOf(product.getQuantity()));
            detailsBinding.txtNameCategoryProductDetail.setText("Loại: "+ String.valueOf(product.getId_category()));

        }

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        detailsBinding.btnUpdateProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToProductUpdateFragment(product);
                dialog.dismiss();
            }
        });
        detailsBinding.btnDeleteProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check = productDao.deleteProduct(product.getId());
                switch (check) {
                    case 1:
                        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list.addAll(productDao.getProductList());
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                        updateAddButtonVisibility();
                        break;
                    case 0:
                        Toast.makeText(getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;
                    case -1:
                        Toast.makeText(getContext(), "Không thể xóa vì sản phẩm này đã được sử dụng trong hóa đơn", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });



    }
    private void navigateToProductUpdateFragment(Product product) {
        Product_Update editFragment = new Product_Update();
        Bundle bundle = new Bundle();
        bundle.putInt("productId", product.getId());

        editFragment.setArguments(bundle);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container_main, editFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void showDialogBottomSheet() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        BottomSheetProductBinding layoutBinding = BottomSheetProductBinding.inflate(getLayoutInflater());
        dialog.setContentView(layoutBinding.getRoot());
        layoutBinding.radioGroupProduct.setOnCheckedChangeListener((new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbAZProduct) {
                    Toast.makeText(getContext(), "Sắp xếp từ A - Z", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<Product>() {
                        @Override
                        public int compare(Product product, Product t1) {
                            return product.getName().compareToIgnoreCase(t1.getName());
                        }
                    });
                    adapter.notifyDataSetChanged();

                } else if (i == R.id.rbZAProduct) {
                    Toast.makeText(getContext(), "Sắp xếp từ Z - A", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<Product>() {
                        @Override
                        public int compare(Product product, Product t1) {
                            return t1.getName().compareToIgnoreCase(product.getName());
                        }
                    });
                    adapter.notifyDataSetChanged();
                } else if (i == R.id.rbGiaTangDan) {
                    Toast.makeText(getContext(), "Sắp xếp giá tăng dần", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<Product>() {
                        @Override
                        public int compare(Product product, Product t1) {
                            return Integer.compare(t1.getPrice(), product.getPrice());
                        }
                    });
                    adapter.notifyDataSetChanged();
                } else if (i == R.id.rbGiaGiamDan) {
                    Toast.makeText(getContext(), "Sắp xếp giá giảm dần", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<Product>() {
                        @Override
                        public int compare(Product product, Product t1) {
                            return Integer.compare(product.getPrice(), t1.getPrice());
                        }
                    });
                    adapter.notifyDataSetChanged();
                }
            }
        }));

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

}