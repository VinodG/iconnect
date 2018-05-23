package com.winit.iKonnect.ui.activities;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.util.CalendarUtil;
import com.winit.common.util.NetworkUtility;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.NotificationAdapter;
import com.winit.iKonnect.dataobject.NotificationDO;
import com.winit.iKonnect.module.inbox.IInboxPresenter;
import com.winit.iKonnect.module.inbox.IInboxView;
import com.winit.iKonnect.module.inbox.InboxPresenter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gufran.Khan on 6/27/2017.
 */

public class InboxActivity extends BaseActivity implements IInboxView{

    @Nullable
    @Bind(R.id.rvFeeds)
    RecyclerView rvFeeds;

    @Nullable
    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private IInboxPresenter iInboxPresenter;

    private NotificationAdapter notificationAdapter;
    LinearLayoutManager feedsLayoutManager;
    private boolean loading = true;
    private String message;
    NotificationDO notificationDO;
    boolean isFromNotification;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.inbox_activity,flBody,true);
        ButterKnife.bind(this);
        setToolbarTitle(getString(R.string.InBox));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iInboxPresenter = new InboxPresenter(this);
        notificationDO = (NotificationDO) getIntent().getSerializableExtra(ConstantExtraKey.NOTIFICATION_OBJECT);
        isFromNotification = getIntent().getBooleanExtra(ConstantExtraKey.IS_FROM_NOTIFICATION,false);
        if(NetworkUtility.isNetworkConnectionAvailable(InboxActivity.this)) {
            iInboxPresenter.getInboxData();
            message = String.format(getString(R.string.loading_notifications));
        }else{
            message = getString(R.string.No_Network_connection);
        }
        rvFeeds.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int pastVisiblesItems, visibleItemCount, totalItemCount;
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = feedsLayoutManager.getChildCount();
                    totalItemCount = feedsLayoutManager.getItemCount();
                    pastVisiblesItems = feedsLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) + 50 >= totalItemCount) {
                            loading = false;
                            iInboxPresenter.getInboxData(iInboxPresenter.getNotificationDOs());
                        }
                    }
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(checkNetworkConnection())
                    iInboxPresenter.getInboxData();
            }
        });
        populateNotifications();

    }

    @Override
    protected void onPause() {
        super.onPause();
        isFromNotification=false;
        notificationDO=null;
    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    public void showAlert(String type) {
        if(type.equalsIgnoreCase(AppConstants.Logout))
        {
            showCustomDialog(InboxActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        }
    }

    @Override
    public void onLoadFailed() {
        mSwipeRefreshLayout.setRefreshing(false);
        message = getString(R.string.no_Notications);
        populateNotifications();
    }

    @Override
    public void onInboxMessages(List<NotificationDO> notificationDOs) {
        loading = true;
        mSwipeRefreshLayout.setRefreshing(false);
       populateNotifications();
    }

    private void populateNotifications() {
        List<NotificationDO> notificationDOs = iInboxPresenter.getNotificationDOs();
        if (notificationAdapter == null) {
            feedsLayoutManager = new LinearLayoutManager(InboxActivity.this);
            rvFeeds.setLayoutManager(feedsLayoutManager);
            rvFeeds.setItemAnimator(new DefaultItemAnimator());
            rvFeeds.setAdapter(notificationAdapter = new NotificationAdapter(InboxActivity.this, notificationDOs,message));
        } else {
            if (notificationDOs != null) {
                Collections.sort(notificationDOs, new Comparator<NotificationDO>() {
                    @Override
                    public int compare(NotificationDO serviceRequestDO, NotificationDO t1) {
                        return (int) CalendarUtil.getDifferenceTimezone1(t1.getCreatedOn(), serviceRequestDO.getCreatedOn(), CalendarUtil.SEC_DATE_PATTERN, "");
                    }
                });
            }
            if(notificationDO!=null)
            notificationAdapter.refresh(notificationDOs,message,notificationDO.getId());
            else
                notificationAdapter.refresh(notificationDOs,message,0);
//            if(isFromNotification)
//            rvFeeds.scrollToPosition(rvFeeds.getAdapter().getItemCount() - 1);
        }
    }
}
