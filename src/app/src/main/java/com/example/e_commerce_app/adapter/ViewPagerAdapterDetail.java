package com.example.e_commerce_app;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.e_commerce_app.common.Common;
import com.example.e_commerce_app.view.detail.detail.FragmentDetail;
import com.example.e_commerce_app.view.detail.overview.FragmentOverview;
import com.example.e_commerce_app.view.detail.rating.FragmentRating;


public class ViewPagerAdapterDetail extends FragmentPagerAdapter {
    public ViewPagerAdapterDetail(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentOverview();
            case 1:
                return new FragmentRating();
            case 2:
                return new FragmentDetail();
        }
        return new FragmentOverview();
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Common.TITLE_FRAGMENT_DETAIL[0];
            case 1:
                return Common.TITLE_FRAGMENT_DETAIL[1];
            case 2:
                return Common.TITLE_FRAGMENT_DETAIL[2];
        }
        return Common.TITLE_FRAGMENT_DETAIL[0];
    }


}

