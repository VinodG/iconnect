package com.winit.iKonnect.module.WelcomScreen;

import com.winit.iKonnect.dataobject.WelcomeMessageDO;
import com.winit.iKonnect.module.base.IBaseView;

/**
 * Created by Rohitmanohar on 12-07-2017.
 */

public interface IWelcomScreenView extends IBaseView
{
    public void LoadMessage(WelcomeMessageDO message);
}
