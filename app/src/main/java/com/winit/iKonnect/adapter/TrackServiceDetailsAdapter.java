package com.winit.iKonnect.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winit.iKonnect.BR;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.ServiceRequestDO;
import com.winit.iKonnect.dataobject.ServicerequestassignmentModelsDO;
import com.winit.iKonnect.ui.activities.BaseActivity;

import java.util.ArrayList;


/**
 * Created by Sreekanth.P on 20-11-2017.
 */

public class TrackServiceDetailsAdapter extends RecyclerView.Adapter<TrackServiceDetailsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ServicerequestassignmentModelsDO> trackServiceDOs;
    private ServiceRequestDO serviceRequestDO;
    private TrackServiceDetailBinder detailBinder;

    public TrackServiceDetailsAdapter(Context context, ArrayList<ServicerequestassignmentModelsDO> servicerequestassignmentModels, ServiceRequestDO serviceRequestDO) {
        this.trackServiceDOs = servicerequestassignmentModels;
        this.context = context;
        this.serviceRequestDO = serviceRequestDO;
        detailBinder = new TrackServiceDetailBinder();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.tracking_details_cell, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final ServicerequestassignmentModelsDO servicerequestassignmentModelsDO = trackServiceDOs.get(position);

        if (servicerequestassignmentModelsDO.getFormId().equalsIgnoreCase("23") &&
                servicerequestassignmentModelsDO.getStatus().equalsIgnoreCase(ServiceRequestDO.APPROVED)) {
            servicerequestassignmentModelsDO.setStatus("In Process");
        }


        viewHolder.bind(servicerequestassignmentModelsDO);
    }

    @Override
    public int getItemCount() {
        if (trackServiceDOs != null)
            return trackServiceDOs.size();
        return 0;
    }

     public void refresh(ArrayList<ServicerequestassignmentModelsDO> trackServiceDOs,ServiceRequestDO serviceRequestDO) {
         this.trackServiceDOs = trackServiceDOs;
         this.serviceRequestDO = serviceRequestDO;
         notifyDataSetChanged();
     }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ServicerequestassignmentModelsDO servicerequestassignmentModelsDO) {
            binding.setVariable(BR.serviceRequestDO, serviceRequestDO);
            binding.setVariable(BR.servicerequestassignmentModelsDO, servicerequestassignmentModelsDO);
            binding.setVariable(BR.detailBinder, detailBinder);
            binding.executePendingBindings();
        }
    }


    public class TrackServiceDetailBinder {

        public void showReason(String role, String message) {
            ((BaseActivity) context).showCustomDialog(context, "Remark by "+role, message, context.getString(R.string.OK), "", "", false);
        }
    }

}
