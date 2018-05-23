package com.winit.iKonnect.dataobject;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class VisaLetterDO extends FormRequestDO{
    private String PassportNo ="";
    private String ChamberofCommerceStatus ="";

    public String getChamberofCommerceStatus() {
        return ChamberofCommerceStatus;
    }

    public void setChamberofCommerceStatus(String chamberofCommerceStatus) {
        ChamberofCommerceStatus = chamberofCommerceStatus;
    }

    public String getPassportNo() {

        return PassportNo;
    }

    public void setPassportNo(String passportNo) {
        PassportNo = passportNo;
    }
}
