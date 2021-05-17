package com.example.ecommerceapp.View.AdminPageFragments;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.View.Adapters.AdminPageViewPagerAdapter;
import com.example.ecommerceapp.View.Adapters.UserPageViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

public class AdminPageMainFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private AdminPageViewPagerAdapter adminPageViewPagerAdapter;

    private int[] tabTitles = {R.string.admin_account_title, R.string.account_product_title, R.string.account_order_title};
    private int[] tabIcons = {R.drawable.account_manage_icon, R.drawable.product_icon, R.drawable.user_order_icon};

    private String userName;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_page_main, null);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userName = "admin";
        setupActionBar();
        setupTabLayout(view);
    }

    private void setupActionBar() {
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(userName);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();
    }

    private void setupTabLayout(View view) {
        mTabLayout = view.findViewById(R.id.tabLayout);
        mViewPager = view.findViewById(R.id.viewPager);

        adminPageViewPagerAdapter = new AdminPageViewPagerAdapter(getChildFragmentManager()
                , FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mViewPager.setAdapter(adminPageViewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < adminPageViewPagerAdapter.getCount(); i++) {
            mTabLayout.getTabAt(i).setText(tabTitles[i])
                    .setIcon(tabIcons[i]);
        }

        mTabLayout.getTabAt(0).getIcon().setColorFilter(
                requireContext().getColor(R.color.tabSelectedIconColor), PorterDuff.Mode.SRC_IN);

        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(@NonNull @NotNull TabLayout.Tab tab) {
                int tabIconColor = requireContext().getColor(R.color.tabSelectedIconColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                super.onTabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                int tabIconColor = requireContext().getColor(R.color.tabUnselectedIconColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }
        });
    }
}