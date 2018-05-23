package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.ThoughtOfTheDayResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ashoka.Reddy on 5/29/2017.
 */

public class ThoughtOfTheParser extends BaseJsonHandler{
    private ThoughtOfTheDayResponse calenderListResponse;

    @Override
    public Object getData() {
        return calenderListResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        calenderListResponse = gsonBuilder.create().fromJson(response.toString(), ThoughtOfTheDayResponse.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        calenderListResponse = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), ThoughtOfTheDayResponse.class);
    }
}
