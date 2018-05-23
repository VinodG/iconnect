package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.PostFeedActionResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *  on 09-12-2016.
 */

public class PostFeedActionParser extends BaseJsonHandler {

    private PostFeedActionResponse postFeedActionResponse;

    @Override
    public Object getData() {
        return postFeedActionResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        postFeedActionResponse = gsonBuilder.create().fromJson(response.toString(), PostFeedActionResponse.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        postFeedActionResponse = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), PostFeedActionResponse.class);
    }

}
