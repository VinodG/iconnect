package com.winit.iKonnect.dataobject;

/**
 * Created by Ashoka.Reddy on 5/26/2017.
 */

public class msgModel extends FormRequestDO {

    public String to ="";
    public String from ="";
    public String subject ="";

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public String content ="";
}
