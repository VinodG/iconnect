package com.winit.iKonnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.iKonnect.R;
import com.winit.iKonnect.databinding.InboxCellBinding;
import com.winit.iKonnect.dataobject.NotificationDO;
import com.winit.iKonnect.ui.activities.NotificationDetailActivity;

import java.util.List;

/**
 * Created by Girish Velivela on 5/9/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    private List<NotificationDO> notificationDOs;
    private String message;
    private final int HEADER_POSITION = 0;
    private boolean isArabic;
    private NotificationBinder notificationBinder;
    private int id;

    public NotificationAdapter(Context context, List<NotificationDO> notificationDOs, String message){
        this.context = context;
        this.notificationDOs = notificationDOs;
        this.message = message;
        notificationBinder = new NotificationBinder();
        isArabic = Preference.getInstance().getStringFromPreference(Preference.LANGUAGE,"").equalsIgnoreCase(AppConstants.ARABIC);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == HEADER_POSITION){
            TextView tvNoFeeds = new TextView(context);
            tvNoFeeds.setGravity(Gravity.CENTER);
            tvNoFeeds.setTypeface(AppConstants.REGULAR);
            ViewHolder viewHolder = new ViewHolder(tvNoFeeds);
            viewHolder.tvNoFeeds = tvNoFeeds;
            return viewHolder;
        }else {
            InboxCellBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.inbox_cell, parent, false);
            ViewHolder viewHolder = new ViewHolder(binding);
            return viewHolder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position == HEADER_POSITION){
            RecyclerView.LayoutParams params;
            if(notificationDOs != null && notificationDOs.size()>0){
                params = new RecyclerView.LayoutParams(0, 0);
                params.setMargins(0,0,0,0);
            }else {
                params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(10,10,10,10);
            }
            holder.tvNoFeeds.setLayoutParams(params);
            holder.tvNoFeeds.setText(message);
        }else{
            final NotificationDO notificationDO = notificationDOs.get(position - 1);
            if(notificationDO.getId()==id)
            {
                notificationBinder.navigateToDetials(notificationDO);
            }
            holder.bind(notificationDO);
        }
    }

    @Override
    public void onViewDetachedFromWindow(NotificationAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled(NotificationAdapter.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        if(notificationDOs != null)
            return notificationDOs.size()+1;
        return 1;
    }

    public void refresh(List<NotificationDO> notificationDOs,String message, int id) {
        this.notificationDOs = notificationDOs;
        this.message = message;
        this.id=id;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private InboxCellBinding binding;
        private RecyclerView recyclerView;
        private TextView tvNoFeeds;

        public ViewHolder(View view) {
            super(view);
        }
        public ViewHolder(InboxCellBinding binding) {
            super(binding.getRoot());
            IKonnectApplication.setTypeFace((ViewGroup) binding.getRoot(), AppConstants.REGULAR);
            this.binding = binding;
        }

        public void bind(NotificationDO notificationDO) {
            notificationDO.setArabic(isArabic);
            binding.setNotificationDO(notificationDO);
            binding.setNotificationBinder(notificationBinder);
            binding.executePendingBindings();
        }
    }

    public class NotificationBinder{
        public void navigateToDetials(NotificationDO notificationDO) {
            /*if (!StringUtils.isEmpty(notificationDO.getType())) {
                if (notificationDO.getType().equalsIgnoreCase("POST")) {

                } else if (notificationDO.getType().equalsIgnoreCase("Notification")) {
                    Intent intent = new Intent(context, NotificationDetailActivity.class);
                    intent.putExtra(ConstantExtraKey.NOTIFICATION_OBJECT, notificationDO);
                    context.startActivity(intent);
                } else {

                }
            }*/
            //Should be remove when from backend "Type" from backend.
//            if(!StringUtils.isEmpty(notificationDO.getImagePath())){
                Intent intent = new Intent(context, NotificationDetailActivity.class);
                intent.putExtra(ConstantExtraKey.NOTIFICATION_OBJECT, notificationDO);
                context.startActivity(intent);
//            }

        }
    }
}


