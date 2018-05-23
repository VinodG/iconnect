package com.winit.iKonnect.module.usernavigationactivity.Interactor;

import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 * Created by Ashoka.Reddy on 7/21/2017.
 */

public class NavigationInteractor extends AsyncBaseHttpInteractor implements INavigationInteractor,HttpService.HttpListener{
    @Override
    public void onResponseReceived(Response response) {

    }

    @Override
    public void callService(String staffnumber, String deviceID) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_LOG_OUT_SERVICE, BuildJsonRequest.getLogOutResponse(staffnumber,deviceID),this);
    }
}
