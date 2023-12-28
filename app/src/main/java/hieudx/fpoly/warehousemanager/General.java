package hieudx.fpoly.warehousemanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class General {
    public static String genarateIdBill(int listSize, String kob, Context context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMM", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        SharedPreferences shared = context.getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
        int user_id = shared.getInt("id", 0);
        String generatedId = kob + "_" + currentDate + "_" + user_id + (listSize + 1);
        return generatedId;
    }

    public static void onStateIconBack(FragmentActivity fragmentActivity, ActionBar actionBar, FragmentManager fragmentManager, boolean state) {
        ((AppCompatActivity) fragmentActivity).setSupportActionBar(MainActivity.binding.tbMain);
        if (state) actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        else actionBar.setHomeAsUpIndicator(R.drawable.back);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        MainActivity.binding.tbMain.setNavigationOnClickListener(view12 -> {
            if (state) {
                Fragment currentFragment =  fragmentManager.findFragmentById(R.id.frag_container_main);
                if (currentFragment != null) {
                    fragmentManager.beginTransaction().remove(currentFragment).commit();
                }
                MainActivity.binding.bgrDashBoard.setVisibility(View.VISIBLE);
                MainActivity.binding.layoutDashboard.setVisibility(View.VISIBLE);
            }
                else fragmentManager.popBackStack();

        });
    }

    public static void onLoadSpinner(Context context, ArrayList<HashMap<String, Object>> listHM, Spinner spinner) {
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"name"},
                new int[]{android.R.id.text1});
        spinner.setAdapter(simpleAdapter);
    }

    public static Dialog onSettingsBotSheet(Context context, ViewBinding viewBinding) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(viewBinding.getRoot());

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
        return dialog;
    }

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

    public static void isContainChar(String value, TextInputLayout inputLayout) {
        if (value.matches("[a-zA-Z]+")) inputLayout.setError("Không được nhâp chữ");
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
        } else if (!value.matches("^0\\d{9}$")) {
            field.setError("Số điện thoại không hợp lệ");
        } else if (!TextUtils.isDigitsOnly(value)){
            field.setError("Không được nhập chữ");
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

    public static void loadFragment(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        if (bundle != null) fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.frag_container_main, fragment)
                .addToBackStack(null) // Cho phép quay lại fragment trước đó nếu cần
                .commit();
    }

    public static void showSuccessPopup(Context context, String heading, String description, OnDialogButtonClickListener listener) {
        PopupDialog.getInstance(context)
                .setStyle(Styles.SUCCESS)
                .setHeading(heading)
                .setDescription(description)
                .setCancelable(false)
                .showDialog(listener);
    }
    public static void showFailurePopup(Context context, String heading, String description, OnDialogButtonClickListener listener) {
        PopupDialog.getInstance(context)
                .setStyle(Styles.FAILED)
                .setHeading(heading)
                .setDescription(description)
                .setCancelable(false)
                .showDialog(listener);
    }
}
