package com.winit.iKonnect.module.WelcomScreen;

import com.winit.iKonnect.dataobject.WelcomeMessageDO;
import com.winit.iKonnect.module.WelcomScreen.interacter.WelcomScreenInteractor;
import com.winit.iKonnect.module.base.BasePresenter;

/**
 * Created by Rohitmanohar on 12-07-2017.
 */

public class WelcomScreenPresentor extends BasePresenter implements IWelcomScreenPresentor
{
    private IWelcomScreenView view;
    private WelcomScreenInteractor interactor;
    public WelcomScreenPresentor(IWelcomScreenView view)
    {
        super(view);
        this.view=view;
        interactor = new WelcomScreenInteractor(this);
    }

    @Override
    public void getWelcomeMessage()
    {
        interactor.getWelcomMessage();
    }

    @Override
    public void loadMessage(WelcomeMessageDO welcomeMessageDO)
    {
        view.LoadMessage(welcomeMessageDO);
    }

    @Override
    public void loadData() {

    }
}
