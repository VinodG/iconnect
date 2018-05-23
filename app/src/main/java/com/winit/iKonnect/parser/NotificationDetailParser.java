package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.NotificationDetailResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *  on 09-12-2016.
 */

public class NotificationDetailParser extends BaseJsonHandler {

    private NotificationDetailResponse notificationDetailResponse;

    @Override
    public Object getData() {
        return notificationDetailResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        notificationDetailResponse = gsonBuilder.create().fromJson(response.toString(), NotificationDetailResponse.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        notificationDetailResponse = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), NotificationDetailResponse.class);
    }

}
