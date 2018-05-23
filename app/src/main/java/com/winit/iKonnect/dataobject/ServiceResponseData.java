package com.winit.iKonnect.dataobject;

/**
 * Created by Ashoka.Reddy on 7/12/2017.
 */

public class ServiceResponseData extends BaseDO {
    private String id;
    private String formId;
    private String formName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    private String SerialNo;
}
