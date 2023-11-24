package hieudx.fpoly.warehousemanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import hieudx.fpoly.warehousemanager.databinding.ActivityMainBinding;
import hieudx.fpoly.warehousemanager.fragments.bill.Bill_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Category_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Delivery_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Product_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Staff_Fragment;
import hieudx.fpoly.warehousemanager.fragments.statistic.Statistic_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Supplier_Fragment;
import hieudx.fpoly.warehousemanager.fragments.member.Member_Fragment;
import hieudx.fpoly.warehousemanager.view.Account_Activity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productFragment = new Product_Fragment();
        categoryFragment = new Category_Fragment();
        billFragment = new Bill_Fragment();
        memberFragment = new Member_Fragment();
        staffFragment = new Staff_Fragment();
        deliveryFragment = new Delivery_Fragment();
        supplierFragment = new Supplier_Fragment();
        statisticFragment = new Statistic_Fragment();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        MenuItem searchItem = menu.findItem(R.id.act_searchview);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this, "Khi người dùng nhấn enter", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                xử lý Khi người dùng thay đổi nội dung ô tìm kiếm
//                Có thể thực hiện tìm kiếm theo thời gian thực ở đây
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.act_person) {
            Intent intent = new Intent(MainActivity.this, Account_Activity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadFragment(memberFragment);
                    }
                }, 1500);
            } else if (item.getItemId() == R.id.nav_bot_statistic) {
                loadFragment(statisticFragment);
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
            loadFragment(new Member_Fragment());
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    loadFragment(memberFragment);
//                }
//            }, 1500);
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
            loadFragment(staffFragment);
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

    public static void translayout() {
        binding.bgrDashBoard.setVisibility(View.GONE);
        binding.layoutDashboard.setVisibility(View.GONE);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.bgrDashBoardParent.getLayoutParams();
        layoutParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        binding.bgrDashBoardParent.setLayoutParams(layoutParams);
    }
}