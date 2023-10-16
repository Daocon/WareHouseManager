package hieudx.fpoly.warehousemanager.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import hieudx.fpoly.warehousemanager.fragments.First_OnBoarding_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Second_OnBoarding_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Third_OnBoarding_Fragment;

public class SlidePager_Adapter extends FragmentStatePagerAdapter {
    public SlidePager_Adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                First_OnBoarding_Fragment tab1 = new First_OnBoarding_Fragment();
                return tab1;

            case 1:
                Second_OnBoarding_Fragment tab2 = new Second_OnBoarding_Fragment();
                return tab2;

            case 2:
                Third_OnBoarding_Fragment tab3 = new Third_OnBoarding_Fragment();
                return tab3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
