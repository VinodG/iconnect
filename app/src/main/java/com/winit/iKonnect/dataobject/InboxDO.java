package com.winit.iKonnect.dataobject;

/**
 * Created by Gufran.Khan on 6/29/2017.
 */

public class InboxDO extends BaseDO {
    /**
     * id : 1
     * title : GirishTitle
     * titleAR : null
     * msg : Gr Massage
     * msgAR : null
     * hasRemarks : true
     * hasAttachments : false
     * imagePath : 636338285584776935_screenshot_09262014_131157.png
     * remarks : Gir reeema
     * remarksAr : null
     * attachments : null
     */

    private int id;
    private String title;
    private Object titleAR;
    private String msg;
    private String msgAR;
    private boolean hasRemarks;
    private boolean hasAttachments;
    private String imagePath;
    private String remarks;
    private String remarksAr;
    private String attachments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getTitleAR() {
        return titleAR;
    }

    public void setTitleAR(Object titleAR) {
        this.titleAR = titleAR;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getMsgAR() {
        return msgAR;
    }

    public void setMsgAR(String msgAR) {
        this.msgAR = msgAR;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Object getRemarksAr() {
        return remarksAr;
    }

    public void setRemarksAr(String remarksAr) {
        this.remarksAr = remarksAr;
    }

    public Object getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }
}
