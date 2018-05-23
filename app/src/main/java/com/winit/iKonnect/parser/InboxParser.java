package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.InboxResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *  on 09-12-2016.
 */

public class InboxParser extends BaseJsonHandler {

    private InboxResponse inboxResponse;

    @Override
    public Object getData() {
        return inboxResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        inboxResponse = gsonBuilder.create().fromJson(response.toString(), InboxResponse.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        inboxResponse = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), InboxResponse.class);
    }

}
