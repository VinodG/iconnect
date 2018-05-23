package com.winit.iKonnect.dataobject;

import java.util.ArrayList;

/**
 * Created by Rohitmanohar on 12-07-2017.
 */

public class ChatMemberDO extends BaseDO
{
    public String id = "";
    public String staffNumber = "";
    public String staffName = "";
    public String photoUrl = "";
    public String IsActive = "";
    public String ReciverName="";
    public int Unreadmsg;

    public int getUnreadmsg() {
        return Unreadmsg;
    }

    public void setUnreadmsg(int unreadmsg) {
        Unreadmsg = unreadmsg;
    }

    public ArrayList<String> FCMDeviceToken;

    public ArrayList<String> getFCMDeviceToken() {
        return FCMDeviceToken;
    }

    public void setFCMDeviceToken(ArrayList<String> FCMDeviceToken) {
        this.FCMDeviceToken = FCMDeviceToken;
    }
    public String getReciverName() {
        return ReciverName;
    }

    public void setReciverName(String reciverName) {
        ReciverName = reciverName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }
}
