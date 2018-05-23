package com.winit.iKonnect.module.serviceHistory.interactor;

import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.ServiceHistoryDO;
import com.winit.iKonnect.dataobject.ServiceRequestListDO;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 * Created by Ojha.Sandeep on 11/28/2017.
 */

public class AsyncServiceHistroyInteractor extends AsyncBaseHttpInteractor implements IAsyncServiceHistroyInteractor {

    private OnSereviceHistoryListener onSereviceHistoryListener;

    public AsyncServiceHistroyInteractor(OnSereviceHistoryListener onSereviceHistoryListener) {
        this.onSereviceHistoryListener = onSereviceHistoryListener;
    }

    @Override
    public void serviceHistory(int formId) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_SERVICE_HISTORY, BuildJsonRequest.getPostServiceHistroyRequest(formId),this);
    }

    @Override
    public void onResponseReceived(Response response) {

        if (response!=null) {
            if (response.data != null && response.data instanceof ServiceHistoryDO) {
                ServiceHistoryDO serviceHistoryDO = (ServiceHistoryDO) response.data;
                if (serviceHistoryDO.getStatusCode() == 200) {
//                    this.onLoginFinishedListener.onSuccess(loginResponse.getStatusMessageEn(), loginResponse.getServicerequestModel());
                    this.onSereviceHistoryListener.onSuccess(serviceHistoryDO);
                }
                if (serviceHistoryDO.getStatusCode() == 451) {
                    //commneted by sandeep
                    //this.onSereviceHistoryListener.onError(AppConstants.Logout);
                } else
                    this.onSereviceHistoryListener.onError(isArabic ? serviceHistoryDO.getStatusMessageAr() : serviceHistoryDO.getStatusMessageEn());

            }
        }
    }

    public interface OnSereviceHistoryListener{

        void onError(String Message);

        void onSuccess(Object object);
    }
}
