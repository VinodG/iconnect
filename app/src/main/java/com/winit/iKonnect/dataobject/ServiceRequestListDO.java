package com.winit.iKonnect.dataobject;

import com.winit.iKonnect.dataobject.response.BaseResponse;

import java.util.ArrayList;

/**
 * Created by Ojha.Sandeep on 11/28/2017.
 */

public class ServiceRequestListDO {

    private ServiceRequestDO servicerequest;
    private LeaveApplicationDO AnnualLeave;
    private SalaryTransferRequestBankAccountDo SalaryTransferRequest;
    private C3CardATMSwitchClaimDO C3CardATMSwitchClaim;
    private C3CardReplacementDO C3CardReplacement;
    private C3CardStatementDO C3CardStatement;
    private CardCancellationDO C3CardCancelation;
    private BusinessCardDo BusinessCard;
    private BusinessTravelDo BusinessTravel;
    private CommitmentFormDO CommitmentForm;
    private HousingAllowanceDO HousingAllowance;
    private LeaveJoiningDO LeaveJoining;
    private PassportReleaseDO PassportRelease;
    private SalaryCertificateRequestDO SalaryCertificateRequest;
    private SalaryTransferLetterRequestDO SalaryTransferLetterRequest;
    public SystemAccessDO systemaccess;
    private TransportLoanDO TransportLoan;
    private VisaSpouseChildrenDO VisaSpouseChildren;
    private VisaSpouseChildrenDO VisaInLawsChildren;
    private VisaSpouseChildrenDO FamilyVisaSpouseChildren;
    private VisaSpouseChildrenDO FamilyVisaParent;
    private ArrayList<AttachmentsDO> Attachments;

    public ArrayList<AttachmentsDO> getAttachments() {
        return Attachments;
    }

    public void setAttachments(ArrayList<AttachmentsDO> attachments) {
        Attachments = attachments;
    }

    public ServiceRequestDO getServicerequest() {
        return servicerequest;
    }

    public void setServicerequest(ServiceRequestDO servicerequest) {
        this.servicerequest = servicerequest;
    }

    public LeaveApplicationDO getAnnualLeave() {
        return AnnualLeave;
    }

    public void setAnnualLeave(LeaveApplicationDO annualLeave) {
        AnnualLeave = annualLeave;
    }

    public SalaryTransferRequestBankAccountDo getSalaryTransferRequest() {
        return SalaryTransferRequest;
    }

    public void setSalaryTransferRequest(SalaryTransferRequestBankAccountDo salaryTransferRequest) {
        SalaryTransferRequest = salaryTransferRequest;
    }

    public C3CardATMSwitchClaimDO getC3CardATMSwitchClaim() {
        return C3CardATMSwitchClaim;
    }

    public void setC3CardATMSwitchClaim(C3CardATMSwitchClaimDO c3CardATMSwitchClaim) {
        C3CardATMSwitchClaim = c3CardATMSwitchClaim;
    }

    public C3CardReplacementDO getC3CardReplacement() {
        return C3CardReplacement;
    }

    public void setC3CardReplacement(C3CardReplacementDO c3CardReplacement) {
        C3CardReplacement = c3CardReplacement;
    }

    public C3CardStatementDO getC3CardStatement() {
        return C3CardStatement;
    }

    public void setC3CardStatement(C3CardStatementDO c3CardStatement) {
        C3CardStatement = c3CardStatement;
    }

    public CardCancellationDO getC3CardCancelation() {
        return C3CardCancelation;
    }

    public void setC3CardCancelation(CardCancellationDO c3CardCancelation) {
        C3CardCancelation = c3CardCancelation;
    }

    public BusinessCardDo getBusinessCard() {
        return BusinessCard;
    }

    public void setBusinessCard(BusinessCardDo businessCard) {
        BusinessCard = businessCard;
    }

    public BusinessTravelDo getBusinessTravel() {
        return BusinessTravel;
    }

    public void setBusinessTravel(BusinessTravelDo businessTravel) {
        BusinessTravel = businessTravel;
    }

    public CommitmentFormDO getCommitmentForm() {
        return CommitmentForm;
    }

    public void setCommitmentForm(CommitmentFormDO commitmentForm) {
        CommitmentForm = commitmentForm;
    }

    public HousingAllowanceDO getHousingAllowance() {
        return HousingAllowance;
    }

    public void setHousingAllowance(HousingAllowanceDO housingAllowance) {
        HousingAllowance = housingAllowance;
    }

    public LeaveJoiningDO getLeaveJoining() {
        return LeaveJoining;
    }

    public void setLeaveJoining(LeaveJoiningDO leaveJoining) {
        LeaveJoining = leaveJoining;
    }

    public PassportReleaseDO getPassportRelease() {
        return PassportRelease;
    }

    public void setPassportRelease(PassportReleaseDO passportRelease) {
        PassportRelease = passportRelease;
    }

    public SalaryCertificateRequestDO getSalaryCertificateRequest() {
        return SalaryCertificateRequest;
    }

    public void setSalaryCertificateRequest(SalaryCertificateRequestDO salaryCertificateRequest) {
        SalaryCertificateRequest = salaryCertificateRequest;
    }

    public SalaryTransferLetterRequestDO getSalaryTransferLetterRequest() {
        return SalaryTransferLetterRequest;
    }

    public void setSalaryTransferLetterRequest(SalaryTransferLetterRequestDO salaryTransferLetterRequest) {
        SalaryTransferLetterRequest = salaryTransferLetterRequest;
    }

    public SystemAccessDO getSystemaccess() {
        return systemaccess;
    }

    public void setSystemaccess(SystemAccessDO systemaccess) {
        this.systemaccess = systemaccess;
    }

    public TransportLoanDO getTransportLoan() {
        return TransportLoan;
    }

    public void setTransportLoan(TransportLoanDO transportLoan) {
        TransportLoan = transportLoan;
    }

    public VisaSpouseChildrenDO getVisaSpouseChildren() {
        return VisaSpouseChildren;
    }

    public void setVisaSpouseChildren(VisaSpouseChildrenDO visaSpouseChildren) {
        VisaSpouseChildren = visaSpouseChildren;
    }

    public VisaSpouseChildrenDO getVisaInLawsChildren() {
        return VisaInLawsChildren;
    }

    public void setVisaInLawsChildren(VisaSpouseChildrenDO visaInLawsChildren) {
        VisaInLawsChildren = visaInLawsChildren;
    }

    public VisaSpouseChildrenDO getFamilyVisaSpouseChildren() {
        return FamilyVisaSpouseChildren;
    }

    public void setFamilyVisaSpouseChildren(VisaSpouseChildrenDO familyVisaSpouseChildren) {
        FamilyVisaSpouseChildren = familyVisaSpouseChildren;
    }

    public VisaSpouseChildrenDO getFamilyVisaParent() {
        return FamilyVisaParent;
    }

    public void setFamilyVisaParent(VisaSpouseChildrenDO familyVisaParent) {
        FamilyVisaParent = familyVisaParent;
    }


}
