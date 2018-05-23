package com.winit.iKonnect.module.SplashScreen;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.dataobject.HoveringDo;
import com.winit.iKonnect.module.SplashScreen.interacter.SplashScreenInteractor;
import com.winit.iKonnect.module.base.BasePresenter;

/**
 * Created by Rohitmanohar on 12-09-2017.
 */

public class SplashScreenPresentor extends BasePresenter implements ISplashScreenPresentor
{
    private ISplashScreenView view;
    private SplashScreenInteractor interactor;
    public SplashScreenPresentor(ISplashScreenView iBaseView)
    {
        super(iBaseView);
        this.view=iBaseView;
        interactor = new SplashScreenInteractor(this);
    }

    @Override
    public void loadData()
    {
        interactor.postHoveringData();
    }

    @Override
    public void getHoveringData(Object object)
    {
        if(object instanceof HoveringDo)
        {
            HoveringDo hoveringDo = (HoveringDo) object;
            preference.saveIntInPreference(Preference.NotificationCount,hoveringDo.NotificationCount);
            preference.saveIntInPreference(Preference.PostCount,hoveringDo.PostCount);
            preference.saveIntInPreference(Preference.ServiceCount,hoveringDo.ServiceCount);
            view.showAlert(AppConstants.Key_Succes);
        }
    }
}
