package hieudx.fpoly.warehousemanager.Bill.Fragment.Bill_Out;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import hieudx.fpoly.warehousemanager.Bill.Adapter.Bill_Product_Add_Adapter;
import hieudx.fpoly.warehousemanager.Bill.Dao.Bill_Out_Dao;
import hieudx.fpoly.warehousemanager.Bill.Model.Bill_Out;
import hieudx.fpoly.warehousemanager.Bill.Model.Bill_out_detail;
import hieudx.fpoly.warehousemanager.Delivery.Dao.Delivery_Dao;
import hieudx.fpoly.warehousemanager.Delivery.Model.Delivery;
import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.Product.Dao.Product_Dao;
import hieudx.fpoly.warehousemanager.Product.Model.Product;
import hieudx.fpoly.warehousemanager.databinding.FragmentAddBillOutBinding;

public class Add_Bill_Out_Fragment extends Fragment {
    private FragmentAddBillOutBinding binding;
    private ArrayList<Product> list_product = new ArrayList<>();
    private ArrayList<Product> list_product_checked = new ArrayList<>();
    private ArrayList<Bill_Out> list_bill_out = new ArrayList<>();

    public Add_Bill_Out_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBillOutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        General.onStateIconBack(getActivity(), actionBar, getParentFragmentManager(), false);

        Product_Dao product_dao = new Product_Dao(getContext());
        list_product = product_dao.getProductList();

        General.onLoadSpinner(getContext(), getListDelivery(), binding.spnDelivery);
        Bill_Product_Add_Adapter adapter = new Bill_Product_Add_Adapter(getContext(), list_product, this::updateCount);
        binding.rcvProduct.setAdapter(adapter);

        Bill_Out bill_out = new Bill_Out();
        Bill_Out_Dao bill_out_dao = new Bill_Out_Dao(getContext());
        list_bill_out = bill_out_dao.getAll();
        bill_out.setId(General.genarateIdBill(list_bill_out.size(),"PX", getContext()));
        binding.tvIdBillIn.setText(bill_out.getId());

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        bill_out.setDate_time(dateFormat.format(currentDate));
        binding.tvDate.setText(bill_out.getDate_time());

        SharedPreferences shared = requireContext().getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
        bill_out.setId_user(shared.getInt("id", 0));

        General.isEmptyValid(binding.edAdd, binding.add);

        binding.btnAdd.setOnClickListener(view1 -> {
            if (list_product_checked.isEmpty()){
                Toast.makeText(getContext(), "Vui lòng chọn sản phẩm", Toast.LENGTH_SHORT).show();
            } else if (binding.add.getError() == null){
                String add = binding.edAdd.getText().toString().trim();
                if (TextUtils.isEmpty(add)){
                    Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    bill_out.setAddress(binding.edAdd.getText().toString());
                    HashMap<String, Object> hm_deli = (HashMap<String, Object>) binding.spnDelivery.getSelectedItem();
                    bill_out.setId_delivery((int) hm_deli.get("id"));
                    bill_out_dao.insert(bill_out);
                    for (Product product : list_product_checked) {
//                        Log.d("zzzzzzzz", "onCreateView: "+product.getId_supplier());
                        Bill_out_detail bill_out_detail = new Bill_out_detail(product.getPrice(), product.getQuantity(), product.getId(), bill_out.getId());
//                        Log.d("uuuuuuuuuuuuu", "onCreateView: "+bill_out_detail.getPrice()+" - "+bill_out_detail.getQuantity()+ " - "+bill_out_detail.getId_product()+ " - "+bill_out_detail.getId_bill_out());
                        boolean check = bill_out_dao.insertDetail(bill_out_detail);
                        if (check) {
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            getParentFragmentManager().popBackStack();
                        }
                    }
                    bill_out_dao.updateSumTotal(bill_out.getId());
                }

            }
        });
    }

    private ArrayList<HashMap<String, Object>> getListDelivery() {
        Delivery_Dao delivery_dao = new Delivery_Dao(getContext());
        ArrayList<Delivery> list = delivery_dao.getListDelivery();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Delivery deli : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("id", deli.getId());
            hs.put("name", deli.getName());
            listHM.add(hs);
        }
        return listHM;
    }
    private void updateCount(ArrayList<Product> list_checked) {
        list_product_checked.clear();
        list_product_checked.addAll(list_checked);

        Double total = 0.0;
        for (Product prod : list_product_checked) {
            total = total + (prod.getPrice() * prod.getQuantity());
        }
        binding.tvTotal.setText(General.formatSumVND(total)+" đ");
    }
}