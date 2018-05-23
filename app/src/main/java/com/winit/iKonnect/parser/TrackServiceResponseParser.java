package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.ServiceListResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Rahul.Yadav on 5/25/2017.
 */

public class TrackServiceResponseParser extends BaseJsonHandler {
   private ServiceListResponse serviceListResponse;

    @Override
    public Object getData() {
        return serviceListResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        serviceListResponse = gsonBuilder.create().fromJson(response.toString(),ServiceListResponse.class);
    }
    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        serviceListResponse = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), ServiceListResponse.class);
    }
}
