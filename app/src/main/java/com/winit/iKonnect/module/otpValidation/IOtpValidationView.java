package com.winit.iKonnect.module.otpValidation;

import com.winit.iKonnect.module.base.IBaseView;

/**
 * Created by Rahul.Yadav on 5/29/2017.
 */

public interface IOtpValidationView extends IBaseView{
    public String INVALID_OTP ="INVALID_OTP";
    void showToast(String msg);

}
