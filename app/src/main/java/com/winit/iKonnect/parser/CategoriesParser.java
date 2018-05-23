package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.response.CategoryListResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *  on 09-12-2016.
 */

public class CategoriesParser extends BaseJsonHandler {

    private CategoryListResponse categoryListResponse;

    @Override
    public Object getData() {
        return categoryListResponse;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        categoryListResponse = gsonBuilder.create().fromJson(response.toString(), CategoryListResponse.class);
    }

    public void parseUsingInputStream(InputStream inputStream) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        categoryListResponse = gsonBuilder.create().fromJson(new InputStreamReader(inputStream), CategoryListResponse.class);
    }

}
