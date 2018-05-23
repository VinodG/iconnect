package com.winit.iKonnect.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.ViewGroup;

import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.BR;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.FeedDetail;

import java.util.List;

/**
 * Created by Girish Velivela on 5/15/2017.
 */

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder>  {

    private Context context;
    private List<FeedDetail> details;
    private String type;

    public DetailsAdapter(Context context, List<FeedDetail> feedDetails,String type) {
        this.context = context;
        this.details = feedDetails;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.detail_cell, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FeedDetail object = details.get(position);
        holder.bind(object);
    }

    @Override
    public int getItemCount() {
        if(details != null)
            return details.size();
        return 0;
    }

    public void refresh(List<FeedDetail> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            IKonnectApplication.setTypeFace((ViewGroup) binding.getRoot(), AppConstants.REGULAR);
            this.binding = binding;
        }

        public void bind(FeedDetail detail) {
            binding.setVariable(BR.feedDetail, detail);
            binding.setVariable(BR.detailType,type);
            binding.executePendingBindings();
        }
    }
}


