package com.winit.iKonnect.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.util.NetworkUtility;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.FeedsAdapter;
import com.winit.iKonnect.dataobject.CategoryDO;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.dataobject.PostFeedActionDO;
import com.winit.iKonnect.dataobject.response.FeedsResponse;
import com.winit.iKonnect.module.category.CategoryPresenter;
import com.winit.iKonnect.module.category.ICategoryFilterListener;
import com.winit.iKonnect.module.category.ICategoryPresenter;
import com.winit.iKonnect.module.category.ICategoryView;
import com.winit.iKonnect.module.feed.FeedActionPresenter;
import com.winit.iKonnect.module.feed.FeedBinder;
import com.winit.iKonnect.module.feed.FeedPresenter;
import com.winit.iKonnect.module.feed.IFeedActionPresenter;
import com.winit.iKonnect.module.feed.IFeedActionView;
import com.winit.iKonnect.module.feed.IFeedPresenter;
import com.winit.iKonnect.module.feed.IFeedView;
import com.winit.iKonnect.ui.activities.BaseActivity;
import com.winit.iKonnect.ui.dialog.CategoryFilterDialog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gufran.Khan on 6/1/2017.
 */

public class FeedsFragement extends Fragment implements IFeedView,IFeedActionView,ICategoryFilterListener,ICategoryView {

    private Context context;

    @Nullable
    @Bind(R.id.rvFeeds)
    RecyclerView rvFeeds;

    @Nullable
    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private FeedsAdapter feedsAdapter;
    private CategoryFilterDialog categoryFilterDialog;
    LinearLayoutManager feedsLayoutManager;
    private boolean loading = true, isFabClicked;

    public IFeedPresenter iFeedPresenter;
    public IFeedActionPresenter iFeedActionPresenter;
    public ICategoryPresenter iCategoryPresenter;
    private FeedBinder feedBinder;
    private String message;
    private int favourite;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        iFeedPresenter = new FeedPresenter(this);
        iFeedActionPresenter = new FeedActionPresenter(this);
        iCategoryPresenter = new CategoryPresenter(this);
        feedBinder = new FeedBinder(getActivity());
        feedBinder.setiBasePresenter(iFeedActionPresenter);
        feedBinder.setiCategoryPresenter(iCategoryPresenter);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppConstants.REFRESH_FEEDS);
        context.registerReceiver(refershFeedsReceiver,intentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        favourite = getActivity().getIntent().getIntExtra(ConstantExtraKey.FEED_TYPE,0);
        iFeedPresenter.setFavourite(favourite);
        if(NetworkUtility.isNetworkConnectionAvailable(context)) {
            iCategoryPresenter.fetchCategories();
            iFeedPresenter.fetchFeeds();
            message = String.format(getString(R.string.loading_feeds));
        }else{
            message = getString(R.string.No_Network_connection);
        }
        if(favourite == FeedsDO.NON_FAVOURITE) {
            rvFeeds.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    int pastVisiblesItems, visibleItemCount, totalItemCount;
                    if (dy > 0) //check for scroll down
                    {
                        visibleItemCount = feedsLayoutManager.getChildCount();
                        totalItemCount = feedsLayoutManager.getItemCount();
                        pastVisiblesItems = feedsLayoutManager.findFirstVisibleItemPosition();
//                        feedsLayoutManager.getChildCount() + feedsLayoutManager.findFirstVisibleItemPosition() >= feedsLayoutManager.getItemCount()
                        if (loading) {
                            if ((visibleItemCount + pastVisiblesItems) + 50 >= totalItemCount) {
                                loading = false;
                                iFeedPresenter.fetchFeeds(iFeedPresenter.getFeedsDOs());
                            }
                        }
                    }
                }
            });
        }
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(((BaseActivity)context).checkNetworkConnection())
                    iFeedPresenter.fetchFeeds();
            }
        });
        populateFeeds();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppConstants.REFRESH_FEEDS);
        context.registerReceiver(refershFeedsReceiver,intentFilter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(NetworkUtility.isNetworkConnectionAvailable(context))
            iCategoryPresenter.fetchCategories();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(refershFeedsReceiver);
    }

    private void populateFeeds() {
        List<FeedsDO> feedsDOs = iFeedPresenter.getFeedsDOs();
        if (feedsAdapter == null) {
            feedsLayoutManager = new LinearLayoutManager(context);
            rvFeeds.setLayoutManager(feedsLayoutManager);
            rvFeeds.setItemAnimator(new DefaultItemAnimator());
            rvFeeds.setAdapter(feedsAdapter = new FeedsAdapter(context, feedsDOs, iFeedPresenter.getArrSelected(),feedBinder,iFeedPresenter,message));
        } else {
            feedsAdapter.refresh(feedsDOs,iFeedPresenter.getArrSelected(),message);
        }
    }

    @Override
    public void onFeeds(List<FeedsDO> feeds) {
        loading = true;
        mSwipeRefreshLayout.setRefreshing(false);
        populateFeeds();
    }

    @Override
    public void noOfFeeds() {
        mSwipeRefreshLayout.setRefreshing(false);
        message = getString(R.string.no_feeds);
        populateFeeds();
    }

    @Override
    public void onActionSuccess(String actionType, FeedsDO feedsDO) {
        if(favourite == FeedsDO.FAVOURITE){
            if(actionType.equalsIgnoreCase(PostFeedActionDO.FAVORITE)){
                if(!feedsDO.getCmspostuserModel().isHasFavorite())
                    iFeedPresenter.getFeedsDOs().remove(feedsDO);
            }
            if(iFeedPresenter.getFeedsDOs().size() == 0)
                message = getString(R.string.no_feeds);
        }
        populateFeeds();
    }

    @Override
    public void setSingleFeed(FeedsResponse feedresponseDo) {

    }

    @Override
    public void showAlert(String type) {
        if(type.equalsIgnoreCase(AppConstants.Logout))
        {

        }
    }

    @Override
    public void onLoadFailed() {

    }

    public void showFilters(){
        ((BaseActivity)context).showLoader(getString(R.string.fetching_categories));
        isFabClicked = true;
        iCategoryPresenter.fetchCategories();
    }

    @Override
    public void onCancel() {
        categoryFilterDialog.dismiss();
    }

    @Override
    public void onApply() {
        if(categoryFilterDialog != null){
            if(categoryFilterDialog.getHmSelected() == null)
                iFeedPresenter.applyFilter(new LinkedHashMap<CategoryDO, ArrayList<CategoryDO>>());
            else
                iFeedPresenter.applyFilter(categoryFilterDialog.getHmSelected());
            categoryFilterDialog.dismiss();
        }
    }

    @Override
    public void onCategoryList(LinkedHashMap<Integer, ArrayList<CategoryDO>> hmCategoryDOs) {
        if(isFabClicked) {
            ((BaseActivity)context).hideLoader();
            categoryFilterDialog = new CategoryFilterDialog(context, this, hmCategoryDOs, iFeedPresenter.getHmSelected());
            categoryFilterDialog.showCustomDialog();
            isFabClicked = false;
        }
    }

    BroadcastReceiver refershFeedsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            FeedsDO refreshFeed = (FeedsDO) intent.getSerializableExtra(ConstantExtraKey.FEED_OBJECT);
            FeedsDO feedsDO = iFeedActionPresenter.getFeedsDO();
            if(feedsDO != null){
                feedsDO.setCmspoststatModel(refreshFeed.getCmspoststatModel());
                feedsDO.setCmspostuserModel(refreshFeed.getCmspostuserModel());
                feedsAdapter.notifyDataSetChanged();
            }
        }
    };

    public void resetPosition() {
        if(rvFeeds != null)
            rvFeeds.smoothScrollToPosition(0);
    }
}
