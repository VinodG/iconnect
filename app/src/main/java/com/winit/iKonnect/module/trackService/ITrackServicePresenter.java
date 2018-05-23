package com.winit.iKonnect.module.trackService;

import com.winit.iKonnect.dataobject.ServiceRequestDO;
import com.winit.iKonnect.module.base.IBasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul.Yadav on 5/25/2017.
 */

public interface ITrackServicePresenter extends IBasePresenter{
    ArrayList<ServiceRequestDO> getTrackServicesDOs(String type);

    void fetchTrackServices();
    void fetchTrackServices(List<ServiceRequestDO> trackServiceDOs);
}
