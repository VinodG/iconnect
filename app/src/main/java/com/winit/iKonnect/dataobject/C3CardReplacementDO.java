package com.winit.iKonnect.dataobject;

/**
 * Created by Ojha.Sandeep on 10/31/2017.
 */

public class C3CardReplacementDO extends FormRequestDO {

    public String CardType = "";
    public String CapturedRetainedbyATM = "";
    public String CapturedCard_Immediate = "";
    public String CapturedCard_7days = "";
    public String CompanyName = "";
    public String BranchID = "";
    public String AuthorizedPerson = "";
    public String ContactNumber = "";
    public String CardMemberFullName = "";
    public String CardholderMobileNumber = "";
    public String EmployeeID = "";
    public String C3PayrollCardNumber = "";
    public String Signature = "";

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getCapturedRetainedbyATM() {
        return CapturedRetainedbyATM;
    }

    public void setCapturedRetainedbyATM(String capturedRetainedbyATM) {
        CapturedRetainedbyATM = capturedRetainedbyATM;
    }

    public String getCapturedCard_Immediate() {
        return CapturedCard_Immediate;
    }

    public void setCapturedCard_Immediate(String capturedCard_Immediate) {
        CapturedCard_Immediate = capturedCard_Immediate;
    }

    public String getCapturedCard_7days() {
        return CapturedCard_7days;
    }

    public void setCapturedCard_7days(String capturedCard_7days) {
        CapturedCard_7days = capturedCard_7days;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getBranchID() {
        return BranchID;
    }

    public void setBranchID(String branchID) {
        BranchID = branchID;
    }

    public String getAuthorizedPerson() {
        return AuthorizedPerson;
    }

    public void setAuthorizedPerson(String authorizedPerson) {
        AuthorizedPerson = authorizedPerson;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getCardMemberFullName() {
        return CardMemberFullName;
    }

    public void setCardMemberFullName(String cardMemberFullName) {
        CardMemberFullName = cardMemberFullName;
    }

    public String getCardholderMobileNumber() {
        return CardholderMobileNumber;
    }

    public void setCardholderMobileNumber(String cardholderMobileNumber) {
        CardholderMobileNumber = cardholderMobileNumber;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public String getC3PayrollCardNumber() {
        return C3PayrollCardNumber;
    }

    public void setC3PayrollCardNumber(String c3PayrollCardNumber) {
        C3PayrollCardNumber = c3PayrollCardNumber;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }
}
