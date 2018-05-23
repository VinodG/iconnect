package com.winit.iKonnect.module.form.interactors;

import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.ServiceResponseData;
import com.winit.iKonnect.dataobject.request.ServiceRequest;
import com.winit.iKonnect.dataobject.request.ServiceRequestBody;
import com.winit.iKonnect.dataobject.response.FormResponse;
import com.winit.iKonnect.dataobject.response.FormResponseDetail;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

import java.util.HashMap;

/**
 * Created by Girish Velivela on 5/11/15.
 */

public class AsyncFormInteractor extends AsyncBaseHttpInteractor implements IAsyncFormInteractor {

    private OnFormPostListener listener;
    private HashMap<String, ServiceResponseData> hmFormDataDetail;

    public AsyncFormInteractor(OnFormPostListener listener) {
        this.listener = listener;
    }

    @Override
    public void postForm(ServiceRequest serviceRequest) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.NEW_SERVICE_REQUEST, BuildJsonRequest.getPostFormRequest(serviceRequest), this);
    }

    @Override
    public void postAttachments(ServiceRequest serviceRequest) {
        HttpService httpService = new HttpService();
        httpService.executeImageUploadAsyncTask(ServiceUrls.ServiceAction.POST_SERVICE_IMAGE, ((ServiceRequestBody) serviceRequest.getServiceRequestBody()).getArrServiceAttachments(), ((ServiceRequestBody) serviceRequest.getServiceRequestBody()).getServiceId(), this);
    }

    @Override
    public void postSignature(ServiceRequest serviceRequest) {
        HttpService httpService = new HttpService();
        httpService.executeImageUploadAsyncTask(ServiceUrls.ServiceAction.UPLOAD_SIGNATURE, ((ServiceRequestBody) serviceRequest.getServiceRequestBody()).getArrServiceAttachments(), ((ServiceRequestBody) serviceRequest.getServiceRequestBody()).getServiceId(), ((ServiceRequestBody) serviceRequest.getServiceRequestBody()).getFormid(), this);

    }


    @Override
    public void getFormStatus() {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_FORM_STATUS, BuildJsonRequest.getFormStatus(), this);
    }

    @Override
    public void onResponseReceived(Response response) {
        if (response != null) {
            if (response.data != null && response.data instanceof FormResponse) {
                FormResponse formResponse = (FormResponse) response.data;
                if (formResponse.getStatusCode() == 200) {
                    listener.onSuccess(isArabic ? formResponse.getStatusMessageAr() : formResponse.getStatusMessageEn(), formResponse.getServicerequestModel());
                } else if (formResponse.getStatusCode() == AppConstants.LOGOUT_ERROR_CODE) {
                    listener.onError(AppConstants.Logout);
                } else
                    listener.onError(isArabic ? formResponse.getStatusMessageAr() : formResponse.getStatusMessageEn());
                return;
            } else if (response.data instanceof FormResponseDetail) {
                FormResponseDetail feedsResponse = (FormResponseDetail) response.data;
                if (feedsResponse.getStatusCode() == 200) {
                    if (feedsResponse != null && feedsResponse.getServicelistModels() != null && feedsResponse.getServicelistModels().size() > 0)
                        for (ServiceResponseData servicedo : feedsResponse.getServicelistModels()) {
                            hmFormDataDetail = new HashMap<>();
                            hmFormDataDetail.put(servicedo.getFormName(), servicedo);
                        }
                    listener.gotFormData(hmFormDataDetail);
                } else if (feedsResponse.getStatusCode() == AppConstants.LOGOUT_ERROR_CODE) {
                    listener.onError(AppConstants.Logout);
                }
            } else if (response.data instanceof String) {

                //added by sandeep for attachment purpose
                if (((String) response.data).contains("<Image>Successful</Image>")) {
                    return;
                }
                else if (((String) response.data).contains("Successful")) {
                    listener.onSuccess(AppConstants.Logout);
                }

                return;

            }else
                listener.onError("No Internet connection, Please check your connection and try again.");
        }
    }
}
