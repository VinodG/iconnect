package com.winit.iKonnect.dataobject;

import java.io.Serializable;

/**
 * Created by Ashoka.Reddy on 5/26/2017.
 */

public class ContactUSDo implements Serializable {
    public String to = "";
    public String from = "";
    public String content = "";
    public String subject = "";
    //newly added
    public String Topic = "";
    public String SubTopic = "";
    public String Body = "";
    public String StaffNumber = "";


    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getSubTopic() {
        return SubTopic;
    }

    public void setSubTopic(String subTopic) {
        SubTopic = subTopic;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getStaffNumber() {
        return StaffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        StaffNumber = staffNumber;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTo() {

        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


}
