package com.winit.iKonnect.module.signUp.interacter;

import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.request.ServiceRequest;
import com.winit.iKonnect.dataobject.request.ServiceRequestBody;
import com.winit.iKonnect.dataobject.response.LoginResponse;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 * Created by Rahul.Yadav on 5/28/2017.
 */

public class SignUpInteracter extends AsyncBaseHttpInteractor implements ISignUpInteractor {
   private OnSignUpListener onSignUpListener;

    public SignUpInteracter(OnSignUpListener signUpPresenter) {
        this.onSignUpListener = signUpPresenter;
    }

    @Override
    public void postData(String staffNumber, String mobile, String workCountry) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.POST_OTP_REQUEST, BuildJsonRequest.getPostOTPRequest(staffNumber,mobile,workCountry), this);
    }
    @Override
    public void postAttachments(ServiceRequest serviceRequest)
    {
        HttpService httpService = new HttpService();
        httpService.executeImageUploadAsyncTask(ServiceUrls.ServiceAction.POST_SERVICE_IMAGE, ((ServiceRequestBody)serviceRequest.getServiceRequestBody()).getArrServiceAttachments(),((ServiceRequestBody)serviceRequest.getServiceRequestBody()).getServiceId(), this);
    }

    @Override
    public void onResponseReceived(Response response) {

            if(response != null){
                if(response.data != null){
                    LoginResponse loginResponse = (LoginResponse) response.data;
                    if(loginResponse.getStatusCode() == 200){
//                    this.onLoginFinishedListener.onSuccess(loginResponse.getStatusMessageEn(), loginResponse.getServicerequestModel());
                        this.onSignUpListener.onSuccess(loginResponse.getStatusCode(),loginResponse.getStatusMessageEn());
                    }else
                        this.onSignUpListener.onError(loginResponse.getStatusMessageEn());

                    return;
                }
            }
            this.onSignUpListener.onError("Unable to connect.");
    }


    public static interface OnSignUpListener {
        void onError(String Message);
        void onSuccess(int status, String object);
    }

}
