package com.winit.iKonnect.module.dashboard;

import com.winit.iKonnect.dataobject.ServiceDO;
import com.winit.iKonnect.dataobject.ServiceResponseData;
import com.winit.iKonnect.module.base.IBasePresenter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public interface IDashboardPresenter extends IBasePresenter{
    ArrayList<ServiceDO> getServiceDOs();
    void getThoughtOfTheDayFromService(String Staffno);
    void getFormActivationStatus();
    void loadData(HashMap<String, ServiceResponseData> hmFormDataDetail);

}
