package com.winit.iKonnect.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.databinding.SelectedCategoryListCellBinding;
import com.winit.iKonnect.dataobject.CategoryDO;
import com.winit.iKonnect.module.feed.IFeedPresenter;

import java.util.List;

/**
 * Created by Girish Velivela on 5/9/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<CategoryDO> categoryDOs;
    private IFeedPresenter iFeedPresenter;
    private boolean isArabic;

    public CategoryAdapter(Context context, List<CategoryDO> categoryDOs, IFeedPresenter iFeedPresenter){
        this.context = context;
        this.categoryDOs = categoryDOs;
        this.iFeedPresenter = iFeedPresenter;
        isArabic = Preference.getInstance().getStringFromPreference(Preference.LANGUAGE,"").equalsIgnoreCase(AppConstants.ARABIC);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SelectedCategoryListCellBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.selected_category_list_cell, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CategoryDO categoryDO= categoryDOs.get(position);
        holder.bind(categoryDO);
    }

    @Override
    public int getItemCount() {
        if(categoryDOs != null)
            return categoryDOs.size();
        return 0;
    }

    public void refresh(List<CategoryDO> categoryDOs) {
        this.categoryDOs = categoryDOs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final SelectedCategoryListCellBinding binding;

        public ViewHolder(SelectedCategoryListCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CategoryDO categoryDO) {
            categoryDO.setArabic(isArabic);
            binding.setCategoryDO(categoryDO);
            binding.setIFeedPresenter(iFeedPresenter);
            binding.executePendingBindings();
        }
    }
}


