package com.winit.iKonnect.module.otpValidation;

import com.winit.iKonnect.module.base.IBasePresenter;

/**
 * Created by Rahul.Yadav on 5/29/2017.
 */

public interface IOtpValidationPresenter extends IBasePresenter
{
    void submit(String StaffNumber, String mobile, String otp);
    void reSendOtp(String StaffNumber, String mobile, String workCountry);

}
