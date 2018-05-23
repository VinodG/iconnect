package com.winit.iKonnect.dataobject;

/**
 * Created by Ojha.Sandeep on 11/1/2017.
 */

public class LeaveJoiningDO extends FormRequestDO {
    public String ResumedWork = "";
    public String Reasoniflatewithsupportingdocuments = "";
    public String SupportingDocuments = "";
    public String DisplayLeaveEndingDate = "";
    public String Approved_NotApproved = "";
    public String HRtoconfirmthereceiptofpassport = "";


    public String getResumedWork() {
        return ResumedWork;
    }

    public void setResumedWork(String resumedWork) {
        ResumedWork = resumedWork;
    }

    public String getReasoniflatewithsupportingdocuments() {
        return Reasoniflatewithsupportingdocuments;
    }

    public void setReasoniflatewithsupportingdocuments(String reasoniflatewithsupportingdocuments) {
        Reasoniflatewithsupportingdocuments = reasoniflatewithsupportingdocuments;
    }

    public String getSupportingDocuments() {
        return SupportingDocuments;
    }

    public void setSupportingDocuments(String supportingDocuments) {
        SupportingDocuments = supportingDocuments;
    }

    public String getDisplayLeaveEndingDate() {
        return DisplayLeaveEndingDate;
    }

    public void setDisplayLeaveEndingDate(String displayLeaveEndingDate) {
        DisplayLeaveEndingDate = displayLeaveEndingDate;
    }

    public String getApproved_NotApproved() {
        return Approved_NotApproved;
    }

    public void setApproved_NotApproved(String approved_NotApproved) {
        Approved_NotApproved = approved_NotApproved;
    }

    public String getHRtoconfirmthereceiptofpassport() {
        return HRtoconfirmthereceiptofpassport;
    }

    public void setHRtoconfirmthereceiptofpassport(String HRtoconfirmthereceiptofpassport) {
        this.HRtoconfirmthereceiptofpassport = HRtoconfirmthereceiptofpassport;
    }
}
