package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.BaseResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Rahul.Yadav on 6/7/2017.
 */

public class BaseResponseParser extends BaseJsonHandler {
   private BaseResponse baseResponse;

    @Override
    public Object getData() {
        return baseResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        baseResponse = gsonBuilder.create().fromJson(response.toString(),BaseResponse.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        baseResponse = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), BaseResponse.class);
    }
}
