package hieudx.fpoly.warehousemanager;

import android.text.TextUtils;

import com.google.android.material.textfield.TextInputLayout;

public class General {

    public static void validName(String value, TextInputLayout field) {
        if (value.matches(".*\\d.*")) {
            field.setError("Tên không được chứa số");
        } else if (TextUtils.isEmpty(value.trim())) {
            field.setError("Hãy nhập tên");
        } else {
            field.setError(null);
        }
    }

    public static void validPass(String value, String value2, TextInputLayout field) {
        if (!value.equals(value2)) {
            field.setError("Mật khẩu không trùng khớp");
        } else if (TextUtils.isEmpty(value.trim()) || TextUtils.isEmpty(value2.trim())) {
            field.setError("Hãy nhập mật khẩu");
        } else {
            field.setError(null);
        }
    }

    public static void validUsername(String value, TextInputLayout field) {
        if (TextUtils.isEmpty(value.trim())) {
            field.setError("Hãy nhập tên đăng nhập");
        } else if (value.trim().contains(" ")) {
            field.setError("Tên đăng nhập không được chứa khoảng trắng");
        }
    }

    public static void validEmail(String value, TextInputLayout field) {
        if (TextUtils.isEmpty(value.trim())) {
            field.setError("Hãy nhập email");
        } else if (value.trim().contains(" ")) {
            field.setError("Email không được chứa khoảng trắng");
        } else if (!value.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            field.setError("Hãy nhập đúng định dạng email");
        } else {
            field.setError(null);
        }
    }

    public static void validPhone(String value, TextInputLayout field) {
        if (TextUtils.isEmpty(value.trim())) {
            field.setError("Hãy nhập số điện thoại");
        } else if (value.length() < 10 || value.length() > 10) {
            field.setError("Số điện thoại phải chứa 10 số");
        } else if (value.contains(" ")) {
            field.setError("Số điện thoại không được chứa khoảng trắng");
        } else if (!value.startsWith("0")) {
            field.setError("Số điện thoại phải bắt đầu bằng số 0");
        } else {
            field.setError(null);
        }
    }

//    public static void init(Fragment fragment, RecyclerView.Adapter adapter) {
//        fragment.requireFragmentManager() = fragment.requ.getSupportFragmentManager();
//        dao = new Bill_In_Dao(getContext());
//        list = dao.getAll();
//        if (!list.isEmpty()) {
//            binding.btnAdd.setVisibility(View.GONE);
//            binding.imgSort.setVisibility(View.VISIBLE);
//            binding.rcv.setVisibility(View.VISIBLE);
//            binding.fabAdd.setVisibility(View.VISIBLE);
//            adapter = new Bill_In_Adapter(getContext(), list, fragmentManager);
//            binding.rcv.setAdapter(adapter);
//        } else {
//            binding.btnAdd.setVisibility(View.VISIBLE);
//            binding.imgSort.setVisibility(View.GONE);
//            binding.rcv.setVisibility(View.GONE);
//            binding.fabAdd.setVisibility(View.GONE);
//        }
//
//        binding.btnAdd.setOnClickListener(view -> {
//            loadFragment();
//        });
//
//        binding.fabAdd.setOnClickListener(view -> {
//            loadFragment();
//        });
//    }
}
