package com.winit.iKonnect.dataobject;

import com.winit.common.util.StringUtils;

import java.util.List;

/**
 * Created by Girish Velivela on 5/28/2017.
 */

public class NotificationDetailsDO extends BaseDO {

    /**
     * id : 1234
     * remarks :
     * remarksAr :
     * attachments : ["path1","path2","path3"]
     */

    private int id;
    private String remarks;
    private String remarksAr;
    private List<String> attachments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
