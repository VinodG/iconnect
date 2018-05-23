package com.winit.iKonnect.module.myDocuments;

import android.content.Context;

import com.winit.iKonnect.dataobject.response.DocumentsUploadDO;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.base.IBaseView;
import com.winit.iKonnect.module.myDocuments.interactors.AsyncMyDocumentsInteractor;
import com.winit.iKonnect.module.myDocuments.interactors.IAsyncMyDocumentsInteractor;
import com.winit.iKonnect.module.serviceHistory.interactor.AsyncServiceHistroyInteractor;

/**
 * Created by Sreekanth.P on 01-12-2017.
 */

public class MyDocumentsPresenter implements IMyDocumentsPresenter, AsyncMyDocumentsInteractor.OnMyDocumentsListener {

    private IMyDocumentsView iMyDocumentsView;
    private IAsyncMyDocumentsInteractor iAsyncMyDocumentsInteractor;

    public MyDocumentsPresenter(IMyDocumentsView iMyDocumentsView) {

        this.iMyDocumentsView = iMyDocumentsView;
        iAsyncMyDocumentsInteractor = new AsyncMyDocumentsInteractor(this);
    }

    @Override
    public void getUserDocuments(String id) {
        iAsyncMyDocumentsInteractor.getAllDocuments(id);
    }

    @Override
    public void onError(String Message) {
        iMyDocumentsView.showAlert(Message);
    }

    @Override
    public void onSuccess(Object object) {

        if (object != null && ((DocumentsUploadDO) object).getDocumentsUpload() != null) {
//        if (object != null  ) {
            DocumentsUploadDO documentsUploadDO = (DocumentsUploadDO) object;
            iMyDocumentsView.getDocumentsUpload(documentsUploadDO.getDocumentsUpload());
        } else {
            iMyDocumentsView.showAlert("No data found");
        }
    }
}
