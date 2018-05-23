package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.CommentsResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *  on 09-12-2016.
 */

public class CommentsParser extends BaseJsonHandler {

    private CommentsResponse commentsResponse;

    @Override
    public Object getData() {
        return commentsResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        commentsResponse = gsonBuilder.create().fromJson(response.toString(), CommentsResponse.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        commentsResponse = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), CommentsResponse.class);
    }

}
