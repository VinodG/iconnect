package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.LoginResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by namashivaya.gangishe on 5/26/2017.
 */

public class LogInResponseParser extends BaseJsonHandler {

    private LoginResponse formResponse;

    @Override
    public Object getData() {
        return formResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        formResponse = gsonBuilder.create().fromJson(response.toString(), LoginResponse.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        formResponse = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), LoginResponse.class);
    }

}

