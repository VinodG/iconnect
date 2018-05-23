package com.winit.iKonnect.module.WelcomScreen;

import com.winit.iKonnect.dataobject.WelcomeMessageDO;
import com.winit.iKonnect.module.base.IBasePresenter;

/**
 * Created by Rohitmanohar on 12-07-2017.
 */

public interface IWelcomScreenPresentor extends IBasePresenter
{
    public void getWelcomeMessage();
    public void loadMessage(WelcomeMessageDO messageDO);
}
