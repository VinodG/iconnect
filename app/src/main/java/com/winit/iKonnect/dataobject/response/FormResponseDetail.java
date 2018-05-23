package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.ServiceResponseData;

import java.util.ArrayList;

/**
 * Created by Ashoka.Reddy on 7/12/2017.
 */

public class FormResponseDetail extends BaseResponse {
    public ArrayList<ServiceResponseData> getServicelistModels() {
        return servicelistModels;
    }

    public void setServicelistModels(ArrayList<ServiceResponseData> servicelistModels) {
        this.servicelistModels = servicelistModels;
    }

    private ArrayList<ServiceResponseData> servicelistModels;
}
