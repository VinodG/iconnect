package com.winit.iKonnect.dataobject;

/**
 * Created by Gufran.Khan on 6/27/2017.
 */

public class ChatMessage extends BaseDO {

    private String userId;
    private String text;
    private String senderName;

    private String userName;
    private String photoUrl;
    private String message;
    private String senderId;
    private long chatTime;

    public long getChatTime() {
        return chatTime;
    }

    public void setChatTime(long chatTime) {
        this.chatTime = chatTime;
    }

    public String ReciverName="";

    public String getReciverName() {
        return ReciverName;
    }

    public void setReciverName(String reciverName) {
        ReciverName = reciverName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }


}
