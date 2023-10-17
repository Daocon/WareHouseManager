package hieudx.fpoly.warehousemanager.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hieudx.fpoly.warehousemanager.fragments.Login_Tab_Fragment;
import hieudx.fpoly.warehousemanager.fragments.SignUp_Tab_Fragment;

public class Login_SignUp_Adapter extends FragmentPagerAdapter {
    private Context context;
    private int totalTab;

    public Login_SignUp_Adapter(@NonNull FragmentManager fm, Context context, int totalTab) {
        super(fm);
        this.context = context;
        this.totalTab = totalTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new Login_Tab_Fragment();
        } else {
            return new SignUp_Tab_Fragment();
        }
//        switch (position) {
//            case 0:
//                Login_Tab_Fragment loginTabFragment = new Login_Tab_Fragment();
//                return loginTabFragment;
//            case 1:
//                SignUp_Tab_Fragment signUpTabFragment = new SignUp_Tab_Fragment();
//                return signUpTabFragment;
//            default:
//                return null;
//        }
    }

    @Override
    public int getCount() {
        return totalTab;
    }
//public class Login_SignUp_Adapter extends FragmentStateAdapter {
//
//    public Login_SignUp_Adapter(@NonNull FragmentActivity activity) {
//        super(activity.getSupportFragmentManager(), activity.getLifecycle());
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        switch (position) {
//            case 0:
//                return new Login_Tab_Fragment();
//            case 1:
//                return new SignUp_Tab_Fragment();
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 2;
//    }
}


//

