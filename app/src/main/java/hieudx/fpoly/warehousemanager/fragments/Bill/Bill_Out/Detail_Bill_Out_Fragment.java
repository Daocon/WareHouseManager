package hieudx.fpoly.warehousemanager.fragments.Bill.Bill_Out;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.adapters.bill.bill_out.Bill_Out_Detail_Adapter;
import hieudx.fpoly.warehousemanager.dao.Bill.Bill_Out_Dao;
import hieudx.fpoly.warehousemanager.dao.Delivery_Dao;
import hieudx.fpoly.warehousemanager.dao.User_Dao;
import hieudx.fpoly.warehousemanager.databinding.FragmentDetailBillOutBinding;
import hieudx.fpoly.warehousemanager.fragments.Bill.Bill_Fragment;
import hieudx.fpoly.warehousemanager.models.Delivery;
import hieudx.fpoly.warehousemanager.models.User;
import hieudx.fpoly.warehousemanager.models.bill.Bill_Out;
import hieudx.fpoly.warehousemanager.models.bill.Bill_out_detail;

public class Detail_Bill_Out_Fragment extends Fragment {
    private FragmentDetailBillOutBinding binding;
    private ArrayList<Bill_out_detail> list = new ArrayList<>();

    public Detail_Bill_Out_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBillOutBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        binding.imgBack.setOnClickListener(view -> loadFragment());
        if (bundle != null) {
            Bill_Out bill_out = (Bill_Out) bundle.getSerializable("data");
            binding.tvIdBillOut.setText("Mã hóa đơn: " + bill_out.getId());
            binding.tvDateTime.setText("Ngày xuất: " + bill_out.getDate_time());
            User_Dao user_dao = new User_Dao(getContext());
            User user = user_dao.getUserById(bill_out.getId_user());
            binding.tvIdUser.setText("Nhân viên: " + user.getName() + " - ID: " + user.getId());
            binding.tvAddress.setText("Địa chỉ nhận hàng: " + bill_out.getAddress());
            Delivery_Dao delivery_dao = new Delivery_Dao(getContext());
            Delivery delivery = delivery_dao.getDeliveryById(bill_out.getId_delivery());
            binding.tvDelivery.setText("Đvvc: " + delivery.getName());
            binding.tvTotal.setText(bundle.getString("sumTotal"));

            Bill_Out_Dao bill_out_dao = new Bill_Out_Dao(getContext());
            list.addAll(bill_out_dao.getListProductDetail(bill_out.getId()));
            Bill_Out_Detail_Adapter adapter = new Bill_Out_Detail_Adapter(getContext(), list);
            binding.rcv.setAdapter(adapter);

            binding.btnDelete.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa ?");
                builder.setNegativeButton("Có", (dialog, which) -> {
                    long check = bill_out_dao.delete(bill_out.getId());
                    if (check == 1) {
                        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        loadFragment();
                    } else {
                        Toast.makeText(getContext(), "Xóa lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Không", null);
                builder.show();
            });

        }
        return binding.getRoot();
    }

    private void loadFragment() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container_main, new Bill_Fragment()).commit();
    }
}