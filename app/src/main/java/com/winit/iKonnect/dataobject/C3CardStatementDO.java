package com.winit.iKonnect.dataobject;

/**
 * Created by Ojha.Sandeep on 10/31/2017.
 */

public class C3CardStatementDO extends FormRequestDO {

    private String CardsStatement = "";
    private String StartDate = "";
    private String EndDate = "";
    private String CompanyDetails = "";
    private String CompanyName = "";
    private String BranchID = "";
    private String AuthorizedPerson = "";
    private String ContactNumber = "";
    private String CardMemberFullName = "";
    private String CardholderMobileNumber = "";
    private String EmployeeID = "";
    private String C3PayrollCardNumber = "";
    private String Signature = "";


    public String getCardsStatement() {
        return CardsStatement;
    }

    public void setCardsStatement(String cardsStatement) {
        CardsStatement = cardsStatement;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
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
