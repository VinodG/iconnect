package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.BaseDO;

/**
 * Created by Rahul.Yadav on 5/27/2017.
 */

public class EmpModelDO extends BaseDO {
    public int id;
    public String StaffNumber = "";
    public String CompanyCode = "";
    public String PersonalArea = "";
    public String PersonalSubArea = "";
    public String KnownAs = "";
    public String Position = "";
    public String OrgUnit = "";
    public String FunctionalArea = "";
    public String ZZSector = "";
    public String IDNumberPassportSlef = "";
    public String IDNumberPassportSlef_ExpireDate = "";
    public String IDNumberEmiratesSelf = "";
    public String IDNumberEmiratesSelf_ExpireDate = "";
    public String IDNumberResidenceVisa = "";
    public String IDNumberResidenceVisa_ExpireDate = "";
    public String ReportingManagerStaffNumber = "";
    public String ReportingManagerName = "";
    public int LeaveBalance;
    public String Band = "";
    public boolean BankLoan = false;
    public String Confirmation = "";
    public String EligibilityForHRA = "";
    public String TransportAllowance = "";
    public boolean CompanyAccomodation = false;
    public float BasicSalary;
    public String LastName = "";
    public String FirstName = "";
    public String CostCtr = "";
    public String HireDate = "";
    public String IndemDate = "";
    public String BirthDate = "";
    public String Gender = "";
    public String Nationality = "";


    public boolean Isactive = true;
    public String Status = "";
    public String mobile = "";
    public String email = "";
    public String createdBy = "";
    public String modifiedBy = "";
    public String modifiedOn = "";
    public String createdOn = "";
    public String dpmStaffNumber = "";
    public String JoiningDate = "";
    public String DeviceType = "";
    public String DeviceId = "";
    private String workCountry = "";
    private String photoUrl = "";
    private Double OutstandingHRA;
    private String HousingCutOff = "";
    private String Sponsor = "";
    private String BankName = "";
    private String IBAN = "";
    private String AccountNumber  = "";

    public String getSponsor() {
        return Sponsor;
    }

    public void setSponsor(String sponsor) {
        Sponsor = sponsor;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getHousingCutOff() {
        return HousingCutOff;
    }

    public void setHousingCutOff(String housingCutOff) {
        HousingCutOff = housingCutOff;
    }


    public boolean isIsactive() {
        return Isactive;
    }

    public Double getOutstandingHRA() {
        return OutstandingHRA;
    }

    public void setOutstandingHRA(Double outstandingHRA) {
        OutstandingHRA = outstandingHRA;
    }


    public float getBasicSalary() {
        return BasicSalary;
    }

    public void setBasicSalary(float basicSalary) {
        BasicSalary = basicSalary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStaffNumber() {
        return StaffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        StaffNumber = staffNumber;
    }

    public String getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(String companyCode) {
        CompanyCode = companyCode;
    }

    public String getPersonalArea() {
        return PersonalArea;
    }

    public void setPersonalArea(String personalArea) {
        PersonalArea = personalArea;
    }

    public String getPersonalSubArea() {
        return PersonalSubArea;
    }

    public void setPersonalSubArea(String personalSubArea) {
        PersonalSubArea = personalSubArea;
    }

    public String getKnownAs() {
        return KnownAs;
    }

    public void setKnownAs(String knownAs) {
        KnownAs = knownAs;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getOrgUnit() {
        return OrgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        OrgUnit = orgUnit;
    }

    public String getFunctionalArea() {
        return FunctionalArea;
    }

    public void setFunctionalArea(String functionalArea) {
        FunctionalArea = functionalArea;
    }

    public String getZZSector() {
        return ZZSector;
    }

    public void setZZSector(String ZZSector) {
        this.ZZSector = ZZSector;
    }

    public String getIDNumberPassportSlef() {
        return IDNumberPassportSlef;
    }

    public void setIDNumberPassportSlef(String IDNumberPassportSlef) {
        this.IDNumberPassportSlef = IDNumberPassportSlef;
    }

    public String getIDNumberPassportSlef_ExpireDate() {
        return IDNumberPassportSlef_ExpireDate;
    }

    public void setIDNumberPassportSlef_ExpireDate(String IDNumberPassportSlef_ExpireDate) {
        this.IDNumberPassportSlef_ExpireDate = IDNumberPassportSlef_ExpireDate;
    }

    public String getIDNumberEmiratesSelf() {
        return IDNumberEmiratesSelf;
    }

    public void setIDNumberEmiratesSelf(String IDNumberEmiratesSelf) {
        this.IDNumberEmiratesSelf = IDNumberEmiratesSelf;
    }

    public String getIDNumberEmiratesSelf_ExpireDate() {
        return IDNumberEmiratesSelf_ExpireDate;
    }

    public void setIDNumberEmiratesSelf_ExpireDate(String IDNumberEmiratesSelf_ExpireDate) {
        this.IDNumberEmiratesSelf_ExpireDate = IDNumberEmiratesSelf_ExpireDate;
    }

    public String getIDNumberResidenceVisa() {
        return IDNumberResidenceVisa;
    }

    public void setIDNumberResidenceVisa(String IDNumberResidenceVisa) {
        this.IDNumberResidenceVisa = IDNumberResidenceVisa;
    }

    public String getIDNumberResidenceVisa_ExpireDate() {
        return IDNumberResidenceVisa_ExpireDate;
    }

    public void setIDNumberResidenceVisa_ExpireDate(String IDNumberResidenceVisa_ExpireDate) {
        this.IDNumberResidenceVisa_ExpireDate = IDNumberResidenceVisa_ExpireDate;
    }

    public String getReportingManagerStaffNumber() {
        return ReportingManagerStaffNumber;
    }

    public void setReportingManagerStaffNumber(String reportingManagerStaffNumber) {
        ReportingManagerStaffNumber = reportingManagerStaffNumber;
    }

    public String getReportingManagerName() {
        return ReportingManagerName;
    }

    public void setReportingManagerName(String reportingManagerName) {
        ReportingManagerName = reportingManagerName;
    }

    public int getLeaveBalance() {
        return LeaveBalance;
    }

    public void setLeaveBalance(int leaveBalance) {
        LeaveBalance = leaveBalance;
    }

    public String getBand() {
        return Band;
    }

    public void setBand(String band) {
        Band = band;
    }

    public boolean isBankLoan() {
        return BankLoan;
    }

    public void setBankLoan(boolean bankLoan) {
        BankLoan = bankLoan;
    }

    public String getConfirmation() {
        return Confirmation;
    }

    public void setConfirmation(String confirmation) {
        Confirmation = confirmation;
    }

    public String getEligibilityForHRA() {
        return EligibilityForHRA;
    }

    public void setEligibilityForHRA(String eligibilityForHRA) {
        EligibilityForHRA = eligibilityForHRA;
    }

    public String getTransportAllowance() {
        return TransportAllowance;
    }

    public void setTransportAllowance(String transportAllowance) {
        TransportAllowance = transportAllowance;
    }

    public boolean isCompanyAccomodation() {
        return CompanyAccomodation;
    }

    public void setCompanyAccomodation(boolean companyAccomodation) {
        CompanyAccomodation = companyAccomodation;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getCostCtr() {
        return CostCtr;
    }

    public void setCostCtr(String costCtr) {
        CostCtr = costCtr;
    }

    public String getHireDate() {
        return HireDate;
    }

    public void setHireDate(String hireDate) {
        HireDate = hireDate;
    }

    public String getIndemDate() {
        return IndemDate;
    }

    public void setIndemDate(String indemDate) {
        IndemDate = indemDate;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public boolean isactive() {
        return Isactive;
    }

    public void setIsactive(boolean isactive) {
        Isactive = isactive;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getDpmStaffNumber() {
        return dpmStaffNumber;
    }

    public void setDpmStaffNumber(String dpmStaffNumber) {
        this.dpmStaffNumber = dpmStaffNumber;
    }

    public String getJoiningDate() {
        return JoiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        JoiningDate = joiningDate;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getWorkCountry() {
        return workCountry;
    }

    public void setWorkCountry(String workCountry) {
        this.workCountry = workCountry;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


}
