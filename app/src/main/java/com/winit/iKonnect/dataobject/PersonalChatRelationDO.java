package com.winit.iKonnect.dataobject;

/**
 * Created by Rohitmanohar on 13-07-2017.
 */

public class PersonalChatRelationDO extends BaseDO
{
    public String reciverId = "";

    public String getReciverId() {
        return reciverId;
    }

    public void setReciverId(String reciverId) {
        this.reciverId = reciverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReciverName() {
        return reciverName;
    }

    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String senderId = "";
    public String reciverName = "";
    public String senderName = "";
}
