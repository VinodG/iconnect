package com.winit.iKonnect.module.myDocuments.interactors;

import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.response.DocumentsUploadDO;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;
import com.winit.iKonnect.module.myDocuments.MyDocumentsPresenter;

/**
 * Created by Sreekanth.P on 06-12-2017.
 */

public class AsyncMyDocumentsInteractor extends AsyncBaseHttpInteractor implements IAsyncMyDocumentsInteractor{

    public OnMyDocumentsListener onMyDocumentsListener;

    public AsyncMyDocumentsInteractor(OnMyDocumentsListener onMyDocumentsListener) {
        this.onMyDocumentsListener=onMyDocumentsListener;
    }

    @Override
    public void getAllDocuments(String id) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_USER_DOCUMENTS, BuildJsonRequest.getPostDocumentsRequest(id),this);
    }

    @Override
    public void onResponseReceived(Response response) {

        if (response!=null){

            if (response!=null && response.data instanceof DocumentsUploadDO){
                DocumentsUploadDO documentsUploadDO=(DocumentsUploadDO)response.data;

                if (documentsUploadDO.getStatusCode()==200){
                    this.onMyDocumentsListener.onSuccess(documentsUploadDO);
                    return;
                }
                if (documentsUploadDO.getStatusCode()==451){

                }else {
                    this.onMyDocumentsListener.onError(isArabic ? documentsUploadDO.getStatusMessageAr() : documentsUploadDO.getStatusMessageEn());
                }
            }
        }
    }

    public interface OnMyDocumentsListener{
        void onError(String Message);

        void onSuccess(Object object);
    }
}
