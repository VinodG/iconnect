package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.dataobject.MyDocumentsDO;
import com.winit.iKonnect.dataobject.ServiceHistoryDO;
import com.winit.iKonnect.dataobject.response.DocumentsUploadDO;

/**
 * Created by Sreekanth.P on 06-12-2017.
 */

class GetUserDocumentsParser extends BaseJsonHandler {
    DocumentsUploadDO documentsUploadDO;
    @Override
    public Object getData() {
        return documentsUploadDO;
    }

    @Override
    public void parse(StringBuilder response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        documentsUploadDO = gsonBuilder.create().fromJson(response.toString(), DocumentsUploadDO.class);
    }
}
