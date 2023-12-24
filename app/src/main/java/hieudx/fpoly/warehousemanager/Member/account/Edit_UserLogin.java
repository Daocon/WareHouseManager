package hieudx.fpoly.warehousemanager.Member.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.Member.Dao.User_Dao;
import hieudx.fpoly.warehousemanager.databinding.ActivityEditUserLoginBinding;
import hieudx.fpoly.warehousemanager.Member.Model.User;

public class Edit_UserLogin extends AppCompatActivity {
    private ActivityEditUserLoginBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private User_Dao userDao;
    String name, username, phone, email, avatar;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_login);

        binding = ActivityEditUserLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        sharedPreferences = getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        id = sharedPreferences.getInt("id", 0);
        Picasso.get().load(sharedPreferences.getString("avatar","")).into(binding.avatarIv);
        binding.txtAvatar.setText(sharedPreferences.getString("avatar",""));
        binding.txtName.setText(sharedPreferences.getString("name", ""));
        binding.txtUsername.setText(sharedPreferences.getString("username", ""));
        binding.txtPhone.setText(sharedPreferences.getString("phone", ""));
        binding.txtEmail.setText(sharedPreferences.getString("email", ""));

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    private void updateUser() {
        validationLinkAvartar();
        validationEmail();
        validationPhone();
        validationUsername();
        validationName();

        if (!hasErrors()) {
            String mavatar = binding.txtAvatar.getText().toString();
            String mname = binding.txtName.getText().toString();
            String musername = binding.txtUsername.getText().toString();
            String mphone = binding.txtPhone.getText().toString();
            String memail = binding.txtEmail.getText().toString();

            if (    mavatar.equals(sharedPreferences.getString("avatar", "")) &&
                    mname.equals(sharedPreferences.getString("name", "")) &&
                    musername.equals(sharedPreferences.getString("username", "")) &&
                    mphone.equals(sharedPreferences.getString("phone", "")) &&
                    memail.equals(sharedPreferences.getString("email", ""))) {
                Toast.makeText(this, "Dữ liệu chưa thay đổi", Toast.LENGTH_SHORT).show();
                return;
            }

            userDao = new User_Dao(Edit_UserLogin.this);
            User user = new User(musername, mname, memail, mphone,mavatar);
            user.setId(id);
            if (userDao.updateUser(user)) {
                Toast.makeText(this, "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
                // Cập nhật dữ liệu vào SharedPreferences
                editor.putString("avatar", mavatar);
                editor.putString("name", mname);
                editor.putString("username", musername);
                editor.putString("phone", mphone);
                editor.putString("email", memail);
                editor.apply();
                getOnBackPressedDispatcher().onBackPressed();
            } else {
                Toast.makeText(this, "Cập nhật dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean hasErrors() {
        return binding.tilEmail.getError() != null || binding.tilPhone.getError() != null ||
                binding.tilUsername.getError() != null || binding.tilName.getError() != null ||
                binding.tilAvatar.getError() != null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void validationLinkAvartar(){
        avatar = binding.txtAvatar.getText().toString();
        if (avatar.isEmpty()){
            binding.tilAvatar.setError("Vui lòng nhập link avatar");
        } else if (avatar.contains(" ")){
            binding.tilAvatar.setError("Link avatar ko được chứa khoảng trắng");
        } else {
            binding.tilAvatar.setError(null);
        }
    }

    private void validationEmail() {
        email = binding.txtEmail.getText().toString();
        if (email.isEmpty()) {
            binding.tilEmail.setError("Vui lòng nhập email");
        } else if (email.contains(" ")) {
            binding.tilEmail.setError("Email không được chứa khoảng trắng");
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            binding.tilEmail.setError("Vui lòng nhập đúng định dạng email");
        } else {
            binding.tilEmail.setError(null); // Xóa thông báo lỗi nếu dữ liệu hợp lệ
        }
    }

    private void validationPhone() {
        phone = binding.txtPhone.getText().toString();
        int phoneLength = phone.length();
        if (phone.isEmpty()) {
            binding.tilPhone.setError("Vui lòng nhập số điện thoại");
        } else if (phoneLength < 10) {
            binding.tilPhone.setError("Số điện thoại phải chứa 10 số");
        } else if (phone.contains(" ")) {
            binding.tilPhone.setError("Số điện thoại không được chứa khoảng trắng");
        } else if (!phone.startsWith("0")) {
            binding.tilPhone.setError("Số điện thoại phải bắt đầu bằng số 0");
        } else {
            binding.tilPhone.setError(null); // Xóa thông báo lỗi nếu dữ liệu hợp lệ
        }
    }

    private void validationUsername() {
        username = binding.txtUsername.getText().toString();
        int usernameLength = username.length();
        if (username.isEmpty()) {
            binding.tilUsername.setError("Vui lòng nhập tên đăng nhập");
        } else if (username.contains(" ")) {
            binding.tilUsername.setError("Tên đăng nhập không được chứa khoảng trắng");
        } else if (usernameLength < 5 || usernameLength > 15) {
            binding.tilUsername.setError("Tên đăng nhập phải có từ 5 đến 15 ký tự");
        } else {
            binding.tilUsername.setError(null); // Xóa thông báo lỗi nếu dữ liệu hợp lệ
        }
    }

    private void validationName() {
        name = binding.txtName.getText().toString();
        if (name.isEmpty()) {
            binding.tilName.setError("Vui lòng nhập họ và tên");
        } else if (name.length() < 2) {
            binding.tilName.setError("Tên phải có ít nhất 2 ký tự");
        } else if (name.matches(".*\\d.*")) {
            binding.tilName.setError("Tên không được chứa số");
        } else {
            binding.tilName.setError(null); // Xóa thông báo lỗi nếu dữ liệu hợp lệ
        }
    }
}