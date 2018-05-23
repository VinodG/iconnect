package com.winit.iKonnect.module.usernavigationactivity;

import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.base.IBaseView;
import com.winit.iKonnect.module.usernavigationactivity.Interactor.INavigationInteractor;
import com.winit.iKonnect.module.usernavigationactivity.Interactor.NavigationInteractor;

/**
 * Created by Ashoka.Reddy on 7/21/2017.
 */

public class NavigationPresentor  extends BasePresenter implements INavigationPresenter {
    private INavigationInteractor navigationInteractor;
    public NavigationPresentor(IBaseView iBaseView) {
        super(iBaseView);
        navigationInteractor= new NavigationInteractor();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void pushServiceForLogout(String StaffNumber, String DeviceID) {
        navigationInteractor.callService(StaffNumber,DeviceID);
    }
}
