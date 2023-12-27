package hieudx.fpoly.warehousemanager.Member;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;
import com.squareup.picasso.Picasso;

import hieudx.fpoly.warehousemanager.Login_SignUp_Forget_Reset.Activity.Forgot_Reset_Pass_Activity;
import hieudx.fpoly.warehousemanager.Login_SignUp_Forget_Reset.Activity.Login_SignUp_Activity;
import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.ActivityAccountBinding;
import hieudx.fpoly.warehousemanager.Member.account.Edit_UserLogin;

public class Account_Activity extends AppCompatActivity {
    private boolean nightMode;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ActivityAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        sharedPreferences = getSharedPreferences("ACCOUNT", MODE_PRIVATE);

        String img = sharedPreferences.getString("avatar", "");
        if (img.isEmpty()) binding.imgAvt.setImageResource(R.drawable.img_avt);
        else Picasso.get().load(img).into(binding.imgAvt);

        binding.tvName.setText(sharedPreferences.getString("name", ""));
        binding.tvPhone.setText(sharedPreferences.getString("phone", ""));

        sharedPreferences = getSharedPreferences("MODE", MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false);

        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            binding.swMode.setChecked(true);
        }
        binding.swMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nightMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", true);
                }
                editor.apply();
            }
        });
        binding.swNotifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(Account_Activity.this, "Bật thông báo: Bật", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Account_Activity.this, "Bật thông báo: Tắt", Toast.LENGTH_SHORT).show();

                }
            }
        });
        binding.swAutoApdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(Account_Activity.this, "Tự động cập nhật: Bật", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Account_Activity.this, "Tự động cập nhật: Tắt", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.rlEditInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Account_Activity.this, "Chỉnh sửa thông tin", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Account_Activity.this, Edit_UserLogin.class));
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        binding.rlChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupDialog.getInstance(Account_Activity.this)
                        .setStyle(Styles.IOS)
                        .setHeading("Đổi mật khẩu")
                        .setDescription("Bạn có chắc chắn muốn đổi mật khẩu không?")
                        .setCancelable(false)
                        .showDialog(new OnDialogButtonClickListener() {
                            @Override
                            public void onPositiveClicked(Dialog dialog) {
                                startActivity(new Intent(Account_Activity.this, Forgot_Reset_Pass_Activity.class));
                                getOnBackPressedDispatcher().onBackPressed();
                            }

                            @Override
                            public void onNegativeClicked(Dialog dialog) {
                                super.onNegativeClicked(dialog);
                            }
                        });
            }
        });

        binding.rlAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Account_Activity.this, "Về chúng tôi", Toast.LENGTH_SHORT).show();
            }
        });
        binding.rlPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Account_Activity.this, "Chính sách bảo mật", Toast.LENGTH_SHORT).show();
            }
        });
        binding.rlError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Account_Activity.this, "Báo lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        binding.rlHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Account_Activity.this, "Trợ giúp và phản hồi", Toast.LENGTH_SHORT).show();
            }
        });
        binding.rlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Account_Activity.this, "Chia sẻ", Toast.LENGTH_SHORT).show();
            }
        });
        binding.rlLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupDialog.getInstance(Account_Activity.this)
                        .setStyle(Styles.STANDARD)
                        .setHeading("Đăng xuất")
                        .setDescription("Bạn có chắc chắn muốn đăng xuất không?" +
                                " Hành động này không thể được hoàn tác")
                        .setPopupDialogIcon(R.drawable.ic_logout)
                        .setPopupDialogIconTint(R.color.red)
                        .setCancelable(false)
                        .showDialog(new OnDialogButtonClickListener() {
                            @Override
                            public void onPositiveClicked(Dialog dialog) {
                                startActivity(new Intent(Account_Activity.this, Login_SignUp_Activity.class));
                                finish();
                            }

                            @Override
                            public void onNegativeClicked(Dialog dialog) {
                                super.onNegativeClicked(dialog);
                            }
                        });
            }
        });
    }
}