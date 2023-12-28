package hieudx.fpoly.warehousemanager.Category.Fragment;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import java.util.ArrayList;
import java.util.Collections;

import hieudx.fpoly.warehousemanager.Category.Adapter.Category_Adapter;
import hieudx.fpoly.warehousemanager.Category.Dao.Category_Dao;
import hieudx.fpoly.warehousemanager.Category.Model.Category;
import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.MainActivity;
import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.BotSheetSortBinding;
import hieudx.fpoly.warehousemanager.databinding.DialogAddEditCategoryBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentCategoryBinding;


public class Category_Fragment extends Fragment {
    private FragmentCategoryBinding binding;
    private Category_Dao categoryDao;
    private Category_Adapter adapter;
    private ArrayList<Category> list = new ArrayList<>();

    public Category_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
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

    private void onAddCategory() {
        DialogAddEditCategoryBinding dialog_binding = DialogAddEditCategoryBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(dialog_binding.getRoot());
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        General.isEmptyValid(dialog_binding.edName, dialog_binding.name);
        dialog.show();
        dialog_binding.btnAdd.setOnClickListener(view1 -> {
            String name = dialog_binding.edName.getText().toString();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getContext(), "Hãy nhập dữ liệu", Toast.LENGTH_SHORT).show();
            } else {
                General.isContainSpecialChar(name, dialog_binding.name);
                if (dialog_binding.name.getError() == null) {
                    if (!categoryDao.isExistCategory(name)) {
                        dialog_binding.name.setError(null);
                        if (categoryDao.insertCategory(name) > 0) {
                            General.showSuccessPopup(getContext(), "Thành công", "Bạn đã thêm thành công", new OnDialogButtonClickListener() {
                                @Override
                                public void onDismissClicked(android.app.Dialog dialog) {
                                    super.onDismissClicked(dialog);
                                }
                            });
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dialog_binding.name.setError("Tên thể loại đã tồn tại");
                    }
                }
            }
        });
        dialog_binding.imgClose.setOnClickListener(view12 -> dialog.dismiss());
    }

    private void onClickSort() {
        binding.imgSort.setOnClickListener(view -> {
            BotSheetSortBinding btnBinding = BotSheetSortBinding.inflate(getLayoutInflater());

            btnBinding.rdSortAsc.setVisibility(View.GONE);
            btnBinding.rdSortDecs.setVisibility(View.GONE);

            btnBinding.rdGr.setOnCheckedChangeListener(((radioGroup, i) -> {
                if (i == R.id.rd_sort_AZ) {
                    Collections.sort(list, Category.sortByNameAZ);
                } else if (i == R.id.rd_sort_ZA) {
                    Collections.sort(list, Category.sortByNameZA);
                }
                adapter.notifyDataSetChanged();
            }));

            General.onSettingsBotSheet(getContext(), btnBinding);
        });
    }

    private void init() {
        categoryDao = new Category_Dao(getContext());
        list = categoryDao.getListCategory();
        General.transLayout(list, binding.btnAdd, binding.imgSort, binding.rcv, binding.fabAdd);
        adapter = new Category_Adapter(getContext(), list, getParentFragmentManager());
        binding.rcv.setAdapter(adapter);

        binding.fabAdd.setOnClickListener(view -> {
            onAddCategory();
        });

        binding.btnAdd.setOnClickListener(view -> {
            onAddCategory();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }
}