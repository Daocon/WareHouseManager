package hieudx.fpoly.warehousemanager.fragments.Bill.Bill_In;

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

public class Add_Bill_In_Fragment extends Fragment {
    private FragmentAddBillInBinding binding;
    private ArrayList<Product> list_product = new ArrayList<>();


    public Add_Bill_In_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBillInBinding.inflate(inflater, container, false);

        Product_Dao product_dao = new Product_Dao(getContext());
        list_product = product_dao.getProductList();
        Bill_In_Product_Add_Adapter adapter = new Bill_In_Product_Add_Adapter(getContext(), list_product);
        binding.rcvProduct.setAdapter(adapter);

        Bill_In_Dao bill_in_dao = new Bill_In_Dao(getContext());
        binding.tvIdBillIn.setText(bill_in_dao.genarateIdBillIn(list_product.size()));

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String date_time = dateFormat.format(currentDate);
        binding.tvDate.setText(date_time);

        binding.btnAdd.setOnClickListener(view -> {

        });
        return binding.getRoot();
    }

    //    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setCustomActionBar();
//    }
//
//    private void setCustomActionBar() {
//        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            // Xử lý khi nút back được nhấn
//            requireActivity().onBackPressed(); // Quay lại fragment trước đó
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}