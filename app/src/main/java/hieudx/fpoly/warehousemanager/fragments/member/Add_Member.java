package hieudx.fpoly.warehousemanager.fragments.member;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.intellij.lang.annotations.Pattern;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.FragmentAddMemberBinding;

public class Add_Member extends Fragment {
    private FragmentAddMemberBinding binding;

    public Add_Member(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddMemberBinding.inflate(inflater,container,false);

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.txtName.getText().toString();
                String username = binding.txtUsername.getText().toString();
                String phone = binding.txtPhone.getText().toString();
                String email = binding.txtEmail.getText().toString();
                String password = binding.txtPassword.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập họ và tên", Toast.LENGTH_SHORT).show();
                    return;
                }
                int nameLength = name.length();
                if (nameLength < 2) {
                    Toast.makeText(getActivity(), "Tên phải có ít nhất 2 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (name.matches("[a-zA-Z]+")) {
                    Toast.makeText(getActivity(), "Tên không được chứa số", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (username.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (username.contains(" ")) {
                    Toast.makeText(getActivity(), "Tên đăng nhập không được chứa khoảng trắng", Toast.LENGTH_SHORT).show();
                    return;
                }

                int usernameLength = username.length();
                if (usernameLength < 5 || usernameLength > 15) {
                    Toast.makeText(getActivity(), "Tên đăng nhập phải có từ 5 đến 15 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.contains(" ")) {
                    Toast.makeText(getActivity(), "Số điện thoại không được chứa khoảng trắng", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!phone.startsWith("0")) {
                    Toast.makeText(getActivity(), "Số điện thoại phải bắt đầu bằng số 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    Toast.makeText(getActivity(), "Vui lòng nhập đúng định dạng email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.contains(" ")) {
                    Toast.makeText(getActivity(), "Mật khẩu không được chứa khoảng trắng", Toast.LENGTH_SHORT).show();
                    return;
                }

                int passwordLength = password.length();
                if (passwordLength < 5 || passwordLength >= 10) {
                    Toast.makeText(getActivity(), "Mật khẩu phải có ít nhất 5 ký tự và nhỏ hơn 10 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo Bundle và đưa dữ liệu vào Bundle
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("username", username);
                bundle.putString("phone", phone);
                bundle.putString("email", email);
                bundle.putString("password", password);

                Member_Fragment memberFragment = new Member_Fragment();
                memberFragment.setArguments(bundle);

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container_main, memberFragment);
                fragmentTransaction.commit();
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Member_Fragment memberFragment = new Member_Fragment();

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container_main, memberFragment);
                fragmentTransaction.commit();
            }
        });


        return binding.getRoot();
    }
}