package com.winit.iKonnect.module.trackService.interacter;

import com.winit.iKonnect.dataobject.ServiceRequestDO;
import com.winit.iKonnect.module.base.interactors.IBaseInteractor;

import java.util.List;

/**
 * Created by Rahul.Yadav on 5/25/2017.
 */

public interface ITrackInteracter  extends IBaseInteractor {

void fetchServices(String staff_Id,String status);

    interface  OnTrackServiceListener extends BaseListener
    {
        void onSuccess(List<ServiceRequestDO> trackServices);
    }
}
