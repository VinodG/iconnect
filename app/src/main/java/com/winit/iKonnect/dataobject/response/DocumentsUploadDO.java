package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.MyDocumentsDO;

import java.util.ArrayList;

/**
 * Created by Sreekanth.P on 06-12-2017.
 */

public class DocumentsUploadDO extends BaseResponse {

    private ArrayList<MyDocumentsDO> DocumentsUpload;

    public ArrayList<MyDocumentsDO> getDocumentsUpload() {
        return DocumentsUpload;
    }

    public void setDocumentsUpload(ArrayList<MyDocumentsDO> documentsUpload) {
        DocumentsUpload = documentsUpload;
    }
}