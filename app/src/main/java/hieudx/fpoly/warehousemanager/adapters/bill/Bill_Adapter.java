package hieudx.fpoly.warehousemanager.adapters.bill;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hieudx.fpoly.warehousemanager.fragments.Bill.Bill_In.Bill_In_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Bill.Bill_Out.Bill_Out_Fragment;

public class Bill_Adapter extends FragmentStateAdapter {

    public Bill_Adapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new Bill_In_Fragment();
        }
        return new Bill_Out_Fragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
