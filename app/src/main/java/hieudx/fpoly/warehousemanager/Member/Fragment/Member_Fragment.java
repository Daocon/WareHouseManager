package hieudx.fpoly.warehousemanager.Member.Fragment;

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
import hieudx.fpoly.warehousemanager.Member.Adapter.User_Adapter;
import hieudx.fpoly.warehousemanager.Member.Dao.User_Dao;
import hieudx.fpoly.warehousemanager.Member.Model.User;
import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.BotSheetSortBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentMemberBinding;

public class Member_Fragment extends Fragment {
    private FragmentMemberBinding binding;
    private ArrayList<User> list;
    private User_Dao userDao;
    private User_Adapter adapter;

    public Member_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMemberBinding.inflate(inflater, container, false);
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
            BotSheetSortBinding btnBinding = BotSheetSortBinding.inflate(getLayoutInflater());

            btnBinding.rdSortAsc.setVisibility(View.GONE);
            btnBinding.rdSortDecs.setVisibility(View.GONE);

            btnBinding.rdGr.setOnCheckedChangeListener(((radioGroup, i) -> {
               if (i == R.id.rd_sort_AZ) {
                    Collections.sort(list, User.sortByNameAZ);
                } else if (i == R.id.rd_sort_ZA) {
                    Collections.sort(list, User.sortByNameZA);
                }
                adapter.notifyDataSetChanged();
            }));
            General.onSettingsBotSheet(getContext(), btnBinding);
        });
    }
    private void init() {
        userDao = new User_Dao(getContext());
        list = new ArrayList<>();
        for (User user : userDao.getAllUser()) {
            if (user.getRole() != 0) {
                list.add(user);
            }
        }
        General.transLayout(list, binding.btnAdd, binding.imgSort, binding.rcv, binding.fabAdd);
        adapter = new User_Adapter(getActivity(), list);
        binding.rcv.setAdapter(adapter);

        binding.fabAdd.setOnClickListener(view1 -> {
            General.loadFragment(getParentFragmentManager(), new Member_Add_Fragment(), null);
        });

        binding.btnAdd.setOnClickListener(view1 -> {
            General.loadFragment(getParentFragmentManager(), new Member_Add_Fragment(), null);
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        General.onStateIconBack(getActivity(), actionBar, getParentFragmentManager(), true);

        init();
    }
}