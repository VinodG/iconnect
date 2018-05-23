package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.ServiceRequestDO;

import java.util.List;

/**
 * Created by Rahul.Yadav on 5/23/2017.
 */

public class ServiceListResponse extends BaseResponse {

    private List<ServiceRequestDO> servicerequestModels;

    public List<ServiceRequestDO> getServicerequestModels() {
        return servicerequestModels;
    }

    public void setServicerequestModels(List<ServiceRequestDO> servicerequestModels) {
        this.servicerequestModels = servicerequestModels;
    }

}
