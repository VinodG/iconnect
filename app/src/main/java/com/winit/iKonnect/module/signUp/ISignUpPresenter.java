package com.winit.iKonnect.module.signUp;

import com.winit.iKonnect.module.base.IBasePresenter;

/**
 * Created by Rahul.Yadav on 5/28/2017.
 */

public interface ISignUpPresenter extends IBasePresenter {
    void submit(String StaffNumber, String mobile, String workCountry);
    void submit(String StaffNumber);

}
