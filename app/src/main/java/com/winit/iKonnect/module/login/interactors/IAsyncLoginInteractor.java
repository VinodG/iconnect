package com.winit.iKonnect.module.login.interactors;

import com.winit.iKonnect.dataobject.LoginDo;
import com.winit.iKonnect.module.base.interactors.IBaseInteractor;

/**
 *  on 9/25/2016.
 */

public interface IAsyncLoginInteractor extends IBaseInteractor {
    void signIn(LoginDo loginDo);
    void signUP(String staffNumber);
    void register(String StaffNumber, String deviceType, String deviceId, String password);
    void postForgetPasswordData(String StaffNumber);
    void postData(String StaffNumber, String newPassword, String oldPassword);
    public void postHoveringData();

}
