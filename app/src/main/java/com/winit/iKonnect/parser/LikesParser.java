package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.LikesResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *  on 09-12-2016.
 */

public class LikesParser extends BaseJsonHandler {

    private LikesResponse likesResponse;

    @Override
    public Object getData() {
        return likesResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        likesResponse = gsonBuilder.create().fromJson(response.toString(), LikesResponse.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        likesResponse = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), LikesResponse.class);
    }

}
