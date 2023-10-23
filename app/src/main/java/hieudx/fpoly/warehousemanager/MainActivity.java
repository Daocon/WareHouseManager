package hieudx.fpoly.warehousemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import hieudx.fpoly.warehousemanager.databinding.ActivityMainBinding;
import hieudx.fpoly.warehousemanager.fragments.Bill_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Category_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Member_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Product_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Setting_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Statistic_Fragment;
import hieudx.fpoly.warehousemanager.view.Login_SignUp_Activity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        customActionBar();

        onClickListenerNavDrawer();

        onClickListenerNavBottom();

    }

    private void onClickListenerNavBottom() {
        binding.navBottom.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_bot_product) {
                loadFragment(new Product_Fragment());
            } else if (item.getItemId() == R.id.nav_bot_category) {
                loadFragment(new Category_Fragment());
            } else if (item.getItemId() == R.id.nav_bot_bill) {
                loadFragment(new Bill_Fragment());
            } else if (item.getItemId() == R.id.nav_bot_member) {
                loadFragment(new Member_Fragment());
            } else if (item.getItemId() == R.id.nav_bot_statistic) {
                loadFragment(new Statistic_Fragment());
            }
            return true;
        });
    }


    private void onClickListenerNavDrawer() {
        binding.nav.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) item -> {
            if (item.getItemId() == R.id.nav_logout) {
                startActivity(new Intent(MainActivity.this, Login_SignUp_Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            } else if (item.getItemId() == R.id.nav_drawer_product) {
                loadFragment(new Product_Fragment());
            } else if (item.getItemId() == R.id.nav_drawer_category) {
                loadFragment(new Category_Fragment());
            } else if (item.getItemId() == R.id.nav_drawer_bill) {
                loadFragment(new Bill_Fragment());
            } else if (item.getItemId() == R.id.nav_drawer_member) {
                loadFragment(new Member_Fragment());
            } else if (item.getItemId() == R.id.nav_drawer_statistic) {
                loadFragment(new Statistic_Fragment());
            } else if (item.getItemId() == R.id.nav_setting) {
                loadFragment(new Setting_Fragment());
            }
            binding.drawerLayout.closeDrawer(binding.nav);
            binding.tbMain.setTitle(item.getTitle());
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
//        menu.add("").setIcon(R.drawable.baseline_person_24).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

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
            Toast.makeText(this, "Chức năng chưa được phát triển", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frag_container_main, fragment).commit();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.frag_container_main, fragment).addToBackStack(null).commit();
    }

    private void customActionBar() {
        //      vì ta đã bỏ actionbar trong themes nên giờ phải set lại actionbar vào toolbar để có thẻ kéo nav từ bên trái
        setSupportActionBar(binding.tbMain);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //        dùng để click vào icon menu bật ra nav
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.tbMain, R.string.navigation_open, R.string.navigation_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}