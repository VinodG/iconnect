package com.winit.iKonnect.module.myDocuments;

import com.winit.iKonnect.dataobject.MyDocumentsDO;
import com.winit.iKonnect.dataobject.response.DocumentsUploadDO;
import com.winit.iKonnect.module.base.IBaseView;

import java.util.ArrayList;

/**
 * Created by Sreekanth.P on 06-12-2017.
 */

public interface IMyDocumentsView extends IBaseView {

    public void getDocumentsUpload(ArrayList<MyDocumentsDO> uploadDOArrayList);
    public void showAlert(String message);
}
