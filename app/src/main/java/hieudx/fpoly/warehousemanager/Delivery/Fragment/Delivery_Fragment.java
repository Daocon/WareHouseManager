package hieudx.fpoly.warehousemanager.Delivery.Fragment;

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

import hieudx.fpoly.warehousemanager.Delivery.Adapter.Delivery_Adapter;
import hieudx.fpoly.warehousemanager.Delivery.Dao.Delivery_Dao;
import hieudx.fpoly.warehousemanager.Delivery.Model.Delivery;
import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.MainActivity;
import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.BotSheetSortBinding;
import hieudx.fpoly.warehousemanager.databinding.DialogAddEditDeliveryBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentDeliveryBinding;


public class Delivery_Fragment extends Fragment {
    private FragmentDeliveryBinding binding;
    private Delivery_Dao dao;
    private Delivery_Adapter adapter;
    private ArrayList<Delivery> list = new ArrayList<>();

    public Delivery_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDeliveryBinding.inflate(inflater, container, false);
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

            btnBinding.rdGr.setOnCheckedChangeListener(((radioGroup, i) -> {
                if (i == R.id.rd_sort_asc) {
                    Collections.sort(list, Delivery.sortByAscPrice);
                } else if (i == R.id.rd_sort_decs) {
                    Collections.sort(list, Delivery.sortByDescPrice);
                } else if (i == R.id.rd_sort_AZ) {
                    Collections.sort(list, Delivery.sortByNameAZ);
                } else if (i == R.id.rd_sort_ZA) {
                    Collections.sort(list, Delivery.sortByNameZA);
                }
                adapter.notifyDataSetChanged();
            }));

            General.onSettingsBotSheet(getContext(), btnBinding);
        });
    }
    private void onAddDelivery() {
        DialogAddEditDeliveryBinding dialog_binding = DialogAddEditDeliveryBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(dialog_binding.getRoot());
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        General.isEmptyValid(dialog_binding.edName, dialog_binding.name);
        General.isEmptyValid(dialog_binding.edPhone, dialog_binding.phone);
        General.isEmptyValid(dialog_binding.edPrice, dialog_binding.price);
        General.isEmptyValid(dialog_binding.edTaxCode, dialog_binding.taxCode);

        dialog.show();

        dialog_binding.btnAdd.setOnClickListener(view -> {
            String name = dialog_binding.edName.getText().toString().trim();
            String phone = dialog_binding.edPhone.getText().toString().trim();
            String price = dialog_binding.edPrice.getText().toString().trim();
            String taxCode = dialog_binding.edTaxCode.getText().toString().trim();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(price) || TextUtils.isEmpty(taxCode)) {
                Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
            } else {
                General.isContainSpecialChar(name, dialog_binding.name);
                General.validPhone(phone, dialog_binding.phone);

                if (price.contains(" "))
                    dialog_binding.price.setError("Không được chứa khoảng trắng");
                else if (price.matches("[a-zA-Z]+"))
                    dialog_binding.price.setError("Không được nhập chữ");
                else if (price.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?~].*"))
                    dialog_binding.price.setError("Không được chứa ký tự đặc biệt");
                else if (Double.parseDouble(price) < 0)
                    dialog_binding.price.setError("Giá phải > 0");
                else dialog_binding.price.setError(null);

                General.isContainSpecialChar(taxCode, dialog_binding.taxCode);
                if (dialog_binding.name.getError() == null
                        && dialog_binding.phone.getError() == null
                        && dialog_binding.price.getError() == null
                        && dialog_binding.taxCode.getError() == null) {
                    if (!dao.isExistDelivery(name)) {
                        dialog_binding.name.setError(null);
                        Delivery delivery = new Delivery(name, phone, Double.parseDouble(price), taxCode);
                        if (dao.insertDelivery(delivery) > 0) {
                            General.showSuccessPopup(getContext(), "Thành công", "Bạn đã thêm đơn vị vận chuyển thành công", new OnDialogButtonClickListener() {
                                @Override
                                public void onDismissClicked(android.app.Dialog dialog) {
                                    super.onDismissClicked(dialog);
                                    onResume();
                                }
                            });
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dialog_binding.name.setError("Tên đvvc đã tồn tại");
                    }
                }
            }
        });
        dialog_binding.imgClose.setOnClickListener(view -> dialog.dismiss());
    }
    private void init () {
        dao = new Delivery_Dao(getContext());
        list = dao.getListDelivery();
        General.transLayout(list, binding.btnAdd, binding.imgSort, binding.rcv, binding.fabAdd);
        adapter = new Delivery_Adapter(list, getContext(), getLayoutInflater());
        binding.rcv.setAdapter(adapter);

        binding.fabAdd.setOnClickListener(view -> {
            onAddDelivery();
        });

        binding.btnAdd.setOnClickListener(view -> {
            onAddDelivery();
        });
    }
    @Override
    public void onResume () {
        super.onResume();
        init();
    }
}
