package com.winit.iKonnect.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.winit.common.constant.ConstantExtraKey;
import com.winit.iKonnect.ui.fragments.DashboardFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul.Yadav on 5/12/2017.
 */

public class DashboardViewPagerAdapter extends FragmentStatePagerAdapter{
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private FragmentManager fm;
    public DashboardViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DashboardFragment feedsFragment = new DashboardFragment();
                Bundle feedsBundle = new Bundle();
                feedsBundle.putInt(ConstantExtraKey.DASHBOARD_POSITION,0);
                feedsFragment.setArguments(feedsBundle);
                return feedsFragment;
            case 1:
                DashboardFragment serviceFragment = new DashboardFragment();
                Bundle servicesBundle = new Bundle();
                servicesBundle.putInt(ConstantExtraKey.DASHBOARD_POSITION,1);
                serviceFragment.setArguments(servicesBundle);
                return serviceFragment;
            default:
                return null;
        }
//        return mFragmentList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
         return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void refresh(){
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
