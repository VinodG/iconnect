package com.winit.iKonnect.module.changePassword;

import com.winit.iKonnect.module.base.IBasePresenter;

/**
 * Created by Rahul.Yadav on 6/7/2017.
 */

public interface IChangePasswordPresenter extends IBasePresenter{
    void submit(String StaffNumber, String newPassword, String oldPassword,String confirmPassword);

}
