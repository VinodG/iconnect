package com.winit.iKonnect.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.databinding.FeedsCellBinding;
import com.winit.iKonnect.dataobject.CategoryDO;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.module.feed.FeedBinder;
import com.winit.iKonnect.module.feed.IFeedPresenter;
import com.winit.iKonnect.ui.activities.BaseActivity;
import com.winit.iKonnect.ui.customview.CustomImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Girish Velivela on 5/9/2017.
 */

public class FeedsAdapter  extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {

    private Context context;
    private List<FeedsDO> feedsDOs;
    private ArrayList<CategoryDO> categoryDOs;
    private FeedBinder feedBinder;
    private IFeedPresenter iFeedPresenter;
    private String message;
    private final int HEADER_POSITION = 0;
    private int FOOTER_POSITION = 1;
    private boolean isArabic;

    public FeedsAdapter(Context context, List<FeedsDO> feedsDOs, ArrayList<CategoryDO> categoryDOs,
                        FeedBinder feedBinder,IFeedPresenter iFeedPresenter,String message){
        this.context = context;
        this.feedsDOs = feedsDOs;
        this.feedBinder = feedBinder;
        this.categoryDOs = categoryDOs;
        this.iFeedPresenter = iFeedPresenter;
        this.message = message;
        isArabic = Preference.getInstance().getStringFromPreference(Preference.LANGUAGE,"").equalsIgnoreCase(AppConstants.ARABIC);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == HEADER_POSITION){
            RecyclerView recyclerView = (RecyclerView) layoutInflater.inflate(R.layout.selected_category,null);
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager();
            layoutManager.setFlexWrap(FlexWrap.WRAP);
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setAlignItems(AlignItems.STRETCH);
            layoutManager.setAutoMeasureEnabled(true);
            recyclerView.setLayoutManager(layoutManager);
            /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);*/
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            FeedsAdapter.ViewHolder viewHolder = new ViewHolder(recyclerView);
            viewHolder.categoryAdapter = new CategoryAdapter(context, categoryDOs, iFeedPresenter);
            viewHolder.recyclerView = recyclerView;
            recyclerView.setAdapter(viewHolder.categoryAdapter);
            return viewHolder;
        }else if(viewType == FOOTER_POSITION){
            TextView tvNoFeeds = new TextView(context);
            tvNoFeeds.setGravity(Gravity.CENTER);
            tvNoFeeds.setTypeface(AppConstants.REGULAR);
            ViewHolder viewHolder = new ViewHolder(tvNoFeeds);
            viewHolder.tvNoFeeds = tvNoFeeds;
            return viewHolder;
        }else {
            FeedsCellBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.feeds_cell, parent, false);
            ViewHolder viewHolder = new ViewHolder(binding);
            /*app:setImage="@{ServiceUrls.FEEDS_DATA+feedDo.coverPictureEnUrl}"
            app:viewFeedImage="@{feedBinder,feedDo}"*/
            FeedsDO feedsDO = feedsDOs.get(viewType - 2);
            Bundle params = new Bundle();
            params.putInt(FirebaseAnalytics.Param.ITEM_ID, feedsDO.getId());
            params.putString(FirebaseAnalytics.Param.ITEM_NAME, feedsDO.getSubtitleEn());
            ((BaseActivity)context).mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST,params);
            viewHolder.customImageView = (CustomImageView) binding.getRoot().findViewById(R.id.ivFeedImage);
            return viewHolder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position == HEADER_POSITION) {
            holder.categoryAdapter.refresh(categoryDOs);
        }else if(position == FOOTER_POSITION){
            RecyclerView.LayoutParams params;
            if(feedsDOs != null && feedsDOs.size()>0){
                params = new RecyclerView.LayoutParams(0, 0);
                params.setMargins(0,0,0,0);
            }else {
                params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(10,10,10,10);
            }
            holder.tvNoFeeds.setLayoutParams(params);
            holder.tvNoFeeds.setText(message);
        }else{
            final FeedsDO feedsDO = feedsDOs.get(position - 2);
//            IKonnectApplication.setImageUrl(holder.customImageView, ServiceUrls.FEEDS_DATA+feedsDO.getCoverPictureEnUrl());
            holder.bind(feedsDO);
        }
    }

    @Override
    public void onViewDetachedFromWindow(FeedsAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled(FeedsAdapter.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        if(feedsDOs != null)
            return feedsDOs.size()+2;
        return 2;
    }

    public void refresh(List<FeedsDO> feedsDOs,ArrayList<CategoryDO> categoryDOs,String message) {
        this.feedsDOs = feedsDOs;
        this.categoryDOs = categoryDOs;
        this.message = message;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FeedsCellBinding binding;
        private CategoryAdapter categoryAdapter;
        private RecyclerView recyclerView;
        private TextView tvNoFeeds;
        private CustomImageView customImageView;
        private LinearLayout ll_feed;

        public ViewHolder(View view) {
            super(view);
        }
        public ViewHolder(FeedsCellBinding binding) {
            super(binding.getRoot());
            IKonnectApplication.setTypeFace((ViewGroup) binding.getRoot(), AppConstants.REGULAR);
            this.binding = binding;
        }

        public void bind(FeedsDO feedsDO) {
            feedsDO.setArabic(isArabic);
            binding.setFeedDo(feedsDO);
            binding.setFeedBinder(feedBinder);
            binding.setIsCommentEnable(true);
            binding.executePendingBindings();
        }
    }
}


