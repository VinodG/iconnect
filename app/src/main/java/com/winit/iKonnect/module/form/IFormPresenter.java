package com.winit.iKonnect.module.form;


import com.winit.iKonnect.dataobject.BillDetailDo;
import com.winit.iKonnect.dataobject.FamilyResidenceVisitVisa_FamilyDetails;
import com.winit.iKonnect.dataobject.InsuranceCardFamilyDetailsModel;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.module.base.IBasePresenter;

import java.util.ArrayList;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public interface IFormPresenter extends IBasePresenter {
    void setAttachment(String path);

    void removeAttachment(String path);

    //new requirement added by sandeep

    void submitBankAccountUpdateSalaryTransferRequest(String reason, String bankName, String accountNumber, String Iban, String signature);

    void submitAtmSwitchClaim(String reason, String cardNumber, String cardMemberMobileNumber, String transactionDate,
                              String transactionTime, String amountWithDraw, String amountRecived, String amountDisputed,
                              String bankName, String atmLocation, String additionalComment, String signature);

    void submitCardReplacementService(
            String reason, String CardType, String cardRetainment,
            String CompanyName, String BranchID, String AuthorizedPerson, String ContactNumber, String CardMemberFullName,
            String CardholderMobileNumber, String EmployeeID, String C3PayrollCardNumber, String Signature
    );


    void submitCardStatementService(
            String reason, String cardStatementType, String startDate, String endDate,
            String CompanyName, String BranchID, String AuthorizedPerson, String ContactNumber, String CardMemberFullName,
            String CardholderMobileNumber, String EmployeeID, String C3PayrollCardNumber, String Signature
    );


    void submitCardCancellation(
            String reason,
            String CompanyName, String CompanyDetails, String BranchID, String AuthorizedPerson, String ContactNumber,
            String CardMemberFullName,
            String CardholderMobileNumber, String employeeId, String C3PayrollCardNumber, String others

    );


    void submitBussinessCard(
            String reason,
            String Emirate,
            String POBox,
            String TelNo,
            String Ext,
            String Fax,
            String Mobile_CUG,
            String Email,
            String WebSite

    );


    void submitBussinessTravelRequest(
            String reason,
            String Destinations,
            String PurposeofTravel,
            String StartDate,
            String EndDate,
            String ContactNumberduringBusinessTravel,
            String TicketRequired,
            String Sector,
            String VisaRequired,
            String HotelAccommodation,
            String CostDebitto,
            String AnyOtherRequirements);

    void submitHouseAllowance(String name, String s, String location, String designation, String department,
                              String advanceRequest, String staffNo);

    void submitLeaveJoining(String reason, String startDate, String document);

    void submitLeaveApplicationForm(String reason, String typeOfLeave, String startDate, String endDate, String salaryAdvance, String contact_number, String doYouWant_leave_salry_advance,
                                    int MaxPos,String reasonForEmergency);

    void SalaryCertificateRequest(String address, String purpose, String otherInstructions);

    void SalaryTransferLetterRequest(String currentBankName, String currentIBANno, String salaryTransferToSameBank, String bankName
            , String IBANno, String haveExistingLoans, String loanBankName, String totalLoanTaken, String tvLoanAppliedOn, String outStanding_asOfday
            , String lastInstal_due, String monthlyInstallment, String purposeOfLoan, String bankDetailsName, String totalLoanAmt
            , String monthlyInstal_50, String haveCreditCard, String ccBankName, String ccOutStandingAmt, String confirm, String approval, String request,String accountNumber);


    void PassportRelease(String purposeOfRelease, String startDate, String endDate, String etOther);

    void SystemAccess(String StaffNo, String Department, String Designation, String sap, String network, String email, String internet, String extension, String remarks);

    void TransportLoan(String isUAEDri_license, String staffNo, String name, String department, String designation, String brandNeworPre
            , String VehicleMake, String VehicleModel, String OdometerRead, String ChassisNo, String NewVehicleMake
            , String NewVehicleModel, String NewChassisNo, String amount,String etEngineNo,String etNewEngineNo,int MaxPos);


    void CommitmentForm(String contributionAmt, String remarks);

    void VisaSpouseChildren(String name, String FatherName, String MotherName, String Nationality, String DateOfBirth, String PlaceOfBirth
            , String PassportNo, String PlaceOfIssue, String DateOfIssue, String DateOfExpiry, String Profession, String DateOfLastEntry
            , String AddrAbroad, String R_ShipWithApplicant, String anyBankLoan, String anyHRAdvance, String anyTransportAdvance
            , String creditCards);

    void VisaInLawsChildren(String name, String FatherName, String MotherName, String Nationality, String DateOfBirth, String PlaceOfBirth
            , String PassportNo, String PlaceOfIssue, String DateOfIssue, String DateOfExpiry, String Profession, String DateOfLastEntry
            , String AddrAbroad, String R_ShipWithApplicant, String Reasonrequest, String anyBankLoan, String anyHRAdvance, String anyTransportAdvance
            , String creditCards);

    void FamilyVisaSpouseChildren(String name, String FatherName, String MotherName, String Nationality, String DateOfBirth, String PlaceOfBirth
            , String PassportNo, String PlaceOfIssue, String DateOfIssue, String DateOfExpiry, String Profession, String DateOfLastEntry
            , String AddrAbroad, String R_ShipWithApplicant, String anyBankLoan, String anyHRAdvance, String anyTransportAdvance
            , String creditCards);

    void FamilyVisaParents(String name, String FatherName, String MotherName, String Nationality, String DateOfBirth, String PlaceOfBirth
            , String PassportNo, String PlaceOfIssue, String DateOfIssue, String DateOfExpiry, String Profession, String DateOfLastEntry
            , String AddrAbroad, String R_ShipWithApplicant, String Reasonrequest, String anyBankLoan, String anyHRAdvance, String anyTransportAdvance
            , String creditCards);

      ArrayList<String>getAttachments();
}
