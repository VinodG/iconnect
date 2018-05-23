package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.TrackServiceViewPagerAdapter;
import com.winit.iKonnect.dataobject.ServiceRequestDO;
import com.winit.iKonnect.module.trackService.ITrackServicePresenter;
import com.winit.iKonnect.module.trackService.ITrackServiceView;
import com.winit.iKonnect.module.trackService.TrackServicePresenter;
import com.winit.iKonnect.ui.fragments.TrackServiceFragments;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rahul.Yadav on 5/24/2017.
 */

public class TrackServiceActivity extends BaseActivity implements ITrackServiceView{
   @Nullable
    @Bind(R.id.tabs_TrackService)
    TabLayout tabLayout;

    @Nullable
    @Bind(R.id.viewpager_trackService)
    ViewPager viewPager;

    public ITrackServicePresenter iTrackServicePresenter;
    private TrackServiceViewPagerAdapter trackServiceViewPagerAdapter;
    boolean isFromNotification;
    boolean isActive=false;
    private TextView tv_requsetedon;



    @Override
    protected void initialize() {
        inflater.inflate(R.layout.track_service,flBody,true);
        ButterKnife.bind(this);
        tv_requsetedon = (TextView) findViewById(R.id.tv_requsetedon);

        setToolbarTitle(getString(R.string.track_service));
        iTrackServicePresenter = new TrackServicePresenter(this);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        isFromNotification = getIntent().getBooleanExtra(ConstantExtraKey.IS_FROM_NOTIFICATION,false);
        isActive = getIntent().getBooleanExtra("isActive",false);
        if(isActive)
        {
            viewPager.setCurrentItem(1);
        }else
            viewPager.setCurrentItem(0);

    }

    @Override
    public void showAlert(String type) {
        hideLoader();
        String message = "";
        switch (type) {
            case AppConstants.Logout:
                showCustomDialog(TrackServiceActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
                break;
            default:
                message = type;
                break;
        }
        showCustomDialog(TrackServiceActivity.this, getString(R.string.alert), message, getString(R.string.OK), "",message);
    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkNetworkConnection()) {
            showLoader(getString(R.string.loading));
            iTrackServicePresenter.fetchTrackServices();
        }
        else
            showCustomDialog(TrackServiceActivity.this, getString(R.string.alert), getString(R.string.No_Network_connection), getString(R.string.OK), "", "gotoDashboard", false);
    }

    @Override
    public void onTrackServices(List<ServiceRequestDO> trackServices) {
        hideLoader();
        trackServiceViewPagerAdapter.refresh();
        if(trackServices!=null && trackServices.size()>0) {
            tv_requsetedon.setVisibility(View.VISIBLE);
//            tv_nodatafound.setVisibility(View.GONE);
        }
        else{

//            tv_nodatafound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setTypeFace() {

      //  btnSubmit.setTypeface(AppConstants.BOLD);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        trackServiceViewPagerAdapter = new TrackServiceViewPagerAdapter(getSupportFragmentManager());
        TrackServiceFragments trackServiceFragments = new TrackServiceFragments();
        Bundle tsBundle = new Bundle();
        tsBundle.putInt(ConstantExtraKey.TRACKING_SERVICE_POSITION,0);
        trackServiceFragments.setArguments(tsBundle);

        TrackServiceFragments closedtrackServiceFragments = new TrackServiceFragments();
        Bundle closedBundle = new Bundle();
        closedBundle.putInt(ConstantExtraKey.TRACKING_SERVICE_POSITION,1);
        closedtrackServiceFragments.setArguments(closedBundle);

        trackServiceViewPagerAdapter.addFragment(trackServiceFragments,getResources().getString(R.string.Fragment_Active));
        trackServiceViewPagerAdapter.addFragment(closedtrackServiceFragments,getResources().getString(R.string.Fragment_Closed));
        viewPager.setAdapter(trackServiceViewPagerAdapter);
    }

    @Override
    public void onButtonYesClick(String from) {
        super.onButtonYesClick(from);
        if(from.equalsIgnoreCase(getString(R.string.No_Network_connection)))
        {
            finish();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //listener for home
        if (item.getItemId() == android.R.id.home) {
            if (isBackAllowed)
            {
                if(isFromNotification)
                {
                    Intent in = new Intent(TrackServiceActivity.this,DashboardActivity.class);
                    startActivity(in);
                    finish();
                }else
                    finish();
            }
            else
                drawerLayout.openDrawer(navigationView);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if(isFromNotification)
        {
            Intent in = new Intent(TrackServiceActivity.this,DashboardActivity.class);
            startActivity(in);
            finish();
        }
        else
            super.onBackPressed();
    }
}
