package com.winit.iKonnect.module.inbox.interactors;

import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.NotificationDO;
import com.winit.iKonnect.dataobject.response.InboxResponse;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

import java.util.List;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public class InboxInteractor extends AsyncBaseHttpInteractor implements IInboxInteractor {

    private OnInboxDataListener listener;

    public InboxInteractor(OnInboxDataListener listener){
        this.listener = listener;
    }

    @Override
    public void fetchInboxs(String UserCode, String dateTime) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_INBOX, BuildJsonRequest.getInboxParams(UserCode,dateTime), this);
    }

    @Override
    public void fetchInboxs(final List<NotificationDO> inboxDOs, final String UserCode, final String dateTime) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpService httpService = new HttpService();
                Response response = httpService.executeTask(ServiceUrls.ServiceAction.GET_INBOX, BuildJsonRequest.getInboxParams(UserCode,dateTime));
                if(response != null){
                    if(response.data != null){
                        InboxResponse inboxResponse = (InboxResponse) response.data;
                        if(inboxResponse.getStatusCode() == 200){
                            if(inboxResponse.getEnoticeModels() != null) {
                                inboxDOs.addAll(inboxResponse.getEnoticeModels());
                                listener.onSuccess(inboxDOs);
                            }
                        }else if(inboxResponse.getStatusCode() == AppConstants.LOGOUT_ERROR_CODE)
                        {
                            listener.onError(AppConstants.Logout);
                        }
                        return;
                    }
                }
            }
        }).start();
    }

    @Override
    public void onResponseReceived(Response response) {
        if(response != null){
            if(response.data != null) {
                InboxResponse inboxResponse = (InboxResponse) response.data;
                if (inboxResponse.getStatusCode() == 200) {
                    listener.onSuccess(inboxResponse.getEnoticeModels());
                } else
                    listener.onError(inboxResponse.getStatusMessageEn());
                return;
            }
        }
        listener.onError("Unable to connect.");
    }
}
