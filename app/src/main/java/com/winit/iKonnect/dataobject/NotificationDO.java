package com.winit.iKonnect.dataobject;

import com.winit.common.util.StringUtils;

import java.util.List;

/**
 * Created by Girish Velivela on 5/28/2017.
 */

public class NotificationDO extends BaseDO {

    /**
     * id : 1234
     * title :
     * titleAr :
     * msg :
     * msgAr :
     * imagePath : http://dev4.winitsoftware.com/e-Connect/Data/CmsPost/42871297.jpeg
     * hasRemarks : 0
     * hasAttachments : 0
     */

    private int id;
    private String title;
    private String titleAr;
    private String titleAR;
    private String msg;
    private String msgAr;
    private String msgAR;
    private String imagePath;
    private String Type;
    private String createdOn;
    private boolean hasRemarks;
    private boolean hasAttachments;
    private String remarks;
    private String remarksAr;
    private List<String> attachments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        if(isArabic)
            return getTitleAr();
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleAr() {
        if(!StringUtils.isEmpty(titleAr))
            return titleAr;
        if(!StringUtils.isEmpty(titleAR))
            return titleAR;
        return title;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getTitleAR() {
        return titleAR;
    }

    public void setTitleAR(String titleAR) {
        this.titleAR = titleAR;
    }

    public String getMsg() {
        if(isArabic)
            return getMsgAr();
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgAr() {
        if(!StringUtils.isEmpty(msgAr))
            return msgAr;
        if(!StringUtils.isEmpty(msgAR))
            return msgAR;
        return msg;
    }

    public void setMsgAr(String msgAr) {
        this.msgAr = msgAr;
    }

    public String getMsgAR() {
        return msgAR;
    }

    public void setMsgAR(String msgAR) {
        this.msgAR = msgAR;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isHasRemarks() {
        return hasRemarks;
    }

    public void setHasRemarks(boolean hasRemarks) {
        this.hasRemarks = hasRemarks;
    }

    public boolean isHasAttachments() {
        return hasAttachments;
    }

    public void setHasAttachments(boolean hasAttachments) {
        this.hasAttachments = hasAttachments;
    }

    public String getRemarks() {
        if(isArabic)
            return getRemarksAr();
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarksAr() {
        if(StringUtils.isEmpty(remarksAr))
            return remarks;
        return remarksAr;
    }

    public void setRemarksAr(String remarksAr) {
        this.remarksAr = remarksAr;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
}
