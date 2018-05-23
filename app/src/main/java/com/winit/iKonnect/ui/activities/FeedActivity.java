package com.winit.iKonnect.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.util.NetworkUtility;
import com.winit.iKonnect.R;
import com.winit.iKonnect.databinding.FeedActivityBinding;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.dataobject.NotificationDO;
import com.winit.iKonnect.dataobject.PostFeedActionDO;
import com.winit.iKonnect.dataobject.response.FeedsResponse;
import com.winit.iKonnect.module.feed.FeedActionPresenter;
import com.winit.iKonnect.module.feed.FeedBinder;
import com.winit.iKonnect.module.feed.IFeedActionPresenter;
import com.winit.iKonnect.module.feed.IFeedActionView;
import com.winit.iKonnect.parser.NotificationParser;
import com.winit.iKonnect.ui.fragments.DetailsFragment;
import com.winit.iKonnect.ui.fragments.FeedsFragement;

import butterknife.ButterKnife;

/**
 * Created by Girish Velivela on 5/17/2017.
 */

public class FeedActivity extends BaseActivity implements IFeedActionView {

    private FeedsFragement feedsFragement;

    private IFeedActionPresenter iFeedActionPresenter;
    private FeedActivityBinding feedActivityBinding;
    private FeedBinder feedBinder;
    private DetailsFragment detailsFragment;
    private int favourite;
    private String from = "";
    private String id = "";
    private String where="";
    private NotificationDO notificationDO;

    @Override
    protected void initialize() {

        favourite = getIntent().getIntExtra(ConstantExtraKey.FEED_TYPE, 0);
        feedBinder = new FeedBinder(FeedActivity.this);

        if(getIntent().hasExtra("where"))
            where =  getIntent().getExtras().getString("where");

        ///**************************Directly we are coming from notification****************************
        if(getIntent().hasExtra("message"))
        {
            String message = getIntent().getExtras().getString("message");
            NotificationParser notificationParser = new NotificationParser();
            notificationParser.parse(new StringBuilder(message));
            notificationDO = (NotificationDO) notificationParser.getData();
            favourite = FeedsDO.FROM_NOTIFICATION;
            id = notificationDO.getId()+"";
            iFeedActionPresenter = new FeedActionPresenter(this);
            iFeedActionPresenter.getSingleFeed(id);
        }
        else if(getIntent().hasExtra(ConstantExtraKey.NOTIFICATION_OBJECT))
        {
            notificationDO = (NotificationDO) getIntent().getExtras().get(ConstantExtraKey.NOTIFICATION_OBJECT);
            favourite = FeedsDO.FROM_NOTIFICATION;
            id = notificationDO.getId()+"";
            iFeedActionPresenter = new FeedActionPresenter(this);
            iFeedActionPresenter.getSingleFeed(id);
        }
        ///************************************************************************************************
        if (favourite == FeedsDO.FAVOURITE) {
            ButterKnife.bind(this);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
            setToolbarTitle(getString(R.string.my_favourate));
            fab.setVisibility(View.VISIBLE);
            fab.setImageResource(R.drawable.fab);
            feedsFragement = new FeedsFragement();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.flBody, feedsFragement).commit();

        }
        else if (favourite == FeedsDO.FROM_NOTIFICATION)
        {

        }
        else
        {
            isBackAllowed = true;
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            feedActivityBinding = DataBindingUtil.inflate(inflater, R.layout.feed_activity, flBody, true);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            detailsFragment = new DetailsFragment();
            transaction.add(R.id.flComments, detailsFragment).commit();
            ButterKnife.bind(feedActivityBinding.getRoot(), this);
            CardView cvFeed = (CardView) findViewById(R.id.cvFeed);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 0);
            cvFeed.setLayoutParams(layoutParams);
            cvFeed.setCardElevation(0);
            FeedsDO feedsDO = (FeedsDO) getIntent().getSerializableExtra(ConstantExtraKey.FEED_OBJECT);
            setToolbarTitle(getIntent().getStringExtra(ConstantExtraKey.CATEGORY_NAME));
            iFeedActionPresenter = new FeedActionPresenter(this);
            feedBinder.setCanOpen(false);

            feedActivityBinding.setFeedDo(feedsDO);
            feedActivityBinding.setIsCommentEnable(false);
            feedActivityBinding.setFeedBinder(feedBinder);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(AppConstants.REFRESH_FEEDS);
            registerReceiver(refershFeedsReceiver, intentFilter);
            if (NetworkUtility.isNetworkConnectionAvailable(FeedActivity.this) && !TextUtils.isEmpty(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")))
                iFeedActionPresenter.performActionFeed(PostFeedActionDO.OPEN, feedsDO);
            feedBinder.setiBasePresenter(iFeedActionPresenter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //listener for home
        if (item.getItemId() == android.R.id.home) {
            if (isBackAllowed)
            {
                if(favourite == FeedsDO.FROM_NOTIFICATION)
                {
                    Intent in = new Intent(FeedActivity.this,DashboardActivity.class);
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
        if(where.equalsIgnoreCase("profile"))
        {
            finish();
        }else
        {
            Intent in = new Intent(FeedActivity.this,DashboardActivity.class);
            startActivity(in);
            finish();
        }
    }

    @Override
    protected void setTypeFace() {

    }


    @Override
    public void onActionSuccess(String type, FeedsDO feedsDO) {
        hideLoader();
        Intent intent = new Intent();
        intent.setAction(AppConstants.REFRESH_FEEDS);
        intent.putExtra(ConstantExtraKey.FEED_OBJECT, feedsDO);
        sendBroadcast(intent);
        feedActivityBinding.setFeedDo(feedsDO);
        feedActivityBinding.executePendingBindings();
    }

    @Override
    public void setSingleFeed(final FeedsResponse feedresponseDo) {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                inflate(feedresponseDo.getCmspostModels().get(0));
            }
        }, 10);
    }

    private void inflate(FeedsDO feedsDO) {
        isBackAllowed = true;
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        feedActivityBinding = DataBindingUtil.inflate(inflater, R.layout.feed_activity, flBody, true);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        detailsFragment = new DetailsFragment();
        transaction.add(R.id.flComments, detailsFragment).commit();
        ButterKnife.bind(feedActivityBinding.getRoot(), this);
        CardView cvFeed = (CardView) findViewById(R.id.cvFeed);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 0);
        cvFeed.setLayoutParams(layoutParams);
        cvFeed.setCardElevation(0);
        setToolbarTitle(feedsDO.getTitleEn());
        iFeedActionPresenter = new FeedActionPresenter(this);
        feedBinder.setCanOpen(false);

        feedActivityBinding.setFeedDo(feedsDO);
        feedActivityBinding.setIsCommentEnable(false);
        feedActivityBinding.setFeedBinder(feedBinder);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppConstants.REFRESH_FEEDS);
        registerReceiver(refershFeedsReceiver, intentFilter);
        if (NetworkUtility.isNetworkConnectionAvailable(FeedActivity.this))
            iFeedActionPresenter.performActionFeed(PostFeedActionDO.OPEN, feedsDO);
        feedBinder.setiBasePresenter(iFeedActionPresenter);
    }


    @Override
    public void showAlert(String type) {
        String message = "";
        switch (type) {
            case AppConstants.Logout:
                showCustomDialog(FeedActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
                break;
            default:
                message = type;
                break;
        }
        showCustomDialog(FeedActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void performFabAction() {
        if (checkNetworkConnection())
            feedsFragement.showFilters();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (favourite == FeedsDO.NON_FAVOURITE)
            unregisterReceiver(refershFeedsReceiver);
    }

    BroadcastReceiver refershFeedsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            FeedsDO refreshFeed = (FeedsDO) intent.getSerializableExtra(ConstantExtraKey.FEED_OBJECT);
            FeedsDO feedsDO = iFeedActionPresenter.getFeedsDO();
            if (feedsDO != null) {
                feedsDO.setCmspoststatModel(refreshFeed.getCmspoststatModel());
                feedsDO.setCmspostuserModel(refreshFeed.getCmspostuserModel());
                feedActivityBinding.setFeedDo(feedsDO);
                feedActivityBinding.executePendingBindings();
            }
        }
    };
}
