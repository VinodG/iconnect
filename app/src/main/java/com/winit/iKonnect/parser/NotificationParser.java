package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.NotificationDO;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *  on 09-12-2016.
 */

public class NotificationParser extends BaseJsonHandler {

    private NotificationDO notificationDO;

    @Override
    public Object getData() {
        return notificationDO;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        notificationDO = gsonBuilder.create().fromJson(response.toString(), NotificationDO.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        notificationDO = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), NotificationDO.class);
    }

}
