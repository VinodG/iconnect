package com.winit.iKonnect.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.winit.common.constant.ConstantExtraKey;
import com.winit.iKonnect.ui.fragments.TrackServiceFragments;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul.Yadav on 5/25/2017.
 */

public class TrackServiceViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public TrackServiceViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TrackServiceFragments trackServiceFragments = new TrackServiceFragments();
                Bundle servicesBundle = new Bundle();
                servicesBundle.putInt(ConstantExtraKey.TRACKING_SERVICE_POSITION,0);
                trackServiceFragments.setArguments(servicesBundle);
                return trackServiceFragments;
            case 1:
                TrackServiceFragments trackServiceFragments1 = new TrackServiceFragments();
                Bundle servicesBundle1 = new Bundle();
                servicesBundle1.putInt(ConstantExtraKey.TRACKING_SERVICE_POSITION,1);
                trackServiceFragments1.setArguments(servicesBundle1);
                return trackServiceFragments1;
        }
        return null;
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
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
