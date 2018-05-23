package com.winit.iKonnect.module.passwordCreation;

/**
 * Created by Rahul.Yadav on 5/29/2017.
 */

public interface IPasswordPresenter  {
    void submit(String StaffNumber, String deviceType, String deviceId, String password,String ConfirmPassword);
    void register(String StaffNumber, String deviceType, String deviceId, String password,String ConfirmPassword);

}
