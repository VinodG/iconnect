package com.winit.iKonnect.module.serviceHistory;

import com.winit.iKonnect.dataobject.ServiceHistoryDO;
import com.winit.iKonnect.dataobject.ServiceRequestListDO;
import com.winit.iKonnect.module.serviceHistory.interactor.AsyncServiceHistroyInteractor;
import com.winit.iKonnect.module.serviceHistory.interactor.IAsyncServiceHistroyInteractor;

/**
 * Created by Ojha.Sandeep on 11/28/2017.
 */

public class ServiceRequestHistroyPresenter implements IServiceHistoryPresenter, AsyncServiceHistroyInteractor.OnSereviceHistoryListener {

    private IServiceHistoryView iServiceHistoryView;
    private IAsyncServiceHistroyInteractor iAsyncServiceHistroyInteractor;


    public ServiceRequestHistroyPresenter(IServiceHistoryView iServiceHistoryView) {
        this.iServiceHistoryView = iServiceHistoryView;
        iAsyncServiceHistroyInteractor = new AsyncServiceHistroyInteractor(this);
    }

    @Override
    public void submitFormId(int id) {
        iAsyncServiceHistroyInteractor.serviceHistory(id);
    }


    @Override
    public void onError(String Message) {

    }

    @Override
    public void onSuccess(Object object) {
        if (object != null) {
            ServiceHistoryDO serviceHistoryDO = (ServiceHistoryDO) object;
            iServiceHistoryView.getServiceHistoryData(serviceHistoryDO);
        }
    }
}
