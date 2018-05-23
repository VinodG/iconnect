package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.PaySlipResponseDo;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Rahul.Yadav on 6/20/2017.
 */

public class PaySlipResponseParser  extends  BaseJsonHandler{

    private PaySlipResponseDo paySlipResponseDo;

    @Override
    public Object getData() {
        return paySlipResponseDo;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        paySlipResponseDo = gsonBuilder.create().fromJson(response.toString().replace("Wage type", "Wagetype"),PaySlipResponseDo.class);
    }
    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        paySlipResponseDo = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), PaySlipResponseDo.class);
    }
}
