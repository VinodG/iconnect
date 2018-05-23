package com.winit.iKonnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.BR;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.ServiceRequestDO;
import com.winit.iKonnect.ui.activities.BaseActivity;
import com.winit.iKonnect.ui.activities.TrackServiceDetails;

import java.util.ArrayList;

/**
 * Created by Rahul.Yadav on 5/25/2017.
 */

public class TrackServicesAdapter extends RecyclerView.Adapter<TrackServicesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ServiceRequestDO> trackServiceDOs;
    private TrackServiceBinder trackServiceBinder;




    public TrackServicesAdapter(Context context,ArrayList<ServiceRequestDO> trackServiceDOs) {
        this.trackServiceDOs = trackServiceDOs;
        this.context = context;
        trackServiceBinder = new TrackServiceBinder();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.track_service_cell,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {


        final ServiceRequestDO trackServiceDO = trackServiceDOs.get(position);

        //when we are coming from Notification directly navigating to Details page
        if(trackServiceDO.getStatus().equalsIgnoreCase("Pending"))
        {
            trackServiceDO.setStatus(context.getString(R.string.Pending));
            trackServiceDO.setStatusToCheck(ServiceRequestDO.PENDING);
        }else if(trackServiceDO.getStatus().equalsIgnoreCase("processing request"))
        {
            trackServiceDO.setStatus(context.getString(R.string.proc_req));
            trackServiceDO.setStatusToCheck(ServiceRequestDO.REJECTED);
        }
        else if(trackServiceDO.getStatus().equalsIgnoreCase("Rejected"))
        {
            trackServiceDO.setStatus(context.getString(R.string.rejected));
            trackServiceDO.setStatusToCheck(ServiceRequestDO.REJECTED);
        }
        else if(trackServiceDO.getStatus().equalsIgnoreCase("ready to collect from your HR representative"))
        {
            trackServiceDO.setStatus(context.getString(R.string.read_to_collect_from_your_HR_representative));
            trackServiceDO.setStatusToCheck(ServiceRequestDO.READY_TO_COLLECT);
        }
        else if(trackServiceDO.getStatus().equalsIgnoreCase("Approved and will be send to your location"))
        {
            trackServiceDO.setStatus(context.getString(R.string.Approved_and_will_be_send_to_your_location));
            trackServiceDO.setStatusToCheck(ServiceRequestDO.APPROVED_TO_COLLECT);
        }
        else if(trackServiceDO.getStatus().equalsIgnoreCase("Approved and ready to collect from your HR representative"))
        {
            trackServiceDO.setStatus(context.getString(R.string.collect_HR));
            trackServiceDO.setStatusToCheck(ServiceRequestDO.READY_TO_COLLECT_FROM_HR);
        }
        else if(trackServiceDO.getStatus().equalsIgnoreCase("approved"))
        {
            trackServiceDO.setStatus(context.getString(R.string.approved));
            trackServiceDO.setStatusToCheck(ServiceRequestDO.APPROVED);
        }
        else if(trackServiceDO.getStatus().equalsIgnoreCase("work in progress"))
        {
            trackServiceDO.setStatus(context.getString(R.string.work_in_progress));
            trackServiceDO.setStatusToCheck(ServiceRequestDO.Work_in_progress);
        }
        viewHolder.bind(trackServiceDO);

//        viewHolder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        if(trackServiceDOs!=null)
            return trackServiceDOs.size();
        return 0;
    }

    public void refresh(ArrayList<ServiceRequestDO> trackServiceDOs) {
        this.trackServiceDOs = trackServiceDOs;
        notifyDataSetChanged();
    }



    public void sendToTrackDetails(){

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ServiceRequestDO serviceRequestDO)
        {
            binding.setVariable(BR.serviceRequestDO,serviceRequestDO);
            binding.setVariable(BR.serviceBinder,trackServiceDOs);
            binding.setVariable(BR.trackServiceBinder,trackServiceBinder);
            binding.executePendingBindings();
        }
    }

    public class TrackServiceBinder{
        public void showReason(ServiceRequestDO serviceRequestDO){
            if (serviceRequestDO.getStatus().equalsIgnoreCase(ServiceRequestDO.REJECTED)) {
                ((BaseActivity) context).showCustomDialog(context, context.getString(R.string.RejectReason), serviceRequestDO.getHrComments(), context.getString(R.string.OK), "", "", false);
            }else {
                ((BaseActivity) context).showCustomDialog(context, context.getString(R.string.HoldReason), serviceRequestDO.getHrComments(), context.getString(R.string.OK), "", "", false);
            }
        }
        public void gotoDetails(ServiceRequestDO serviceRequestDO){
            Intent intent=new Intent(context, TrackServiceDetails.class);
            intent.putExtra("FROM_TRACK",serviceRequestDO);
            ((BaseActivity)context).startActivity(intent);
        }
    }
}

