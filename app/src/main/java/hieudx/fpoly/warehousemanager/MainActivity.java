package hieudx.fpoly.warehousemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import hieudx.fpoly.warehousemanager.Bill.Fragment.Bill_Fragment;
import hieudx.fpoly.warehousemanager.Category.Fragment.Category_Fragment;
import hieudx.fpoly.warehousemanager.Product.Fragment.Product_Fragment;
import hieudx.fpoly.warehousemanager.databinding.ActivityMainBinding;
import hieudx.fpoly.warehousemanager.Delivery.Fragment.Delivery_Fragment;
import hieudx.fpoly.warehousemanager.Staff.Fragment.Staff_Fragment;
import hieudx.fpoly.warehousemanager.Supplier.Fragment.Supplier_Fragment;
import hieudx.fpoly.warehousemanager.Member.Fragment.Member_Fragment;
import hieudx.fpoly.warehousemanager.Statistic.Fragment.Statistic_Fragment;
import hieudx.fpoly.warehousemanager.Member.Account_Activity;

public class MainActivity extends AppCompatActivity {
    public static ActivityMainBinding binding;
    private ActionBar actionBar;
    private SharedPreferences sharedPreferences;
    int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        role = sharedPreferences.getInt("role", 2);

//        SharedPreferences shared = getSharedPreferences("ACCOUNT",MODE_PRIVATE);
//        SharedPreferences.Editor editor = shared.edit();
//        editor.putInt("id", 1);
//        editor.putString("username", "admin");
//        editor.putString("password", "admin");
//        editor.putString("name", "admin");
//        editor.commit();

        customActionBar();
        onClickListenerNavBottom();
        onClickDashBoard();
    }

    private void onClickListenerNavBottom() {
        binding.navBottom.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_bot_product) {
                General.loadFragment(getSupportFragmentManager(), new Product_Fragment(), null);
                translayout();
            } else if (item.getItemId() == R.id.nav_bot_category) {
                General.loadFragment(getSupportFragmentManager(), new Category_Fragment(), null);
                translayout();
            } else if (item.getItemId() == R.id.nav_bot_bill) {
                General.loadFragment(getSupportFragmentManager(), new Bill_Fragment(), null);
                translayout();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.frag_container_main, new Bill_Fragment())
//                        .commit();
//                translayout();
//                loadFragment(billFragment);
            } else if (item.getItemId() == R.id.nav_bot_member) {
                if (role == 1) {
                    Toast.makeText(this, "Bạn ko được phép truy cập", Toast.LENGTH_SHORT).show();
                } else if (role == 0) {
                    General.loadFragment(getSupportFragmentManager(), new Member_Fragment(), null);
                    translayout();
                }
            } else if (item.getItemId() == R.id.nav_bot_statistic) {
                General.loadFragment(getSupportFragmentManager(), new Statistic_Fragment(), null);
                translayout();
            }
            binding.tbMain.setTitle(item.getTitle());
            return true;
        });
    }

    private void onClickDashBoard() {
        binding.cardProduct.setOnClickListener(view -> {
            General.loadFragment(getSupportFragmentManager(), new Product_Fragment(), null);
            translayout();
        });
        binding.cardCaegory.setOnClickListener(view -> {
            General.loadFragment(getSupportFragmentManager(), new Category_Fragment(), null);
            translayout();
        });
        binding.cardBill.setOnClickListener(view -> {
            General.loadFragment(getSupportFragmentManager(), new Bill_Fragment(), null);
            translayout();
        });
        binding.cardMember.setOnClickListener(view -> {
            if (role == 1) {
                Toast.makeText(this, "Bạn ko được phép truy cập", Toast.LENGTH_SHORT).show();
            } else if (role == 0) {
                General.loadFragment(getSupportFragmentManager(), new Member_Fragment(), null);
                translayout();
            }
        });
        binding.cardStatitics.setOnClickListener(view -> {
            General.loadFragment(getSupportFragmentManager(), new Statistic_Fragment(), null);
            translayout();
        });
        binding.cardDelivery.setOnClickListener(view -> {
            General.loadFragment(getSupportFragmentManager(), new Delivery_Fragment(), null);
            translayout();
        });
        binding.cardSupplier.setOnClickListener(view -> {
            General.loadFragment(getSupportFragmentManager(), new Supplier_Fragment(), null);
            translayout();
        });
        binding.cardStaff.setOnClickListener(view -> {
            General.loadFragment(getSupportFragmentManager(), new Staff_Fragment(), null);
            translayout();
        });
    }

    private void customActionBar() {
        //      vì ta đã bỏ actionbar trong themes nên giờ phải set lại actionbar vào toolbar để có thẻ kéo nav từ bên trái
        setSupportActionBar(binding.tbMain);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        binding.tbMain.setNavigationOnClickListener(view -> {
            binding.fragContainerMain.setVisibility(View.GONE);

            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frag_container_main);
            Log.d("currentFragment", "customActionBar: " + currentFragment);

            if (currentFragment != null) {
                Log.d("currentFragment", "customActionBar: " + currentFragment);
                getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
//                    binding.navBottom.getMenu().getItem(0).setChecked(true);
            }
            binding.bgrDashBoard.setVisibility(View.VISIBLE);
            binding.layoutDashboard.setVisibility(View.VISIBLE);
        });

        binding.cvAccount.setOnClickListener(view -> {
            startActivity(new Intent(this, Account_Activity.class));
        });

        String img = sharedPreferences.getString("avatar", "");
        if (img.isEmpty()) binding.imgAccount.setImageResource(R.drawable.img_avt);
        else Picasso.get().load(img).into(binding.imgAccount);
    }

    private void translayout() {
        binding.bgrDashBoard.setVisibility(View.GONE);
        binding.layoutDashboard.setVisibility(View.GONE);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.bgrDashBoardParent.getLayoutParams();
        layoutParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        binding.bgrDashBoardParent.setLayoutParams(layoutParams);
    }
}