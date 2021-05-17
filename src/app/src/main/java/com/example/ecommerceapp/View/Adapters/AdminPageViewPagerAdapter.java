package com.example.ecommerceapp.View.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ecommerceapp.View.AdminPageFragments.AdminAccountFragment;
import com.example.ecommerceapp.View.AdminPageFragments.AdminOrderFragment;
import com.example.ecommerceapp.View.AdminPageFragments.AdminProductFragment;
import com.example.ecommerceapp.View.UserPageFragments.UserInformationFragment;
import com.example.ecommerceapp.View.UserPageFragments.UserOrderFragment;
import com.example.ecommerceapp.View.UserPageFragments.UserSecurityFragment;

import org.jetbrains.annotations.NotNull;

public class AdminPageViewPagerAdapter extends FragmentStatePagerAdapter {
    public AdminPageViewPagerAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new AdminAccountFragment();
            case 1:
                return new AdminProductFragment();
            case 2:
                return new AdminOrderFragment();
            default:
                return new AdminAccountFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
