package com.winit.iKonnect.dataobject;

import com.winit.iKonnect.dataobject.response.BaseResponse;

import java.io.Serializable;

/**
 * Created by Ojha.Sandeep on 11/29/2017.
 */

public class ServiceHistoryDO extends BaseResponse implements Serializable {
    private ServiceRequestListDO Service_Request;


    public ServiceRequestListDO getService_Request() {
        return Service_Request;
    }

    public void setService_Request(ServiceRequestListDO service_Request) {
        Service_Request = service_Request;
    }
}
