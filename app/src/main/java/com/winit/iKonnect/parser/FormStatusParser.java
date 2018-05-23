package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.FormResponseDetail;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ashoka.Reddy on 7/12/2017.
 */

public class FormStatusParser extends BaseJsonHandler{
    private FormResponseDetail formResponsedetail;
    //com.winit.iKonnect.dataobject.response.FormResponseDetail

    @Override
    public Object getData() {
        return formResponsedetail;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        formResponsedetail = gsonBuilder.create().fromJson(response.toString(), FormResponseDetail.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        formResponsedetail = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), FormResponseDetail.class);
    }
}
