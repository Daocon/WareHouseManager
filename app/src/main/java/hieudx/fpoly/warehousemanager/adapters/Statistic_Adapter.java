package hieudx.fpoly.warehousemanager.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hieudx.fpoly.warehousemanager.fragments.statistic.Statistic_Fragment;
import hieudx.fpoly.warehousemanager.fragments.statistic.Total_Fragment;

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
        return new Statistic_Fragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
