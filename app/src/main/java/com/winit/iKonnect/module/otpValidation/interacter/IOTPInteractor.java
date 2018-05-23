package com.winit.iKonnect.module.otpValidation.interacter;

import com.winit.iKonnect.module.base.interactors.IBaseInteractor;

/**
 * Created by Rahul.Yadav on 5/28/2017.
 */

public interface IOTPInteractor extends IBaseInteractor{
    void postData(String StaffNumber, String mobile, String workCountry);

}
