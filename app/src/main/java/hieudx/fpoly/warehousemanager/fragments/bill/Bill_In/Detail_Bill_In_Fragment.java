package hieudx.fpoly.warehousemanager.fragments.bill.Bill_In;

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
import hieudx.fpoly.warehousemanager.adapters.bill.bill_in.Bill_In_Detail_Adapter;
import hieudx.fpoly.warehousemanager.dao.Bill.Bill_In_Dao;
import hieudx.fpoly.warehousemanager.dao.User_Dao;
import hieudx.fpoly.warehousemanager.databinding.FragmentDetailBillInBinding;
import hieudx.fpoly.warehousemanager.fragments.bill.Bill_Fragment;
import hieudx.fpoly.warehousemanager.models.User;
import hieudx.fpoly.warehousemanager.models.bill.Bill_In;
import hieudx.fpoly.warehousemanager.models.bill.Bill_in_detail;

public class Detail_Bill_In_Fragment extends Fragment {
    private FragmentDetailBillInBinding binding;
    private ArrayList<Bill_in_detail> list = new ArrayList<>();

    public Detail_Bill_In_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBillInBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();

        binding.imgBack.setOnClickListener(view -> loadFragment());
        if (bundle != null) {
            Bill_In bill_in = (Bill_In) bundle.getSerializable("data");
            binding.tvIdBillIn.setText("Mã hóa đơn: " + bill_in.getId());
            User_Dao user_dao = new User_Dao(getContext());
            User user = user_dao.getUserById(bill_in.getId_user());
            binding.tvNameUser.setText("Nhân viên: " + user.getName() + " - ID: " + user.getId());
            binding.tvDateTime.setText("Ngày nhập: " + bill_in.getDate_time());
            binding.tvTotal.setText(bundle.getString("sumTotal"));

            Bill_In_Dao bill_in_dao = new Bill_In_Dao(getContext());
            list.addAll(bill_in_dao.getListProductDetail(bill_in.getId()));
            Bill_In_Detail_Adapter adapter = new Bill_In_Detail_Adapter(getContext(), list);
            binding.rcv.setAdapter(adapter);

            binding.btnDelete.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa ?");
                builder.setNegativeButton("Có", (dialog, which) -> {
                    long check = bill_in_dao.delete(bill_in.getId());
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
