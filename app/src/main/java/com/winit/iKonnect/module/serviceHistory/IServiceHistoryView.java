package com.winit.iKonnect.module.serviceHistory;

import com.winit.iKonnect.dataobject.ServiceHistoryDO;
import com.winit.iKonnect.dataobject.ServiceRequestListDO;
import com.winit.iKonnect.module.base.IBaseView;

/**
 * Created by Ojha.Sandeep on 11/28/2017.
 */

public interface IServiceHistoryView extends IBaseView {
    public void getServiceHistoryData(ServiceHistoryDO serviceHistoryDO);

    public void showAlert(String message);


}
