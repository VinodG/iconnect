package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.ContactsListDOArrayList;
import com.winit.iKonnect.dataobject.EmpdetailsModels;

/**
 * Created by Sreekanth.P on 28-12-2017.
 */

class GetEmployeeListParser extends BaseJsonHandler {

    EmpdetailsModels empdetailsModels;
    @Override
    public Object getData() {
        return empdetailsModels;
    }

    @Override
    public void parse(StringBuilder response) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        empdetailsModels = gsonBuilder.create().fromJson(response.toString(), EmpdetailsModels.class);
    }
}
