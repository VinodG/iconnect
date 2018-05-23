package com.winit.iKonnect.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.winit.common.application.IKonnectApplication;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.BR;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.response.EmpdetailsModelDO;

import java.util.ArrayList;

import static com.winit.iKonnect.R.id.llDetails;

/**
 * Created by Sreekanth.P on 27-12-2017.
 */

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<EmpdetailsModelDO> empModelDOs;
    private int pos=-1;
    public EmployeeListAdapter(Context context, ArrayList<EmpdetailsModelDO> empModelDOs) {
        this.context=context;
        this.empModelDOs=empModelDOs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.employee_list_cell, parent, false);
        return new EmployeeListAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final EmpdetailsModelDO empdetailsModelDO = empModelDOs.get(position);
        IKonnectApplication.setImageUrl(holder.ivProfile, ServiceUrls.PROFILE_PIC +empdetailsModelDO.getPhotoUrl(), R.drawable.profile_pic, true);

        holder.bind(empdetailsModelDO);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos= holder.getPosition();
                if (pos==position)
                    holder.llDetails.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });

        if (pos==position)
            holder.llDetails.setVisibility(View.VISIBLE);
        else
            holder.llDetails.setVisibility(View.GONE);

        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {

        if (empModelDOs!=null && empModelDOs.size()>0)
            return empModelDOs.size();
        else
            return 0;
    }

    public void refresh(ArrayList<EmpdetailsModelDO> arrEmployee) {
        this.empModelDOs=arrEmployee;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;
        private LinearLayout llMain,llDetails;
        private ImageView ivProfile;
        public MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            llMain=(LinearLayout)binding.getRoot().findViewById(R.id.llMain);
            llDetails=(LinearLayout)binding.getRoot().findViewById(R.id.llDetails);
            ivProfile=(ImageView) binding.getRoot().findViewById(R.id.ivProfile);
        }

        public void bind(EmpdetailsModelDO empdetailsModelDO) {
            binding.setVariable(BR.empdetailsModelDO,empdetailsModelDO);
        }
    }
}
