package hieudx.fpoly.warehousemanager.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import hieudx.fpoly.warehousemanager.adapters.Category_Adapter;
import hieudx.fpoly.warehousemanager.adapters.User_Adapter;
import hieudx.fpoly.warehousemanager.dao.Category_Dao;
import hieudx.fpoly.warehousemanager.databinding.BottomSheetCategoryBinding;
import hieudx.fpoly.warehousemanager.databinding.BottomSheetLayoutBinding;
import hieudx.fpoly.warehousemanager.databinding.DialogAddCategoryBinding;
import hieudx.fpoly.warehousemanager.databinding.DialogUpdateCategoryBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentCategoryBinding;
import hieudx.fpoly.warehousemanager.models.Category;
import hieudx.fpoly.warehousemanager.models.User;


public class Category_Fragment extends Fragment {
    private FragmentCategoryBinding binding;
    View cView;
    private Category_Dao categoryDao;
    private Category_Adapter adapter;
    private ArrayList<Category> list = new ArrayList<>();
    public static Fragment newInstance() {
        Category_Fragment fragment = new Category_Fragment();
        return fragment;
    }
    public Category_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater,container,false);
        cView = binding.getRoot();
        categoryDao = new Category_Dao(getContext());
        list = categoryDao.getListCategory();
        RecyclerView rcvListCategory = binding.rcvListCategory;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvListCategory.setLayoutManager(linearLayoutManager);
        adapter = new Category_Adapter(getContext(), list);
        rcvListCategory.setAdapter(adapter);
        if (adapter.checkAdd()) {
            binding.btnAddCategory.setVisibility(View.VISIBLE);
        } else {
            binding.btnAddCategory.setVisibility(View.GONE);
        }
        if (adapter.checkAdd()) {
            binding.fladdCategory.setVisibility(View.GONE);
        } else {
            binding.fladdCategory.setVisibility(View.VISIBLE);
        }

        binding.btnSheetCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBottomSheet();
            }
        });
        binding.fladdCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater = getLayoutInflater();
                DialogAddCategoryBinding addCategoryBinding = DialogAddCategoryBinding.inflate(layoutInflater);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(addCategoryBinding.getRoot());

                AlertDialog dialog = builder.create();
                dialog.show();
                addCategoryBinding.btnComfilmAddCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nameCategory =  addCategoryBinding.edtTenLoaiSp.getText().toString();
                        Category cate = new Category(nameCategory);
                        if (nameCategory.isEmpty()){
                            addCategoryBinding.tbAddCategory.setError("Không được để trống");
                        } else if (categoryDao.insertCategory(cate)) {
                            list.clear();
                            list.addAll(categoryDao.getListCategory());
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                addCategoryBinding.btnHuyXoaItemCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        return cView;
    }
    public void showDialogBottomSheet(){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        BottomSheetCategoryBinding layoutBinding = BottomSheetCategoryBinding.inflate(getLayoutInflater());
        dialog.setContentView(layoutBinding.getRoot());
        layoutBinding.radioGroupCategory.setOnCheckedChangeListener((new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.sxA_Z) {
                    Toast.makeText(Category_Fragment.this.getContext(), "Sắp xếp từ A - Z", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<Category>() {
                        @Override
                        public int compare(Category category, Category t1) {
                            return category.getName().compareToIgnoreCase(t1.getName());
                        }
                    });
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(Category_Fragment.this.getContext(), "Sắp xếp từ Z - A", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<Category>() {
                        @Override
                        public int compare(Category category, Category t1) {
                            return t1.getName().compareToIgnoreCase(category.getName());
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
    private void showDialogUpdateCategory(){



    }
}