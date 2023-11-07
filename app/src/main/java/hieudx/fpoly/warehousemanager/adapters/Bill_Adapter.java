package hieudx.fpoly.warehousemanager.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import hieudx.fpoly.warehousemanager.fragments.Bill.Bill_In.Bill_In_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Bill.Bill_Out.Bill_Out_Fragment;

public class Bill_Adapter extends FragmentStateAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> headerList = new ArrayList<>();

    public Bill_Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        initData();
    }

    private void initData() {
        addData(new Bill_In_Fragment(), "Phiếu nhập");
        addData(new Bill_Out_Fragment(), "Phiếu xuất");
    }

    private void addData(Fragment fragment, String header) {
        fragmentList.add(fragment);
        headerList.add(header);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public String getHeader(int position) {
        return headerList.get(position);
    }
}
