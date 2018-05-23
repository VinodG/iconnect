package com.winit.iKonnect.module.otpValidation.interacter;

import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.response.LoginResponse;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 * Created by Rahul.Yadav on 5/28/2017.
 */

public class OTPInteracter extends AsyncBaseHttpInteractor implements IOTPInteractor {
   private OnOtpListener onSignUpListener;

    public OTPInteracter(OnOtpListener signUpPresenter) {
        this.onSignUpListener = signUpPresenter;
    }

    @Override
    public void postData(String staffNumber, String mobile, String workCountry) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.POST_OTP_VALIDATION, BuildJsonRequest.getOtpValidateRequest(staffNumber,mobile,workCountry), this);
    }

    @Override
    public void onResponseReceived(Response response) {
        {

            if(response != null){
                if(response.data != null){
                    LoginResponse loginResponse = (LoginResponse) response.data;
                    if(loginResponse.getStatusCode() == 200){
//                    this.onLoginFinishedListener.onSuccess(loginResponse.getStatusMessageEn(), loginResponse.getServicerequestModel());
                        this.onSignUpListener.onSuccess(loginResponse);
                    }else
                        this.onSignUpListener.onError(loginResponse.getStatusMessageEn());

                    return;
                }
            }
            this.onSignUpListener.onError("Unable to connect.");
        }
    }


    public static interface OnOtpListener {
        void onError(String Message);
        void onSuccess(Object object);
    }

}
