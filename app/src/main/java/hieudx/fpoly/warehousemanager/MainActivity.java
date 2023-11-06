package hieudx.fpoly.warehousemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.adapters.Dash_Board_Adapter;
import hieudx.fpoly.warehousemanager.databinding.ActivityMainBinding;
import hieudx.fpoly.warehousemanager.fragments.Account_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Bill.Bill_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Category_Fragment;
import hieudx.fpoly.warehousemanager.fragments.member.Member_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Product_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Statistic_Fragment;
import hieudx.fpoly.warehousemanager.models.item_dash_board;

public class MainActivity extends AppCompatActivity {
    public static ActivityMainBinding binding;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        customActionBar();
        onClickListenerNavBottom();
        onCreateRcvList();
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
            loadFragment(new Account_Fragment());
        }
        return super.onOptionsItemSelected(item);
    }
    private void onCreateRcvList() {
        ArrayList<item_dash_board> list = new ArrayList<>();
        list.add(new item_dash_board(R.drawable.ic_product, "Sản phẩm"));
        list.add(new item_dash_board(R.drawable.ic_category, "Loại sản phẩm"));
        list.add(new item_dash_board(R.drawable.ic_bill, "Hóa đơn"));
        list.add(new item_dash_board(R.drawable.ic_member, "Thành viên"));
        list.add(new item_dash_board(R.drawable.ic_statistic, "Thống kê"));
        list.add(new item_dash_board(R.drawable.ic_delivery, "Vận chuyển"));
        list.add(new item_dash_board(R.drawable.ic_supplier, "Nhà cung cấp"));
        list.add(new item_dash_board(R.drawable.ic_staff, "Nhân viên"));
        Dash_Board_Adapter adapter = new Dash_Board_Adapter(list, this);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        binding.rcv.setLayoutManager(manager);
        binding.rcv.setAdapter(adapter);
    }

    private void onClickListenerNavBottom() {
        binding.navBottom.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_bot_product) {
                loadFragment(new Product_Fragment());
                translayout();
            } else if (item.getItemId() == R.id.nav_bot_category) {
                loadFragment(new Category_Fragment());
                translayout();
            } else if (item.getItemId() == R.id.nav_bot_bill) {
                loadFragment(new Bill_Fragment());
                translayout();
            } else if (item.getItemId() == R.id.nav_bot_member) {
                loadFragment(new Member_Fragment());
                translayout();
            } else if (item.getItemId() == R.id.nav_bot_statistic) {
                loadFragment(new Statistic_Fragment());
                translayout();
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frag_container_main, fragment).addToBackStack(null).commit();
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
            binding.rcv.setVisibility(View.VISIBLE);
        });

        //        dùng để click vào icon menu bật ra nav
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, binding.drawerLayout, binding.tbMain, R.string.navigation_open, R.string.navigation_close);
//        binding.drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
    }

    public static void translayout() {
        binding.bgrDashBoard.setVisibility(View.GONE);
        binding.rcv.setVisibility(View.GONE);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.bgrDashBoardParent.getLayoutParams();
        layoutParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        binding.bgrDashBoardParent.setLayoutParams(layoutParams);
    }


    //    private void onClickListenerNavDrawer() {
//        binding.nav.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) item -> {
//            if (item.getItemId() == R.id.nav_logout) {
//                startActivity(new Intent(MainActivity.this, Login_SignUp_Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
//            } else if (item.getItemId() == R.id.nav_drawer_product) {
//                loadFragment(new Product_Fragment());
//            } else if (item.getItemId() == R.id.nav_drawer_category) {
//                loadFragment(new Category_Fragment());
//            } else if (item.getItemId() == R.id.nav_drawer_bill) {
//                loadFragment(new Bill_Fragment());
//            } else if (item.getItemId() == R.id.nav_drawer_member) {
//                loadFragment(new Member_Fragment());
//            } else if (item.getItemId() == R.id.nav_drawer_statistic) {
//                loadFragment(new Statistic_Fragment());
//            } else if (item.getItemId() == R.id.nav_setting) {
//                loadFragment(new Setting_Fragment());
//            }
//            binding.drawerLayout.closeDrawer(binding.nav);
//            binding.tbMain.setTitle(item.getTitle());
//            return true;
//        });
//    }
}