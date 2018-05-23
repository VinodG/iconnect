package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.ForgetotpDo;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ashoka.Reddy on 7/22/2017.
 */

public class ForgetPasswordParser extends BaseJsonHandler {
    private ForgetotpDo formResponse;

    @Override
    public Object getData() {
        return formResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        formResponse = gsonBuilder.create().fromJson(response.toString(), ForgetotpDo.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        formResponse = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), ForgetotpDo.class);
    }
}
