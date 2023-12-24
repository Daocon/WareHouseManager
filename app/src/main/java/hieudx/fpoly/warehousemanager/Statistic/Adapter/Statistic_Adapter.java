package hieudx.fpoly.warehousemanager.Statistic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hieudx.fpoly.warehousemanager.Statistic.Fragment.Chart_Fragment;
import hieudx.fpoly.warehousemanager.Statistic.Fragment.Total_Fragment;

public class Statistic_Adapter extends FragmentStateAdapter {

    public Statistic_Adapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new Total_Fragment();
        }
        return new Chart_Fragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
