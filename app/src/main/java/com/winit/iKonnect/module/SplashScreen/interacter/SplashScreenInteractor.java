package com.winit.iKonnect.module.SplashScreen.interacter;

import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.module.SplashScreen.ISplashScreenPresentor;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 * Created by Rohitmanohar on 12-09-2017.
 */

public class SplashScreenInteractor extends AsyncBaseHttpInteractor implements ISplashScreenIntractor,HttpService.HttpListener
{
    private ISplashScreenPresentor iSplashScreenPresentor;
    public SplashScreenInteractor(ISplashScreenPresentor iSplashScreenPresentor)
    {
        this.iSplashScreenPresentor=iSplashScreenPresentor;
    }
    @Override
    public void postHoveringData()
    {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.POST_HOVERING_SERVICE,"",this);
    }

    @Override
    public void onResponseReceived(Response response)
    {
        iSplashScreenPresentor.getHoveringData(response.data);
    }
}
