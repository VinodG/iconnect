package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.ServiceHistoryDO;
import com.winit.iKonnect.dataobject.ServiceRequestListDO;

/**
 * Created by Ojha.Sandeep on 11/28/2017.
 */

public class GetServiceHistoryParser extends BaseJsonHandler {

    private ServiceHistoryDO serviceHistoryResponse;

    @Override
    public Object getData() {
        return serviceHistoryResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        serviceHistoryResponse = gsonBuilder.create().fromJson(response.toString(), ServiceHistoryDO.class);

    }

}
