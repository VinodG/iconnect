package com.winit.iKonnect.module.passwordCreation;

import com.winit.common.util.StringUtils;
import com.winit.iKonnect.dataobject.response.BaseResponse;
import com.winit.iKonnect.dataobject.response.LoginResponse;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.login.interactors.AsyncLoginInteractor;
import com.winit.iKonnect.module.login.interactors.IAsyncLoginInteractor;
import com.winit.iKonnect.ui.activities.PasswordActivity;


import static com.winit.iKonnect.module.login.ILoginView.PASSWORD;

/**
 * Created by Rahul.Yadav on 5/29/2017.
 */

public class PasswordPresenter extends BasePresenter implements IPasswordPresenter,AsyncLoginInteractor.OnLoginFinishedListener {

   private IPasswordView view;
    private IAsyncLoginInteractor interactor;

    public PasswordPresenter(PasswordActivity view) {
        super(view);
        this.view = view;
        this.interactor = new AsyncLoginInteractor(this);
    }

    @Override
    public void submit(String StaffNumber, String deviceType, String deviceId, String password, String ConfirmPassword) {
        if(StringUtils.isEmpty(password)){
            view.showAlert(PASSWORD);
        }
        else if(StringUtils.isEmpty(ConfirmPassword)){
            view.showAlert(view.CONFIRM_PASSWORD);
        }
        else if(!StringUtils.isEmpty(password)&&!StringUtils.isEmpty(ConfirmPassword)&&!password.equals(ConfirmPassword)){
            view.showAlert(view.INVALID_PASSWORD);
        }
        else {
        interactor.postData(StaffNumber,ConfirmPassword,"");
        }
    }

    @Override
    public void register(String StaffNumber, String deviceType, String deviceId, String password, String ConfirmPassword) {
        if(StringUtils.isEmpty(password)){
            view.showAlert(PASSWORD);
        }
        else if(StringUtils.isEmpty(ConfirmPassword)){
            view.showAlert(view.CONFIRM_PASSWORD);
        }
        else if(!StringUtils.isEmpty(password)&&!StringUtils.isEmpty(ConfirmPassword)&&!password.equals(ConfirmPassword)){
            view.showAlert(view.INVALID_PASSWORD);
        }
        else {
            interactor.register(StaffNumber,deviceType,deviceId,password);
        }
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onError(String Message) {
        view.showAlert(Message);
    }

    @Override
    public void onSuccess(Object object) {
        if (object instanceof LoginResponse) {
            LoginResponse loginResponse = (LoginResponse) object;
            if (loginResponse != null && loginResponse.getStatusCode() == 200)
                view.showAlert(isArabic ? loginResponse.getStatusMessageAr() : loginResponse.getStatusMessageEn());
        }else if(object instanceof BaseResponse)
        {
            BaseResponse baseResponse = (BaseResponse) object;
            if (baseResponse != null && baseResponse.getStatusCode() == 200)
                view.showAlert(isArabic ? baseResponse.getStatusMessageAr() : baseResponse.getStatusMessageEn());
        }

    }
}
