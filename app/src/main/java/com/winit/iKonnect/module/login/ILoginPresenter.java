package com.winit.iKonnect.module.login;


/**
 *  on 9/25/2016.
 */

public interface ILoginPresenter {
    void submit(String StaffNumber, String deviceType, String deviceId, String password, String FCMId);
    void postHovering();
}
