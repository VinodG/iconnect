package com.winit.iKonnect.dataobject;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class EmploymentLetterDO extends FormRequestDO{

    private String TypeofIdProof;
    private String PassportNo ="";
    private String ChamberofCommerceStatus="";
    private String IsSalaryRequiredinLetter= "";
    private String TypeofRequest = "";

    public String getTypeofIdProof() {
        return TypeofIdProof;
    }

    public void setTypeofIdProof(String typeofIdProof) {
        TypeofIdProof = typeofIdProof;
    }

    public String getIsSalaryRequiredinLetter() {
        return IsSalaryRequiredinLetter;
    }

    public void setIsSalaryRequiredinLetter(String isSalaryRequiredinLetter) {
        IsSalaryRequiredinLetter = isSalaryRequiredinLetter;
    }

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

    public String getTypeofRequest() {
        return TypeofRequest;
    }

    public void setTypeofRequest(String typeofRequest) {
        this.TypeofRequest = typeofRequest;
    }
}
