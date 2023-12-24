package hieudx.fpoly.warehousemanager.Delivery.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.FragmentDeliveryAddBinding;
import hieudx.fpoly.warehousemanager.Delivery.Fragment.Delivery_Fragment;


public class Delivery_add extends Fragment {


    public Delivery_add() {
        // Required empty public constructor
    }

    private FragmentDeliveryAddBinding binding;
    String nameDvvc, soDienThoai, maSoThue;
    int gia;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDeliveryAddBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        binding.edtTenDvvc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    validName();
                }
            }
        });
        binding.edtSdtDvvc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    validSDT();
                }
            }
        });
        binding.edtGiaDvvc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    validGia();
                }
            }
        });
        binding.edtMaSoThueDvvc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    validMaSoThue();
                }
            }
        });
        binding.btnThemDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putData();
            }
        });
        return binding.getRoot();
    }
        private void putData(){
        validName();
        validGia();
        validSDT();
        validMaSoThue();
        if (binding.tilTenDvvc.getError() == null &&
                binding.tilSdtDvvc.getError() == null &&
                binding.tilGiaDvvc.getError() == null &&
                binding.tilMaSoThueDvvc.getError() == null){
            Bundle bundle = new Bundle();
            bundle.putString("namedvvc",nameDvvc);
            bundle.putInt("giadvvc",gia);
            bundle.putString("sdtdvvc",soDienThoai);
            bundle.putString("masothue",maSoThue);
            Delivery_Fragment deliveryFragment = new Delivery_Fragment();
            deliveryFragment.setArguments(bundle);
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag_container_main, deliveryFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        }

    private void validName() {
        nameDvvc = binding.edtTenDvvc.getText().toString();
        if (nameDvvc.isEmpty()) {
            binding.tilTenDvvc.setError("Không được để trống");
        } else {
            binding.tilTenDvvc.setError(null);
        }
    }
    private void validSDT() {
        soDienThoai = binding.edtSdtDvvc.getText().toString();
        if (soDienThoai.isEmpty()) {
            binding.tilSdtDvvc.setError("Không được để trống");
        }else if (!soDienThoai.matches("^0\\d{9}$")) {
            binding.tilSdtDvvc.setError("Số điện thoại không hợp lệ");
        } else {
            binding.tilSdtDvvc.setError(null);
        }
    }
    private void validGia(){

        try {
            gia = Integer.parseInt(binding.edtGiaDvvc.getText().toString());
            if (gia < 0) {
                binding.tilGiaDvvc.setError("Giá phải lớn hơn hoăc bằng 0");
            } else {
                binding.tilGiaDvvc.setError(null);
            }
        } catch (NumberFormatException e) {
            binding.tilGiaDvvc.setError("Giá phải là số");
        }
    }
    private void validMaSoThue() {
        maSoThue = binding.edtMaSoThueDvvc.getText().toString();
        if (maSoThue.isEmpty()) {
            binding.tilMaSoThueDvvc.setError("Không được để trống");
        } else {
            binding.tilMaSoThueDvvc.setError(null);
        }
    }
}