package hieudx.fpoly.warehousemanager.Supplier.Fragment;

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

import java.util.ArrayList;
import java.util.Collections;

import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.MainActivity;
import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.Supplier.Adapter.Supplier_Adapter;
import hieudx.fpoly.warehousemanager.Supplier.Dao.Supplier_Dao;
import hieudx.fpoly.warehousemanager.Supplier.Model.Supplier;
import hieudx.fpoly.warehousemanager.databinding.BotSheetSortBinding;
import hieudx.fpoly.warehousemanager.databinding.DialogAddEditDeliveryBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentSupplierBinding;

public class Supplier_Fragment extends Fragment {
    private FragmentSupplierBinding binding;
    private Supplier_Dao dao;
    private Supplier_Adapter adapter;
    private ArrayList<Supplier> list = new ArrayList<>();

    public Supplier_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupplierBinding.inflate(inflater, container, false);
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
            btnBinding.rdSortDecs.setVisibility(View.GONE);
            btnBinding.rdSortAsc.setVisibility(View.GONE);

            btnBinding.rdGr.setOnCheckedChangeListener(((radioGroup, i) -> {
                if (i == R.id.rd_sort_AZ) {
                    Collections.sort(list, Supplier.sortByNameAZ);
                } else if (i == R.id.rd_sort_ZA) {
                    Collections.sort(list, Supplier.sortByNameZA);
                }
                adapter.notifyDataSetChanged();
            }));
            General.onSettingsBotSheet(getContext(), btnBinding);
        });
    }

    private void init() {
        dao = new Supplier_Dao(getContext());
        list = dao.getAll();
        General.transLayout(list, binding.btnAdd, binding.imgSort, binding.rcv, binding.fabAdd);
        adapter = new Supplier_Adapter(list, getContext(), getLayoutInflater());
        binding.rcv.setAdapter(adapter);

        binding.fabAdd.setOnClickListener(view -> {
            onAddDelivery();
        });

        binding.btnAdd.setOnClickListener(view -> {
            onAddDelivery();
        });
    }

    private void onAddDelivery() {
        DialogAddEditDeliveryBinding dialog_binding = DialogAddEditDeliveryBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(dialog_binding.getRoot());
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog_binding.tvTitleDialog.setText("Thêm nhà cung cấp");
        dialog_binding.tvName.setText("Tên nhà cung cấp");
        dialog_binding.tvPrice.setText("Địa chỉ");

        General.isEmptyValid(dialog_binding.edName, dialog_binding.name);
        General.isEmptyValid(dialog_binding.edPhone, dialog_binding.phone);
        General.isEmptyValid(dialog_binding.edPrice, dialog_binding.price);
        General.isEmptyValid(dialog_binding.edTaxCode, dialog_binding.taxCode);

        dialog.show();

        dialog_binding.btnAdd.setOnClickListener(view -> {
            String name = dialog_binding.edName.getText().toString().trim();
            String phone = dialog_binding.edPhone.getText().toString().trim();
            String add = dialog_binding.edPrice.getText().toString().trim();
            String taxCode = dialog_binding.edTaxCode.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(add) || TextUtils.isEmpty(taxCode)) {
                Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
            } else {
                General.validPhone(phone, dialog_binding.phone);
                General.isContainSpecialChar(name, dialog_binding.name);
                General.isContainSpecialChar(add, dialog_binding.price);
                General.isContainSpecialChar(taxCode, dialog_binding.taxCode);
                if (dialog_binding.name.getError() == null
                        && dialog_binding.phone.getError() == null
                        && dialog_binding.price.getError() == null
                        && dialog_binding.taxCode.getError() == null) {
                    if (!dao.isExistSupplier(name)) {
                        dialog_binding.name.setError(null);
                        Supplier supplier = new Supplier(name, phone, add, taxCode);
                        if (dao.insert(supplier) > 0) {
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dialog_binding.name.setError("Tên nhà cung cấp đã tồn tại");
                    }
                }
            }
        });
        dialog_binding.imgClose.setOnClickListener(view -> dialog.dismiss());

    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

}