package com.winit.iKonnect.dataobject;

/**
 * Created by Ojha.Sandeep on 10/31/2017.
 */

public class CardCancellationDO extends FormRequestDO {

    public String ReasonForCancelation = "";
    public String CompanyDetails = "";
    public String CompanyName = "";
    public String BranchID = "";
    public String AuthorizedPersone = "";
    public String ContactNumber = "";
    public String CardMemberFullName = "";
    public String CardholderMobileNumber = "";
    public String C3PayrollCardNumber = "";
    public String DateofCardCancellation = "";
    public String DateofAdjustmentEntries = "";
    public String CardCancellationNameandSignature = "";
    public String AdjustmentNameandSignature = "";
    public String Signature = "";

    //newly added check witrh backend people about filed

    public String Employee_ID_No = "";

    public String getEmployee_ID_No() {
        return Employee_ID_No;
    }

    public void setEmployee_ID_No(String employee_ID_No) {
        Employee_ID_No = employee_ID_No;
    }

    public String getReasonForCancelation() {
        return ReasonForCancelation;
    }

    public void setReasonForCancelation(String reasonForCancelation) {
        ReasonForCancelation = reasonForCancelation;
    }

    public String getCompanyDetails() {
        return CompanyDetails;
    }

    public void setCompanyDetails(String companyDetails) {
        CompanyDetails = companyDetails;
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

    public String getAuthorizedPersone() {
        return AuthorizedPersone;
    }

    public void setAuthorizedPersone(String authorizedPersone) {
        AuthorizedPersone = authorizedPersone;
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

    public String getC3PayrollCardNumber() {
        return C3PayrollCardNumber;
    }

    public void setC3PayrollCardNumber(String c3PayrollCardNumber) {
        C3PayrollCardNumber = c3PayrollCardNumber;
    }

    public String getDateofCardCancellation() {
        return DateofCardCancellation;
    }

    public void setDateofCardCancellation(String dateofCardCancellation) {
        DateofCardCancellation = dateofCardCancellation;
    }

    public String getDateofAdjustmentEntries() {
        return DateofAdjustmentEntries;
    }

    public void setDateofAdjustmentEntries(String dateofAdjustmentEntries) {
        DateofAdjustmentEntries = dateofAdjustmentEntries;
    }

    public String getCardCancellationNameandSignature() {
        return CardCancellationNameandSignature;
    }

    public void setCardCancellationNameandSignature(String cardCancellationNameandSignature) {
        CardCancellationNameandSignature = cardCancellationNameandSignature;
    }

    public String getAdjustmentNameandSignature() {
        return AdjustmentNameandSignature;
    }

    public void setAdjustmentNameandSignature(String adjustmentNameandSignature) {
        AdjustmentNameandSignature = adjustmentNameandSignature;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }
}
