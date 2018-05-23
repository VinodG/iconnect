package com.winit.iKonnect.module.staffdetail.Interactor;

import com.winit.common.constant.AppConstants;
import com.winit.common.util.StringUtils;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.request.ServiceRequest;
import com.winit.iKonnect.dataobject.request.ServiceRequestBody;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;
import com.winit.iKonnect.module.staffdetail.iStaffDetailPresentor;

/**
 * Created by Ashoka.Reddy on 6/16/2017.
 */

public class StaffDetailInteractor extends AsyncBaseHttpInteractor implements iStaffDetailInteractor {

    iStaffDetailPresentor iStaffDetailPresentor;

    public StaffDetailInteractor(iStaffDetailPresentor iStaffDetailPresentor){
        this.iStaffDetailPresentor = iStaffDetailPresentor;
    }


    @Override
    public void onResponseReceived(Response response) {
        if(response != null &&  response.data instanceof String){
            if(response.data instanceof String && !StringUtils.isEmpty((String) response.data))
                iStaffDetailPresentor.onProfilSuccess();
        }else
        {
            iStaffDetailPresentor.onError(AppConstants.Logout);
        }
    }

    @Override
    public void postAttachments(ServiceRequest serviceRequest) {
        HttpService httpService = new HttpService();
        httpService.executeImageUploadAsyncTask(ServiceUrls.ServiceAction.UPDATE_PROFILE_PIC, ((ServiceRequestBody)serviceRequest.getServiceRequestBody()).getArrServiceAttachments(),((ServiceRequestBody)serviceRequest.getServiceRequestBody()).getServiceId(), this);
    }
}
