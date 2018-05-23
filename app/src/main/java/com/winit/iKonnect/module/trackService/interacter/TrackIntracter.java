package com.winit.iKonnect.module.trackService.interacter;

import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.response.ServiceListResponse;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 * Created by Rahul.Yadav on 5/25/2017.
 */

public class TrackIntracter extends AsyncBaseHttpInteractor implements ITrackInteracter{

    private OnTrackServiceListener listener;

    public TrackIntracter(OnTrackServiceListener listener){
        this.listener = listener;
    }

    @Override
    public void fetchServices(String staff_Id, String status) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_TRACKING_DETAIL, BuildJsonRequest.getTrackServicesParams(staff_Id,"All"),this);
    }

    @Override
    public void onResponseReceived(Response response) {
        if(response!=null)
        {
            if(response.data!=null)
            {
                ServiceListResponse serviceListResponse = (ServiceListResponse) response.data;
                if(serviceListResponse.getStatusCode()==200)
                    listener.onSuccess(serviceListResponse.getServicerequestModels());
                else if(serviceListResponse.getStatusCode()== AppConstants.LOGOUT_ERROR_CODE)
                {
                    listener.onError(AppConstants.Logout);
                }
                else
                    listener.onError(serviceListResponse.getStatusMessageEn());
            }
        }
        listener.onError("Unable to connect.");

    }
}
