package hieudx.fpoly.warehousemanager.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import hieudx.fpoly.warehousemanager.fragments.login_signup.Login_Tab_Fragment;
import hieudx.fpoly.warehousemanager.fragments.login_signup.SignUp_Tab_Fragment;

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
    }

    @Override
    public int getCount() {
        return totalTab;
    }
}



