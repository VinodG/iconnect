package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.FormResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class FormResponseParser extends BaseJsonHandler {

    private FormResponse formResponse;

    @Override
    public Object getData() {
        return formResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        formResponse = gsonBuilder.create().fromJson(response.toString(), FormResponse.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        formResponse = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), FormResponse.class);
    }

}
