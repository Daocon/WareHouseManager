package hieudx.fpoly.warehousemanager.Bill.Fragment.Bill_In;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.Bill.Adapter.bill_in.Bill_In_Adapter;
import hieudx.fpoly.warehousemanager.Bill.Dao.Bill_In_Dao;
import hieudx.fpoly.warehousemanager.Bill.Model.Bill_In;
import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.MainActivity;
import hieudx.fpoly.warehousemanager.databinding.FragmentBillInBinding;

public class Bill_In_Fragment extends Fragment {
    private FragmentBillInBinding binding;
    private Bill_In_Dao dao;
    private ArrayList<Bill_In> list;
    private Bill_In_Adapter adapter;
    private FragmentManager fragmentManager;

    public Bill_In_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBillInBinding.inflate(inflater, container, false);
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
//        binding.imgSort.setOnClickListener(view -> {
//            Dialog dialog = new Dialog(getContext());
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            BotSheetSortBinding btnBinding = BotSheetSortBinding.inflate(getLayoutInflater());
//            dialog.setContentView(btnBinding.getRoot());
//
//            btnBinding.rdGr.setOnCheckedChangeListener(((radioGroup, i) -> {
//                if (i == R.id.rd_sort_asc) {
//                    Collections.sort(list, Bill_In.sortByAscSum);
//                } else if(i == R.id.rd_sort_decs){
//                    Collections.sort(list, Bill_In.sortByDescSum);
//                } else if (i == R.id.rd_sort_AZ){
//                    Collections.sort(list, Bill_In.sortByNameAZ);
//                } else if (i == R.id.rd_sort_ZA){
//                    Collections.sort(list, Bill_In.sortByNameZA);
//                }
//                adapter.notifyDataSetChanged();
//
//            }));
//
//            General.onSettingsBotSheet(dialog);
//            dialog.show();
//        });
    }

    private void init() {
        fragmentManager = requireActivity().getSupportFragmentManager();
        dao = new Bill_In_Dao(getContext());
        list = dao.getAll();
        General.transLayout(list, binding.btnAdd, binding.imgSort, binding.rcv, binding.fabAdd);
        adapter = new Bill_In_Adapter(getContext(), list, fragmentManager);
        binding.rcv.setAdapter(adapter);

        binding.btnAdd.setOnClickListener(view -> {
            General.loadFragment(fragmentManager, new Add_Bill_In_Fragment(), null);
        });

        binding.fabAdd.setOnClickListener(view -> {
            General.loadFragment(fragmentManager, new Add_Bill_In_Fragment(), null);
        });
    }
}