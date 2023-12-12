package hieudx.fpoly.warehousemanager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class General {
    public static void isEmptyValid(TextInputEditText inputEditText, TextInputLayout inputLayout) {
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence.toString())) {
                    inputLayout.setError("Không được để trống");
                } else if (TextUtils.isEmpty(charSequence.toString().trim())) {
                    inputLayout.setError("Không được nhập khoảng trắng");
                } else {
                    inputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    public static void isContainNumber(String value, TextInputLayout inputLayout) {
        if (value.matches(".*\\d.*"))
            inputLayout.setError("Không được chứa số");
        else inputLayout.setError(null);
    }

    public static void isContainSpace(String value, TextInputLayout inputLayout) {
        if (value.trim().contains(" "))
            inputLayout.setError("Không được chứa khoảng trắng");
        else inputLayout.setError(null);
    }

    public static void validEmail(String value, TextInputLayout inputLayout) {
        if (value.trim().contains(" ") || !value.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
            inputLayout.setError("Hãy nhập đúng định dạng email");
        else inputLayout.setError(null);
    }

    public static void passValid(String pass, String repass, TextInputLayout inputLayout) {
        if (!pass.equals(repass)) {
            inputLayout.setError("Mật khẩu không trùng khớp");
        } else {
            inputLayout.setError(null);
        }
    }

    public static void validPhone(String value, TextInputLayout field) {
        if (value.length() < 10 || value.length() > 10) {
            field.setError("Số điện thoại phải chứa 10 số");
        } else if (!value.startsWith("0")) {
            field.setError("Số điện thoại phải bắt đầu bằng số 0");
        } else {
            field.setError(null);
        }
    }

    public static String formatSumVND(Double sum) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat nf = NumberFormat.getInstance(locale);
        return nf.format(sum);
    }

    public static void transLayout(ArrayList list, Button btnAdd, ImageView imgSort, RecyclerView rcv, FloatingActionButton fabAdd) {
        if (!list.isEmpty()) {
            btnAdd.setVisibility(View.GONE);
            imgSort.setVisibility(View.VISIBLE);
            rcv.setVisibility(View.VISIBLE);
            fabAdd.setVisibility(View.VISIBLE);
        } else {
            btnAdd.setVisibility(View.VISIBLE);
            imgSort.setVisibility(View.GONE);
            rcv.setVisibility(View.GONE);
            fabAdd.setVisibility(View.GONE);
        }
    }

    public static void loadFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.frag_container_main, fragment)
                .addToBackStack(null) // Cho phép quay lại fragment trước đó nếu cần
                .commit();
    }

}
