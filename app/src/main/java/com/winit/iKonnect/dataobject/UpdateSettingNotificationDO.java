package com.winit.iKonnect.dataobject;

/**
 * Created by Ashoka.Reddy on 23-Sep-17.
 */

public class UpdateSettingNotificationDO
{
    private String staffNumber="";
    private String deviceType="";
    private String deviceId="";
    private String isNotificationEnabled="";
    private String preferedLanguage="";
    private String FCMDeviceToken="";

    public String getPreferedLanguage() {
        return preferedLanguage;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getFCMDeviceToken() {
        return FCMDeviceToken;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getIsNotificationEnabled() {
        return isNotificationEnabled;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setFCMDeviceToken(String FCMDeviceToken) {
        this.FCMDeviceToken = FCMDeviceToken;
    }

    public void setIsNotificationEnabled(String isNotificationEnabled) {
        this.isNotificationEnabled = isNotificationEnabled;
    }

    public void setPreferedLanguage(String preferedLanguage) {
        this.preferedLanguage = preferedLanguage;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

}
