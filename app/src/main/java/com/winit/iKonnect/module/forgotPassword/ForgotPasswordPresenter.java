package com.winit.iKonnect.module.forgotPassword;

import com.winit.common.Preference;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.dataobject.response.ForgetotpDo;
import com.winit.iKonnect.dataobject.response.LoginResponse;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.login.interactors.AsyncLoginInteractor;
import com.winit.iKonnect.module.login.interactors.IAsyncLoginInteractor;
import com.winit.iKonnect.ui.activities.ForgotPassword;


import static com.winit.iKonnect.module.forgotPassword.IForgotPasswordView.STAFF_ID;

/**
 * Created by Rahul.Yadav on 6/6/2017.
 */

public class ForgotPasswordPresenter extends BasePresenter implements IForgotPasswordPresenter ,AsyncLoginInteractor.OnLoginFinishedListener {
    private IAsyncLoginInteractor iAsyncLoginInteractor;
    private IForgotPasswordView view;
    private String StaffNumner = "";

    public ForgotPasswordPresenter(ForgotPassword view){
        super(view);
        this.view = view;
        iAsyncLoginInteractor = new AsyncLoginInteractor(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void submitForgetPassword(String StaffNumber) {
        if(StringUtils.isEmpty(StaffNumber)){
            view.showAlert(STAFF_ID);
        }
        else {
            StaffNumner = StaffNumber;
            iAsyncLoginInteractor.postForgetPasswordData(StaffNumber);
        }
    }

    @Override
    public void onError(String Message) {
        view.showAlert(Message);
    }

    @Override
    public void onSuccess(Object object) {
        if(object instanceof LoginResponse)
        {
            LoginResponse loginResponse = (LoginResponse) object;
            if(loginResponse!=null&&loginResponse.getStatusCode()==200)
            {
                preference.saveStringInPreference(Preference.STAFF_NUMBER,StaffNumner);
                preference.saveBooleanInPreference(Preference.IS_FROM_FORGOT_PASSWORD,true);
                view.showAlert(isArabic? loginResponse.getStatusMessageAr() :loginResponse.getStatusMessageEn());

            }

        }
        else if(object instanceof ForgetotpDo)
        {
            ForgetotpDo loginResponse = (ForgetotpDo) object;
            if(loginResponse!=null&&loginResponse.getStatusCode().equalsIgnoreCase("200"))
            {
                preference.saveStringInPreference(Preference.STAFF_NUMBER,StaffNumner);
                preference.saveStringInPreference(Preference.STAFF_MOBILE,loginResponse.getMobile());
                preference.saveBooleanInPreference(Preference.IS_FROM_FORGOT_PASSWORD,true);
                preference.saveStringInPreference(Preference.STAFF_WORK_COUNTRY,loginResponse.getWorkCountry());
                view.showAlert(isArabic? loginResponse.getStatusMessageAr() :loginResponse.getStatusMessageEn(), loginResponse);
            }

        }

    }
}
