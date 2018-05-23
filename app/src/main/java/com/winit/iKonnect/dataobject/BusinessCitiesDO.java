package com.winit.iKonnect.dataobject;

/**
 * Created by Ojha.Sandeep on 11/24/2017.
 */

public class BusinessCitiesDO extends FormRequestDO {

    public String getEmirate() {
        return Emirate;
    }

    public void setEmirate(String emirate) {
        Emirate = emirate;
    }

    public String getPOBox() {
        return POBox;
    }

    public void setPOBox(String POBox) {
        this.POBox = POBox;
    }

    public String getTelNo() {
        return TelNo;
    }

    public void setTelNo(String telNo) {
        TelNo = telNo;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    private String Emirate = "";
    private String POBox = "";
    private String TelNo = "";
    private String Fax = "";






}
