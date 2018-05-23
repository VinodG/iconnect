package com.winit.iKonnect.module.trackService;

import com.winit.iKonnect.dataobject.ServiceRequestDO;
import com.winit.iKonnect.module.base.IBaseView;

import java.util.List;

/**
 * Created by Rahul.Yadav on 5/25/2017.
 */

public interface ITrackServiceView extends IBaseView{
    void onTrackServices(List<ServiceRequestDO> trackServices);
}
