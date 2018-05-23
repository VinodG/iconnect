package com.winit.iKonnect.module.WelcomScreen.interacter;

import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.WelcomeMessageDO;
import com.winit.iKonnect.module.WelcomScreen.IWelcomScreenPresentor;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 * Created by Rohitmanohar on 12-07-2017.
 */

public class WelcomScreenInteractor extends AsyncBaseHttpInteractor implements IWelcomScreenInteractor,HttpService.HttpListener
{
    private IWelcomScreenPresentor iWelcomScreenPresentor;
    public WelcomScreenInteractor(IWelcomScreenPresentor iWelcomScreenPresentor)
    {
        this.iWelcomScreenPresentor=iWelcomScreenPresentor;
    }

    @Override
    public void onResponseReceived(Response response)
    {
        iWelcomScreenPresentor.loadMessage((WelcomeMessageDO) response.data);
    }

    @Override
    public void getWelcomMessage()
    {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_WELCOME_MESSAGE,"",this);
    }
}
