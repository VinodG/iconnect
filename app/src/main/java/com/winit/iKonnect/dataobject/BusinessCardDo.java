package com.winit.iKonnect.dataobject;

/**
 * Created by Ojha.Sandeep on 10/31/2017.
 */

public class BusinessCardDo extends FormRequestDO {

    public String Emirate = "";
    public String POBox = "";
    public String TelNo = "";
    public String Ext = "";
    public String Fax = "";
    public String Mobile_CUG = "";
    public String Email = "";
    private String City = "";
    private String Mobile = "";
    private String Website = "";


    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

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

    public String getExt() {
        return Ext;
    }

    public void setExt(String ext) {
        Ext = ext;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getMobile_CUG() {
        return Mobile_CUG;
    }

    public void setMobile_CUG(String mobile_CUG) {
        Mobile_CUG = mobile_CUG;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

}
