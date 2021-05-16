package com.example.ecommerceapp.View.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.ecommerceapp.View.UserPageFragments.UserInformationFragment;
import com.example.ecommerceapp.View.UserPageFragments.UserOrderFragment;
import com.example.ecommerceapp.View.UserPageFragments.UserSecurityFragment;

import org.jetbrains.annotations.NotNull;

public class UserPageViewPagerAdapter extends FragmentStatePagerAdapter {

    public UserPageViewPagerAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new UserInformationFragment();
            case 1:
                return new UserOrderFragment();
            case 2:
                return new UserSecurityFragment();
            default:
                return new UserInformationFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


}
