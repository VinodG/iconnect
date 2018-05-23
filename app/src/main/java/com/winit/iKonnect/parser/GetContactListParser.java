package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.ContactListDO;
import com.winit.iKonnect.dataobject.ContactsListDOArrayList;
import com.winit.iKonnect.dataobject.ServiceHistoryDO;

import java.util.ArrayList;

/**
 * Created by Ojha.Sandeep on 12/6/2017.
 */

public class GetContactListParser extends BaseJsonHandler {

    private ContactsListDOArrayList TopicModels;

    @Override
    public Object getData() {
        return TopicModels;
    }

    @Override
    public void parse(StringBuilder response) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        TopicModels = gsonBuilder.create().fromJson(response.toString(), ContactsListDOArrayList.class);

    }
}
