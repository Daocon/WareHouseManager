package hieudx.fpoly.warehousemanager.fragments.Bill.Bill_In;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hieudx.fpoly.warehousemanager.adapters.bill.bill_in.Bill_In_Product_Add_Adapter;
import hieudx.fpoly.warehousemanager.dao.Bill.Bill_In_Dao;
import hieudx.fpoly.warehousemanager.dao.Product_Dao;
import hieudx.fpoly.warehousemanager.databinding.FragmentAddBillInBinding;
import hieudx.fpoly.warehousemanager.models.Product;
import hieudx.fpoly.warehousemanager.models.bill.Bill_In;
import hieudx.fpoly.warehousemanager.models.bill.Bill_in_detail;

public class Add_Bill_In_Fragment extends Fragment {
    private FragmentAddBillInBinding binding;
    private ArrayList<Product> list_product = new ArrayList<>();
    private ArrayList<Product> list_product_checked = new ArrayList<>();

    public Add_Bill_In_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBillInBinding.inflate(inflater, container, false);

        Product_Dao product_dao = new Product_Dao(getContext());
        list_product = product_dao.getProductList();
        Bill_In_Product_Add_Adapter adapter = new Bill_In_Product_Add_Adapter(getContext(), list_product, this::updateCount);
        binding.rcvProduct.setAdapter(adapter);

        Bill_In bill_in = new Bill_In();
        Bill_In_Dao bill_in_dao = new Bill_In_Dao(getContext());
        bill_in.setId(bill_in_dao.genarateIdBillIn(list_product.size()));
        binding.tvIdBillIn.setText(bill_in.getId());

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        bill_in.setDate_time(dateFormat.format(currentDate));
        binding.tvDate.setText(bill_in.getDate_time());

        SharedPreferences shared = requireContext().getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
        bill_in.setId_user(shared.getInt("id", 0));
        binding.btnAdd.setOnClickListener(view -> {
            bill_in_dao.insert(bill_in);
            for (Product product : list_product_checked) {
//                Log.d("tag_kiemTra", "onCreateView: "+product.getPrice());
                Bill_in_detail bill_in_detail = new Bill_in_detail(product.getPrice(), product.getQuantity(), product.getId(), bill_in.getId());
//                Log.d("uuuuuuuuuuuuu", "onCreateView: "+bill_in_detail.getPrice()+" - "+bill_in_detail.getQuantity()+ " - "+bill_in_detail.getId_product()+ " - "+bill_in_detail.getId_bill_in());
                bill_in_dao.insertDetail(bill_in_detail);
//                String quantity = String.valueOf(product.getQuantity());
//                String price = String.valueOf(product.getPrice());
//                int productId = product.getId();
//                Log.d("qqqqqqqqqqqq", "onCreateView: " + list_product_checked.size());
//                // Sử dụng thông tin từ mỗi item
//                Log.d("itemmmmmmm", "Checked - Quantity: " + quantity + ", Price: " + price + ", Product ID: " + productId + ", size: " + list_product_checked.size());
            }
        });
        return binding.getRoot();
    }

    private void updateCount(ArrayList<Product> list_checked) {
        list_product_checked.clear();
        list_product_checked.addAll(list_checked);
    }
}