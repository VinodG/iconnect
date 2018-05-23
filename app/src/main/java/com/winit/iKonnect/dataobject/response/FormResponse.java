package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.ServiceRequestDO;

/**
 * Created by Rahul.Yadav on 5/23/2017.
 */

public class FormResponse extends BaseResponse {

    private ServiceRequestDO servicerequestModel;

    public ServiceRequestDO getServicerequestModel() {
        return servicerequestModel;
    }

    public void setServicerequestModel(ServiceRequestDO servicerequestModel) {
        this.servicerequestModel = servicerequestModel;
    }

}
