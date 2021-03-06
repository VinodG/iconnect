package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.FeedsResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *  on 09-12-2016.
 */

public class FeedsParser extends BaseJsonHandler {

    private FeedsResponse feedsResponse;

    @Override
    public Object getData() {
        return feedsResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        feedsResponse = gsonBuilder.create().fromJson(response.toString(), FeedsResponse.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        feedsResponse = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), FeedsResponse.class);
    }

}
