package hieudx.fpoly.warehousemanager.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.FragmentAccountBinding;
import hieudx.fpoly.warehousemanager.databinding.FragmentDetailMemberBinding;

public class Account_Fragment extends Fragment {
    private FragmentAccountBinding binding;

    public Account_Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);

        binding.swNotifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), "Trạng thái thông báo: Bật", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Trạng thái thông báo: Tắt", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.swMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), "Chế độ tối: Bật", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Chế độ tối: Tắt", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.swAutoApdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), "Tự động cập nhật: Bật", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Tự động cập nhật: Tắt", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.rlEditInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Chỉnh sửa thông tin", Toast.LENGTH_SHORT).show();
            }
        });
        binding.rlChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });

        binding.rlAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Về chúng tôi", Toast.LENGTH_SHORT).show();
            }
        });
        binding.rlPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Chính sách bảo mật", Toast.LENGTH_SHORT).show();
            }
        });
        binding.rlError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Báo lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        binding.rlHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Trợ giúp và phản hồi", Toast.LENGTH_SHORT).show();
            }
        });
        binding.rlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Chia sẻ", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}