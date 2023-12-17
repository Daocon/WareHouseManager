package hieudx.fpoly.warehousemanager.Product.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;

import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.MainActivity;
import hieudx.fpoly.warehousemanager.Product.Adapter.Product_Adapter;
import hieudx.fpoly.warehousemanager.Product.Dao.Product_Dao;
import hieudx.fpoly.warehousemanager.Product.Model.Product;
import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.BotSheetSortBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentProductBinding;

public class Product_Fragment extends Fragment {
    private FragmentProductBinding binding;
    private Product_Dao productDao;
    private Product_Adapter adapter;
    private ArrayList<Product> list = new ArrayList<>();

    public Product_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        onClickSort();
        onSearch();

        binding.fabAdd.setOnClickListener(view1 -> {
            General.loadFragment(getParentFragmentManager(), new Product_Add_Edit_Fragment(), null);
        });
    }

    private void onSearch() {
        MainActivity.binding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void onClickSort() {
        binding.imgSort.setOnClickListener(view -> {
            BotSheetSortBinding btnBinding = BotSheetSortBinding.inflate(getLayoutInflater());

            btnBinding.rdGr.setOnCheckedChangeListener(((radioGroup, i) -> {
                if (i == R.id.rd_sort_asc) {
                    Collections.sort(list, Product.sortByAscPrice);
                } else if (i == R.id.rd_sort_decs) {
                    Collections.sort(list, Product.sortByDescPrice);
                } else if (i == R.id.rd_sort_AZ) {
                    Collections.sort(list, Product.sortByNameAZ);
                } else if (i == R.id.rd_sort_ZA) {
                    Collections.sort(list, Product.sortByNameZA);
                }
                adapter.notifyDataSetChanged();
            }));
            General.onSettingsBotSheet(getContext(), btnBinding);
        });

    }

    private void init() {
        productDao = new Product_Dao(getContext());
        list = productDao.getProductList();
        General.transLayout(list, binding.btnAdd, binding.imgSort, binding.rcv, binding.fabAdd);
        adapter = new Product_Adapter(getContext(), list, getLayoutInflater(), getParentFragmentManager());
        binding.rcv.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        General.onStateIconBack(getActivity(), actionBar, getParentFragmentManager(), true);
    }
}