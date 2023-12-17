package hieudx.fpoly.warehousemanager.Bill.Fragment.Bill_Out;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Collections;

import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.MainActivity;
import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.Bill.Adapter.bill_out.Bill_Out_Adapter;
import hieudx.fpoly.warehousemanager.Bill.Dao.Bill_Out_Dao;
import hieudx.fpoly.warehousemanager.databinding.BotSheetSortBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentBillOutBinding;
import hieudx.fpoly.warehousemanager.Bill.Model.Bill_Out;

public class Bill_Out_Fragment extends Fragment {
    private FragmentBillOutBinding binding;
    private Bill_Out_Dao dao;
    private ArrayList<Bill_Out> list;
    private Bill_Out_Adapter adapter;
    private FragmentManager fragmentManager;


    public Bill_Out_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBillOutBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        onClickSort();
        onSearch();
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
            Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            BotSheetSortBinding btnBinding = BotSheetSortBinding.inflate(getLayoutInflater());
            dialog.setContentView(btnBinding.getRoot());

            btnBinding.rdGr.setOnCheckedChangeListener(((radioGroup, i) -> {
                if (i == R.id.rd_sort_asc) {
                    Collections.sort(list, Bill_Out.sortByAscSum);
                } else if (i == R.id.rd_sort_decs) {
                    Collections.sort(list, Bill_Out.sortByDescSum);
                } else if (i == R.id.rd_sort_AZ) {
                    Collections.sort(list, Bill_Out.sortByNameAZ);
                } else if (i == R.id.rd_sort_ZA) {
                    Collections.sort(list, Bill_Out.sortByNameZA);
                }
                adapter.notifyDataSetChanged();
            }));

            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        });
    }

    private void init() {
        fragmentManager = requireActivity().getSupportFragmentManager();
        dao = new Bill_Out_Dao(getContext());
        list = dao.getAll();
        General.transLayout(list, binding.btnAdd, binding.imgSort, binding.rcv, binding.fabAdd);
        adapter = new Bill_Out_Adapter(getContext(), list, fragmentManager);
        binding.rcv.setAdapter(adapter);

        binding.btnAdd.setOnClickListener(view -> {
            General.loadFragment(fragmentManager, new Add_Bill_Out_Fragment(), null);
        });

        binding.fabAdd.setOnClickListener(view -> {
            General.loadFragment(fragmentManager, new Add_Bill_Out_Fragment(), null);
        });
    }

}