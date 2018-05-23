package com.winit.iKonnect.module.login.interactors;


import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.HoveringDo;
import com.winit.iKonnect.dataobject.LoginDo;
import com.winit.iKonnect.dataobject.response.BaseResponse;
import com.winit.iKonnect.dataobject.response.ForgetotpDo;
import com.winit.iKonnect.dataobject.response.LoginResponse;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 * on 9/25/2016.
 */

public class AsyncLoginInteractor extends AsyncBaseHttpInteractor implements IAsyncLoginInteractor {

    private OnLoginFinishedListener onLoginFinishedListener;

    public AsyncLoginInteractor(OnLoginFinishedListener onLoginFinishedListener) {
        this.onLoginFinishedListener = onLoginFinishedListener;
    }


    @Override
    public void signIn(LoginDo loginDo) {

        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.POST_LOGIN_DATA, BuildJsonRequest.getPostFormRequest(loginDo), this);
    }

    @Override
    public void signUP(String staffNumber) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.POST_SIGN_UP, BuildJsonRequest.getPostSignUPRequest(staffNumber), this);
    }

    @Override
    public void register(String StaffNumber, String deviceType, String deviceId, String password) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.POST_STAFF_REGISTER, BuildJsonRequest.getPostStaffRegister(StaffNumber, deviceType, deviceId, password), this);
    }

    @Override
    public void postForgetPasswordData(String staffNumber) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_FORGET_PASSWORD, BuildJsonRequest.getPostForgetPasswordRequest(staffNumber), this);
    }

    @Override
    public void postData(String StaffNumber, String newPassword, String oldPassword) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_CHANGE_PASSWORD, BuildJsonRequest.getChangePasswordRequest(StaffNumber, newPassword, oldPassword), this);
    }

    @Override
    public void postHoveringData() {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.POST_HOVERING_SERVICE, "", this);
    }

    @Override
    public void onResponseReceived(Response response) {

        if (response != null) {
            if (response.data != null && response.data instanceof LoginResponse) {
                LoginResponse loginResponse = (LoginResponse) response.data;
                if (loginResponse.getStatusCode() == 200) {
//                    this.onLoginFinishedListener.onSuccess(loginResponse.getStatusMessageEn(), loginResponse.getServicerequestModel());
                    this.onLoginFinishedListener.onSuccess(loginResponse);
                }
                if (loginResponse.getStatusCode() == 451) {
//                    this.onLoginFinishedListener.onSuccess(loginResponse.getStatusMessageEn(), loginResponse.getServicerequestModel());
                    this.onLoginFinishedListener.onError(AppConstants.Logout);
                } else
                    this.onLoginFinishedListener.onError(isArabic ? loginResponse.getStatusMessageAr() : loginResponse.getStatusMessageEn());
                return;
            } else if (response.data != null && response.data instanceof ForgetotpDo) {
                ForgetotpDo loginResponse = (ForgetotpDo) response.data;
                if (loginResponse.getStatusCode().equalsIgnoreCase("200")) {
//                    this.onLoginFinishedListener.onSuccess(loginResponse.getStatusMessageEn(), loginResponse.getServicerequestModel());
                    this.onLoginFinishedListener.onSuccess(loginResponse);
                } else
                    this.onLoginFinishedListener.onError(isArabic ? loginResponse.getStatusMessageAr() : loginResponse.getStatusMessageEn());
                return;

            } else if (response.data != null && response.data instanceof BaseResponse) {
                BaseResponse baseResponse = (BaseResponse) response.data;
                if (baseResponse.getStatusCode() == 200) {
//                    this.onLoginFinishedListener.onSuccess(loginResponse.getStatusMessageEn(), loginResponse.getServicerequestModel());
                    this.onLoginFinishedListener.onSuccess(baseResponse);
                } else
                    this.onLoginFinishedListener.onError(isArabic ? baseResponse.getStatusMessageAr() : baseResponse.getStatusMessageEn());
                return;

            } else if (response.data != null && response.data instanceof HoveringDo) {
                HoveringDo hoveringDo = (HoveringDo) response.data;
                if (hoveringDo != null) {
                    this.onLoginFinishedListener.onSuccess(hoveringDo);
                    return;
                }

            }

        }
        this.onLoginFinishedListener.onError("Unable to connect.");
    }

    /**
     * Created by Girish Velivela on 5/11/15.
     */
    public static interface OnLoginFinishedListener {
        void onError(String Message);

        void onSuccess(Object object);
    }
}
