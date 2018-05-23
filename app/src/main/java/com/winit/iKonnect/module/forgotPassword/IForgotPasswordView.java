package com.winit.iKonnect.module.forgotPassword;

import com.winit.iKonnect.dataobject.response.ForgetotpDo;
import com.winit.iKonnect.module.base.IBaseView;

/**
 * Created by Rahul.Yadav on 6/6/2017.
 */

public interface IForgotPasswordView extends IBaseView {
    public String STAFF_ID = "STAFF_ID";
    void showAlert(String type, ForgetotpDo loginResponse);
}
