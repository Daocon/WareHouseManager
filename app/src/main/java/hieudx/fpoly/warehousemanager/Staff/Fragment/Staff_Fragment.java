package hieudx.fpoly.warehousemanager.Staff.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.Staff.Adapter.Staff_Adapter;
import hieudx.fpoly.warehousemanager.Staff.Dao.Staff_Dao;
import hieudx.fpoly.warehousemanager.Staff.Model.Staff;
import hieudx.fpoly.warehousemanager.databinding.BottomSheetStaffBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentStaffBinding;
import hieudx.fpoly.warehousemanager.databinding.UpdateStaffBinding;


public class Staff_Fragment extends Fragment {

    FragmentStaffBinding binding;

    RecyclerView rcvStaff;

    private Staff_Dao dao;

    private Staff_Adapter adapter;

    ArrayList<Staff> list = new ArrayList<>();

    public Staff_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStaffBinding.inflate(inflater,container,false);
        dao = new Staff_Dao(getContext());
        list = dao.getStaffList();
        RecyclerView rcvStaff = binding.rcvListStaff;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcvStaff.setLayoutManager(linearLayoutManager);
        adapter = new Staff_Adapter(list,getContext());
        rcvStaff.setAdapter(adapter);
        updateAddButtonVisibility();


        adapter.setOnItemClick(new Staff_Adapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                showDialogDetail(adapter.getStaffAtPosition(position));
            }
        });

        binding.btnSheetStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBottomSheet();
            }
        });

        binding.flAddStaff.setOnClickListener(v -> {
            fragmentAddStaff();
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            String namenv = bundle.getString("namenv");
            String sdtnv = bundle.getString("sdtnnv");
            String diachinv = bundle.getString("diachinnv");
            String luongnv = bundle.getString("luongnv");
            String work_day = bundle.getString("work_day");
            String coefficient = bundle.getString("coefficient");

            int luongInt = Integer.parseInt(luongnv);
            int workDayInt = Integer.parseInt(work_day);
            int coefficientInt = Integer.parseInt(coefficient);

            Staff newStaff = new Staff(namenv,sdtnv,diachinv,luongInt,workDayInt,coefficientInt);
            if (dao.insertStaff(newStaff)) {
                list.clear();
                list.addAll(dao.getStaffList());
                adapter.notifyDataSetChanged();
                updateAddButtonVisibility();
                General.showSuccessPopup(getContext(), "Thành công", "Bạn đã thêm nhân viên thành công", new OnDialogButtonClickListener() {
                    @Override
                    public void onDismissClicked(Dialog dialog) {
                        super.onDismissClicked(dialog);
                    }
                });
            }
        }
        return binding.getRoot();
    }



    private void fragmentAddStaff(){
        Staff_Add staffAdd = new Staff_Add();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container_main,staffAdd);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showDialogBottomSheet() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        BottomSheetStaffBinding layoutBinding = BottomSheetStaffBinding.inflate(getLayoutInflater());
        dialog.setContentView(layoutBinding.getRoot());
        layoutBinding.radioGroupStaff.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.sxZ_AStaff) {
                    Toast.makeText(getContext(), "Sắp xếp từ A-Z", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<Staff>() {
                        @Override
                        public int compare(Staff staff, Staff t1) {
                            return staff.getName().compareToIgnoreCase(t1.getName());
                        }
                    });
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Sắp xếp từ Z-A", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<Staff>() {
                        @Override
                        public int compare(Staff staff, Staff t1) {
                            return t1.getName().compareToIgnoreCase(staff.getName());
                        }
                    });
                    adapter.notifyDataSetChanged();
                }
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void updateAddButtonVisibility() {
        boolean isListEmpty = adapter.isListEmpty();
        if (isListEmpty) {
            binding.btnAddStaff.setVisibility(View.VISIBLE);
            binding.flAddStaff.setVisibility(View.GONE);
        } else {
            binding.btnAddStaff.setVisibility(View.GONE);
            binding.flAddStaff.setVisibility(View.VISIBLE);
        }
    }

    private void navigateToStaffUpdateFragment(Staff staff) {
        Staff_Update editFragment = new Staff_Update();
        Bundle bundle = new Bundle();
        bundle.putInt("staffID", staff.getId());

        editFragment.setArguments(bundle);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container_main, editFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showDialogDetail(Staff staff) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity) getActivity()).getLayoutInflater();



        UpdateStaffBinding updateStaffBinding = UpdateStaffBinding.inflate(inflater);
        builder.setView(updateStaffBinding.getRoot());

        Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        if (staff != null) {
            updateStaffBinding.txtNameStaff.setText("Tên: " + staff.getName());
            updateStaffBinding.txtSdtStaff.setText("Phone: " + staff.getPhone());
            updateStaffBinding.txtDiachiStaff.setText("Địa chỉ: " + staff.getAddress());
            updateStaffBinding.txtLuongStaff.setText("Lương: " + staff.getSalary() + " ");
            updateStaffBinding.txtWorkDay.setText("Ngày Công: " + staff.getWork_day() + " ");
            updateStaffBinding.txtHeSo.setText("Hệ Số: " + staff.getCoefficient() + " ");

        }

        updateStaffBinding.btnCall.setOnClickListener(v -> {
            Uri number = Uri.parse("tel:" + staff.getPhone());
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            startActivity(callIntent);
        });

        updateStaffBinding.btnMess.setOnClickListener(v -> {
            /*String emailAddress = "recipient@example.com";

            Uri emailUri = Uri.parse("mailto:" + emailAddress);

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO,emailUri);
                startActivity(emailIntent);*/

            Uri smsUri = Uri.parse("smsto:" + staff.getPhone());
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
            startActivity(smsIntent);

        });

        updateStaffBinding.btnUpdateStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToStaffUpdateFragment(staff);
                dialog.dismiss();
            }
        });


    }

}