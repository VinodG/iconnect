package com.winit.iKonnect.dataobject;

/**
 * Created by namashivaya.gangishe on 5/26/2017.
 */

public class LoginDo extends BaseDO {
    private String StaffNumber = "";
    private String deviceType = "";
    private String deviceId = "";

    public String getFCMID() {
        return FCMID;
    }

    public void setFCMID(String FCMID) {
        this.FCMID = FCMID;
    }

    private String FCMID = "";
    private String password = "";

    public String getStaffNumber() {
        return StaffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        StaffNumber = staffNumber;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
