package com.winit.iKonnect.module.changePassword.interacter;

import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.response.BaseResponse;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;
import com.winit.iKonnect.module.changePassword.ChangePasswordPresenter;

/**
 * Created by Rahul.Yadav on 6/7/2017.
 */

public class ChangePasswordInteracter extends AsyncBaseHttpInteractor implements IChangePasswordInteracter{

    private OnChnagePasswordListener onChnagePasswordListener;

    public ChangePasswordInteracter(ChangePasswordPresenter changePasswordPresenter) {
        super();
        this.onChnagePasswordListener = changePasswordPresenter;
    }

    @Override
    public void onResponseReceived(Response response) {
        if(response != null){
            if(response.data != null){
                BaseResponse baseResponse = (BaseResponse) response.data;
                if(baseResponse.getStatusCode() == 200){
//                    this.onLoginFinishedListener.onSuccess(loginResponse.getStatusMessageEn(), loginResponse.getServicerequestModel());
                    this.onChnagePasswordListener.onSuccess(baseResponse.getStatusCode(),baseResponse.getStatusMessageEn());
                }else
                    this.onChnagePasswordListener.onError(baseResponse.getStatusMessageEn());

                return;
            }
        }
        this.onChnagePasswordListener.onError("Unable to connect.");

    }

    @Override
    public void postData(String StaffNumber, String newPassword, String oldPassword) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_CHANGE_PASSWORD, BuildJsonRequest.getChangePasswordRequest(StaffNumber,newPassword,oldPassword), this);

    }

    public static interface OnChnagePasswordListener {
        void onError(String Message);
        void onSuccess(int status, String object);
    }
}
