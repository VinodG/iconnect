package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.ChatGroupResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *  on 09-12-2016.
 */

public class ChatGroupParser extends BaseJsonHandler {

    private ChatGroupResponse chatGroupResponse;

    @Override
    public Object getData() {
        return chatGroupResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        chatGroupResponse = gsonBuilder.create().fromJson(response.toString(), ChatGroupResponse.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        chatGroupResponse = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), ChatGroupResponse.class);
    }

}
