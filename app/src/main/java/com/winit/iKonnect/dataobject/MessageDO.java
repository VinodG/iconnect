package com.winit.iKonnect.dataobject;

/**
 * Created by home on 9/29/2017.
 */

public class MessageDO extends BaseDO
{
    private String Message;
    private Long Time;
    private String UserCode;

    public Long getTime() {
        return Time;
    }

    public String getMessage() {
        return Message;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setTime(Long time) {
        Time = time;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }
}
