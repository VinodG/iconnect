package com.winit.iKonnect.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.util.NetworkUtility;
import com.winit.common.util.StringUtils;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.DashboardViewPagerAdapter;
import com.winit.iKonnect.adapter.ServicesAdapter;
import com.winit.iKonnect.adapter.TrackServiceViewPagerAdapter;
import com.winit.iKonnect.dataobject.NotificationDO;
import com.winit.iKonnect.dataobject.ServiceRequestDO;
import com.winit.iKonnect.dataobject.ServiceResponseData;
import com.winit.iKonnect.module.dashboard.DashboardPresenter;
import com.winit.iKonnect.module.dashboard.IDashboardPresenter;
import com.winit.iKonnect.module.dashboard.IDashboardView;
import com.winit.iKonnect.module.trackService.ITrackServicePresenter;
import com.winit.iKonnect.module.trackService.ITrackServiceView;
import com.winit.iKonnect.module.trackService.TrackServicePresenter;
import com.winit.iKonnect.parser.NotificationParser;
import com.winit.iKonnect.ui.fragments.DashboardFragment;
import com.winit.iKonnect.ui.fragments.FeedsFragement;
import com.winit.iKonnect.ui.fragments.TrackServiceFragments;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rahul.Yadav on 5/12/2017.
 */

public class DashboardActivity extends BaseActivity implements IDashboardView, ITrackServiceView {

    /*@Nullable
    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Nullable
    @Bind(R.id.viewpager)
    ViewPager viewPager;*/
    @Nullable
    @Bind(R.id.flTrack)
    FrameLayout flTrack;

    @Nullable
    @Bind(R.id.flFeeds)
    FrameLayout flFeeds;

    @Nullable
    @Bind(R.id.rv_hrServices)
    RecyclerView rvServices;

    @Nullable
    @Bind(R.id.indicator)
    View indicator;

    @Nullable
    @Bind(R.id.tvFeeds)
    TextView tvFeeds;

    @Nullable
    @Bind(R.id.tvTrack)
    TextView tvTrack;
    @Nullable
    @Bind(R.id.llService)
    LinearLayout llService;

    @Nullable
    @Bind(R.id.tvServices)
    TextView tvServices;

    /*** New Track using TAB              added by sandeep****/
    @Nullable
    @Bind(R.id.tabs_TrackService)
    TabLayout tabs_TrackService;

    @Nullable
    @Bind(R.id.viewpager_trackService)
    ViewPager viewpager_trackService;

    @Nullable
    @Bind(R.id.tv_requsetedon)
    TextView tv_requsetedon;

    private TrackServiceViewPagerAdapter trackServiceViewPagerAdapter;

    /******************************   end  ***************************/

    TrackServiceFragments trackServiceFragments;
    public ITrackServicePresenter iTrackServicePresenter;
    private FeedsFragement feedsFragement;

    private ServicesAdapter servicesAdapter;
    public IDashboardPresenter iDashboardPresenter;
    private DashboardViewPagerAdapter dashboardViewPagerAdapter;
    private LinearLayout.LayoutParams indicatorParams;
    private HashMap<String, ServiceResponseData> hmFormDataDetail;
    private String fromstaff = "";
    protected boolean isFirstViewPager = false;

    @Override
    protected void initialize() {

        if (getIntent().hasExtra("from")) {
            fromstaff = getIntent().getExtras().getString("from");
        }

        inflater.inflate(R.layout.dashboard_activity, flBody, true);
        ButterKnife.bind(this);
        indicator.setLayoutParams(indicatorParams = new LinearLayout.LayoutParams((preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 0) / 3), (int) (3 * BaseActivity.px)));
        fab.setImageResource(R.drawable.fab);
        iDashboardPresenter = new DashboardPresenter(this);
        feedsFragement = new FeedsFragement();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.flFeeds, feedsFragement).commit();

        iTrackServicePresenter = new TrackServicePresenter(this);

        tabs_TrackService.setupWithViewPager(viewpager_trackService);
        setupViewPagerForTrack(viewpager_trackService);
        viewpager_trackService.setCurrentItem(0);

       /* if (isNetworkConnectionAvailable()) {
            showLoader("" + getString(R.string.loading));
            iTrackServicePresenter.fetchTrackServices();
        } else
            showCustomDialog(DashboardActivity.this, getString(R.string.alert), getString(R.string.No_Network_connection), getString(R.string.OK), "", "gotoDashboard", false);
*/
        if (NetworkUtility.isNetworkConnectionAvailable(DashboardActivity.this))
            iDashboardPresenter.getThoughtOfTheDayFromService(preference.getStringFromPreference(Preference.STAFF_NUMBER, ""));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppConstants.RESET_FEEDS);
        registerReceiver(resetFeedsReceiver, intentFilter);

        isFirstViewPager = true;


        if (isNetworkConnectionAvailable()) {
            showLoader("" + getString(R.string.loading));
            iDashboardPresenter.getFormActivationStatus();
        } else
            showCustomDialog(DashboardActivity.this, getString(R.string.alert), getString(R.string.No_Network_connection), getString(R.string.OK), "", "gotoDashboard", false);

        //***********************we are directly coming from Track Details page************************************

        if (getIntent().hasExtra("FROM_DETAILS")){
            slideToTrack();
//            tabs_TrackService.setupWithViewPager(viewpager_trackService);
//            setupViewPagerForTrack(viewpager_trackService);
//            viewpager_trackService.setCurrentItem(0);
        }

        /********************************************************************************************************/
        viewpager_trackService.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
              //  tabs_TrackService.getTabAt(position).setIcon(R.drawable.bluebox);
            }
        });
    }

    @Override
    protected void setTypeFace() {
        tvFeeds.setTypeface(AppConstants.MEDIUM);
        tvServices.setTypeface(AppConstants.MEDIUM);
        tvTrack.setTypeface(AppConstants.MEDIUM);
    }

    @Override
    public void onBackPressed() {

        if (fromstaff.equalsIgnoreCase("staffdetail")) {
            System.exit(0);
        } else if (!isFirstViewPager) {
            tvFeeds.performClick();
        } else if (isFirstViewPager) {
            System.exit(0);
        } else
            super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNetworkConnectionAvailable()) {
            String profilPic = preference.getStringFromPreference(preference.STAFF_PHOTO_URL, "");
            if (!StringUtils.isEmpty(profilPic))
                IKonnectApplication.setImageUrl(ivProfile, ServiceUrls.PROFILE_PIC + profilPic, R.drawable.profile_pic);
        } else {
            showCustomDialog(DashboardActivity.this, getString(R.string.alert), getString(R.string.No_Network_connection), getString(R.string.OK), "", "", false);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        dashboardViewPagerAdapter = new DashboardViewPagerAdapter(getSupportFragmentManager());
        DashboardFragment feedsFragment = new DashboardFragment();
        Bundle feedsBundle = new Bundle();
        feedsBundle.putInt(ConstantExtraKey.DASHBOARD_POSITION, 0);
        feedsFragment.setArguments(feedsBundle);

        DashboardFragment serviceFragment = new DashboardFragment();
        Bundle servicesBundle = new Bundle();
        servicesBundle.putInt(ConstantExtraKey.DASHBOARD_POSITION, 1);
        serviceFragment.setArguments(servicesBundle);

        dashboardViewPagerAdapter.addFragment(feedsFragment, "News Feeds");
        dashboardViewPagerAdapter.addFragment(serviceFragment, "Service Request");
        viewPager.setAdapter(dashboardViewPagerAdapter);
    }

    @Nullable
    @OnClick(R.id.tvFeeds)
    public void slideToFeeds() {
        fab.setVisibility(View.VISIBLE);
        /***********************To set Track Service Spinner default to Active********************************/
        AppConstants.track = 0;
        /***********************To set Track Service Spinner default to Active********************************/
        isFirstViewPager = true;
        indicatorParams.setMargins(0, 0, 0, 0);
        indicatorParams.setMarginStart(0);
        indicator.setLayoutParams(indicatorParams);
        fab.setVisibility(View.VISIBLE);

        flFeeds.setVisibility(View.VISIBLE);
        rvServices.setVisibility(View.GONE);
        llService.setVisibility(View.GONE);
        flTrack.setVisibility(View.GONE);
        tvFeeds.setTextColor(getResources().getColor(R.color.tab_select));
        tvServices.setTextColor(getResources().getColor(R.color.text_dark));
        tvTrack.setTextColor(getResources().getColor(R.color.text_dark));
        tvFeeds.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.newsfeed_select), null, null, null);
        tvServices.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.services), null, null, null);
        tvTrack.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.search), null, null, null);
    }


    @Nullable
    @OnClick(R.id.tvServices)
    public void slideToServices() {

        fab.setVisibility(View.GONE);
        /***********************To set Track Service Spinner default to Active********************************/
        AppConstants.track = 0;
        /***********************To set Track Service Spinner default to Active********************************/

        isFirstViewPager = false;
        llService.setVisibility(View.VISIBLE);
        flTrack.setVisibility(View.GONE);
        tvFeeds.setTextColor(getResources().getColor(R.color.text_dark));
        tvServices.setTextColor(getResources().getColor(R.color.tab_select));
        tvTrack.setTextColor(getResources().getColor(R.color.text_dark));
        tvFeeds.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.newsfeed), null, null, null);
        tvServices.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.services_select), null, null, null);
        tvTrack.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.search), null, null, null);

        indicatorParams.setMarginStart((preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 0) / 3));
        indicator.setLayoutParams(indicatorParams);
        flFeeds.setVisibility(View.GONE);
        rvServices.setVisibility(View.VISIBLE);
        fab.setVisibility(View.GONE);

    }

    @Nullable
    @OnClick(R.id.tvTrack)
    public void slideToTrack() {
        isFirstViewPager = false;
        flFeeds.setVisibility(View.GONE);
        rvServices.setVisibility(View.GONE);
        llService.setVisibility(View.GONE);
        flTrack.setVisibility(View.VISIBLE);
        fab.setVisibility(View.GONE);

        if (isNetworkConnectionAvailable()) {
            showLoader("" + getString(R.string.loading));
            iTrackServicePresenter.fetchTrackServices();
        } else
            showCustomDialog(DashboardActivity.this, getString(R.string.alert), getString(R.string.No_Network_connection), getString(R.string.OK), "", "gotoDashboard", false);

        indicatorParams.setMarginStart((preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 0) / 3) * 2);
        indicator.setLayoutParams(indicatorParams);

        tvFeeds.setTextColor(getResources().getColor(R.color.text_dark));
        tvServices.setTextColor(getResources().getColor(R.color.text_dark));
        tvTrack.setTextColor(getResources().getColor(R.color.tab_select));
        tvFeeds.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.newsfeed), null, null, null);
        tvServices.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.services), null, null, null);
        tvTrack.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.search_select), null, null, null);

        flTrack.setVisibility(View.GONE);
    }

    @Override
    public void onButtonYesClick(String from) {
        if (from.equalsIgnoreCase("gotoDashboard"))
            tvFeeds.performClick();
        else
            super.onButtonYesClick(from);
    }

    @Override
    public void StoreThoughtoftheDay(String s, String s_arabic) {
        preference.saveStringInPreference(Preference.THOUGHT_OF_THE_DAY, s);
        preference.saveStringInPreference(Preference.THOUGHT_OF_THE_DAY_ARABIC, s_arabic);
        preference.commitPreference();
    }

    @Override
    public void onServices() {
        if (servicesAdapter == null) {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(DashboardActivity.this, 3);
            rvServices.setLayoutManager(mLayoutManager);
            rvServices.setItemAnimator(new DefaultItemAnimator());
            rvServices.setAdapter(servicesAdapter = new ServicesAdapter(DashboardActivity.this, iDashboardPresenter.getServiceDOs()));
        } else {
            servicesAdapter.refresh();
        }
    }

    @Override
    public void gotFormData(final HashMap<String, ServiceResponseData> hmFormDataDetail) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iDashboardPresenter.loadData(hmFormDataDetail);
                hideLoader();
            }
        }, 100);

    }

    @Override
    public void showAlert(String type) {
        String message = "";
        switch (type) {
            case AppConstants.Logout:
                showCustomDialog(DashboardActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
                break;
            default:
                message = type;
                showCustomDialog(DashboardActivity.this, getString(R.string.No_Network_connection), message, getString(R.string.OK), "", "");
                break;
        }
    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void performFabAction() {
        if (checkNetworkConnection())
            feedsFragement.showFilters();
    }

    BroadcastReceiver resetFeedsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            slideToFeeds();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(resetFeedsReceiver);
    }

    @Override
    public void onTrackServices(List<ServiceRequestDO> trackServices) {
        hideLoader();
        if (trackServices != null && trackServices.size() > 0) {
            trackServiceViewPagerAdapter.refresh();
        }
    }

    /*****************************     Track New Code *******************************************/

    private void setupViewPagerForTrack(ViewPager viewPager) {

        trackServiceViewPagerAdapter = new TrackServiceViewPagerAdapter(getSupportFragmentManager());
        TrackServiceFragments trackServiceFragments = new TrackServiceFragments();
        Bundle tsBundle = new Bundle();
        tsBundle.putInt(ConstantExtraKey.TRACKING_SERVICE_POSITION, 0);
        trackServiceFragments.setArguments(tsBundle);

        TrackServiceFragments closedtrackServiceFragments = new TrackServiceFragments();
        Bundle closedBundle = new Bundle();
        closedBundle.putInt(ConstantExtraKey.TRACKING_SERVICE_POSITION, 1);
        closedtrackServiceFragments.setArguments(closedBundle);

        trackServiceViewPagerAdapter.addFragment(trackServiceFragments, getResources().getString(R.string.Fragment_Active));
        trackServiceViewPagerAdapter.addFragment(closedtrackServiceFragments, getResources().getString(R.string.Fragment_Closed));
        viewPager.setAdapter(trackServiceViewPagerAdapter);
    }
}
