package com.example.ecommerceapp.View;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.View.Adapters.UserPageViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

public class UserPageActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private UserPageViewPagerAdapter userPageViewPagerAdapter;

    private int[] tabTitles = {R.string.user_information_title, R.string.user_order_title, R.string.user_security_title};
    private int[] tabIcons = {R.drawable.user_information_icon, R.drawable.user_order_icon, R.drawable.user_security_icon};

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        setupTabLayout();
    }

    private void setupTabLayout() {
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        userPageViewPagerAdapter = new UserPageViewPagerAdapter(getSupportFragmentManager()
                , FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mViewPager.setAdapter(userPageViewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < userPageViewPagerAdapter.getCount(); i++) {
            mTabLayout.getTabAt(i).setText(tabTitles[i])
                    .setIcon(tabIcons[i]);
        }

        mTabLayout.getTabAt(0).getIcon().setColorFilter(
                UserPageActivity.this.getColor(R.color.tabSelectedIconColor), PorterDuff.Mode.SRC_IN);

        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(@NonNull @NotNull TabLayout.Tab tab) {
                int tabIconColor = UserPageActivity.this.getColor(R.color.tabSelectedIconColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                super.onTabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                int tabIconColor = UserPageActivity.this.getColor(R.color.tabUnselectedIconColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }
        });
    }
}
