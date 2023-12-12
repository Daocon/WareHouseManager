package hieudx.fpoly.warehousemanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import hieudx.fpoly.warehousemanager.databinding.ActivityMainBinding;
import hieudx.fpoly.warehousemanager.Category.Fragment.Category_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Delivery_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Product_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Staff_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Supplier_Fragment;
import hieudx.fpoly.warehousemanager.Bill.Fragment.Bill_Fragment;
import hieudx.fpoly.warehousemanager.fragments.member.Member_Fragment;
import hieudx.fpoly.warehousemanager.fragments.statistic.Chart_Fragment;
import hieudx.fpoly.warehousemanager.fragments.statistic.Statistic_Fragment;

public class MainActivity extends AppCompatActivity {
    public static ActivityMainBinding binding;
    private ActionBar actionBar;
    private Product_Fragment productFragment;
    private Category_Fragment categoryFragment;
    private Bill_Fragment billFragment;
    private Member_Fragment memberFragment;
    private Statistic_Fragment statisticFragment;
    private Delivery_Fragment deliveryFragment;
    private Supplier_Fragment supplierFragment;
    private Staff_Fragment staffFragment;
    private Chart_Fragment chartFragment;
    private SharedPreferences sharedPreferences;

    int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        role = sharedPreferences.getInt("role",2);

        productFragment = new Product_Fragment();
        categoryFragment = new Category_Fragment();
        billFragment = new Bill_Fragment();
        memberFragment = new Member_Fragment();
        staffFragment = new Staff_Fragment();
        deliveryFragment = new Delivery_Fragment();
        supplierFragment = new Supplier_Fragment();
        statisticFragment = new Statistic_Fragment();
        chartFragment = new Chart_Fragment();
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
                loadFragment(productFragment);
            } else if (item.getItemId() == R.id.nav_bot_category) {
                loadFragment(categoryFragment);
            } else if (item.getItemId() == R.id.nav_bot_bill) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frag_container_main, new Bill_Fragment())
                        .commit();
                translayout();
//                loadFragment(billFragment);
            } else if (item.getItemId() == R.id.nav_bot_member) {
                if (role == 1){
                    Toast.makeText(this, "Bạn ko được phép truy cập", Toast.LENGTH_SHORT).show();
                } else if (role == 0){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frag_container_main, new Member_Fragment())
                            .commit();
                    translayout();
                }
            } else if (item.getItemId() == R.id.nav_bot_statistic) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frag_container_main, new Statistic_Fragment())
                        .commit();
                translayout();
            }
            binding.tbMain.setTitle(item.getTitle());
            return true;
        });
    }

    private void onClickDashBoard() {
        binding.cardProduct.setOnClickListener(view -> {
            loadFragment(productFragment);
        });
        binding.cardCaegory.setOnClickListener(view -> {
            loadFragment(categoryFragment);
        });
        binding.cardBill.setOnClickListener(view -> {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frag_container_main, new Bill_Fragment())
//                    .commit();
//            translayout();
            loadFragment(new Bill_Fragment());
        });
        binding.cardMember.setOnClickListener(view -> {
            if (role == 1){
                Toast.makeText(this, "Bạn ko được phép truy cập", Toast.LENGTH_SHORT).show();
            } else if (role == 0){
                loadFragment(memberFragment);
            }
        });
        binding.cardStatitics.setOnClickListener(view -> {
            loadFragment(statisticFragment);
        });
        binding.cardDelivery.setOnClickListener(view -> {
            loadFragment(deliveryFragment);
        });
        binding.cardSupplier.setOnClickListener(view -> {
            loadFragment(supplierFragment);
        });
        binding.cardStaff.setOnClickListener(view -> {
            loadFragment(chartFragment);
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frag_container_main, fragment).commit();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.frag_container_main, fragment).commit();
        translayout();
    }

    private void customActionBar() {
        //      vì ta đã bỏ actionbar trong themes nên giờ phải set lại actionbar vào toolbar để có thẻ kéo nav từ bên trái
        setSupportActionBar(binding.tbMain);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        binding.tbMain.setNavigationOnClickListener(view -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frag_container_main);
            if (currentFragment != null) {
                getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
//                    binding.navBottom.getMenu().getItem(0).setChecked(true);
            }
            binding.bgrDashBoard.setVisibility(View.VISIBLE);
            binding.layoutDashboard.setVisibility(View.VISIBLE);
        });
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