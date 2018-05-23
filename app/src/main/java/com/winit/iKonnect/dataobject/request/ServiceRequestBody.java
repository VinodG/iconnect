package com.winit.iKonnect.dataobject.request;

import com.winit.iKonnect.dataobject.AdvanceHousingAllowanceDO;
import com.winit.iKonnect.dataobject.AuthorizationtoDriveCompanyVehicleDO;
import com.winit.iKonnect.dataobject.BankAccountUpdateDO;
import com.winit.iKonnect.dataobject.BankLoanDO;
import com.winit.iKonnect.dataobject.BusinessCardDo;
import com.winit.iKonnect.dataobject.BusinessTravelDo;
import com.winit.iKonnect.dataobject.C3CardATMSwitchClaimDO;
import com.winit.iKonnect.dataobject.C3CardReplacementDO;
import com.winit.iKonnect.dataobject.C3CardStatementDO;
import com.winit.iKonnect.dataobject.CardCancellationDO;
import com.winit.iKonnect.dataobject.ChangeofSalaryBankAccountDO;
import com.winit.iKonnect.dataobject.CommitmentFormDO;
import com.winit.iKonnect.dataobject.DrivingLicenceDO;
import com.winit.iKonnect.dataobject.EmploymentLetterDO;
import com.winit.iKonnect.dataobject.FamilyResidenceVisitVisaModel;
import com.winit.iKonnect.dataobject.HospitalLetterDO;
import com.winit.iKonnect.dataobject.HousingAllowanceDO;
import com.winit.iKonnect.dataobject.InsuranceCardFamilyDetailsModel;
import com.winit.iKonnect.dataobject.LeaveApplicationDO;
import com.winit.iKonnect.dataobject.LeaveJoiningDO;
import com.winit.iKonnect.dataobject.LeaveRequestDO;
import com.winit.iKonnect.dataobject.MedicalExpenseClaimDO;
import com.winit.iKonnect.dataobject.PassportReleaseDO;
import com.winit.iKonnect.dataobject.PayrollCardDO;
import com.winit.iKonnect.dataobject.SalaryCertificateRequestDO;
import com.winit.iKonnect.dataobject.SalaryTransferLetterRequestDO;
import com.winit.iKonnect.dataobject.SalaryTransferRequestBankAccountDo;
import com.winit.iKonnect.dataobject.SystemAccessDO;
import com.winit.iKonnect.dataobject.TransportLoanDO;
import com.winit.iKonnect.dataobject.VisaLetterDO;
import com.winit.iKonnect.dataobject.VisaSpouseChildrenDO;
import com.winit.iKonnect.dataobject.msgModel;
import com.winit.iKonnect.dataobject.response.GrievanceDO;
import com.winit.iKonnect.dataobject.response.InsuranceCardModel;

import java.util.ArrayList;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class ServiceRequestBody {

    private FamilyResidenceVisitVisaModel FamilyResidenceVisitVisa;
    private DrivingLicenceDO DrivingLicence;
    private VisaLetterDO VisaLetter;
    private EmploymentLetterDO EmploymentLetter;
    private AdvanceHousingAllowanceDO AdvanceHousingAllowance;
    private MedicalExpenseClaimDO MedicalExpenseClaim;
    private msgModel msgModel;
    private BankAccountUpdateDO BankAccountUpdate;
    private ChangeofSalaryBankAccountDO ChangeofSalaryBankAccount;
    private BankLoanDO BankLoan;
    private PayrollCardDO PayrollCard;
    private InsuranceCardFamilyDetailsModel InsuranceCardFamilyDetailsModel;
    private HospitalLetterDO HospitalLetter;
    private GrievanceDO Grievance;
    private LeaveRequestDO LeaveRequest;
    private InsuranceCardModel InsuranceCard;
    private AuthorizationtoDriveCompanyVehicleDO insurance;
    private ArrayList<String> arrServiceAttachments;
    private int serviceId;
    private int Formid;
    //newly added by sandeep
    private SalaryTransferRequestBankAccountDo BankSalaryTransfer;
    private C3CardATMSwitchClaimDO C3CardATMSwitchClaim;

    private SalaryCertificateRequestDO SalaryCertificateRequest;
    private SalaryTransferLetterRequestDO SalaryTransferLetterRequest;
    private SystemAccessDO SystemAccess;
    private TransportLoanDO TransportLoan;
    private CommitmentFormDO CommitmentForm;
    private VisaSpouseChildrenDO VisaSpouseChildren;
    private VisaSpouseChildrenDO VisaInLawsChildren;
    private VisaSpouseChildrenDO FamilyVisaSpouseChildren;
    private VisaSpouseChildrenDO FamilyVisaParents;

    private C3CardReplacementDO C3CardReplacement;
    private C3CardStatementDO C3CardStatement;
    private CardCancellationDO C3CardCancelation;
    private BusinessCardDo BusinessCard;
    private BusinessTravelDo BusinessTravel;
    private HousingAllowanceDO HousingAllowance;
    private LeaveJoiningDO LeaveJoining;
    private LeaveApplicationDO AnnualLeave;
    private PassportReleaseDO PassportRelease;

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

    public SystemAccessDO getSystemAccess() {
        return SystemAccess;
    }

    public void setSystemAccess(SystemAccessDO systemAccess) {
        SystemAccess = systemAccess;
    }

    public TransportLoanDO getTransportLoan() {
        return TransportLoan;
    }

    public void setTransportLoan(TransportLoanDO transportLoan) {
        TransportLoan = transportLoan;
    }

    public CommitmentFormDO getCommitmentForm() {
        return CommitmentForm;
    }

    public void setCommitmentForm(CommitmentFormDO commitmentForm) {
        CommitmentForm = commitmentForm;
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

    public VisaSpouseChildrenDO getFamilyVisaParents() {
        return FamilyVisaParents;
    }

    public void setFamilyVisaParents(VisaSpouseChildrenDO familyVisaParents) {
        FamilyVisaParents = familyVisaParents;
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

    public LeaveApplicationDO getAnnualLeave() {
        return AnnualLeave;
    }

    public void setAnnualLeave(LeaveApplicationDO annualLeave) {
        AnnualLeave = annualLeave;
    }

    public PassportReleaseDO getPassportRelease() {
        return PassportRelease;
    }

    public void setPassportRelease(PassportReleaseDO passportRelease) {
        PassportRelease = passportRelease;
    }








    /*******************************added newly by sandeep******************************/
    public SalaryTransferRequestBankAccountDo getSalaryTransferRequestBankAccountDo() {
        return BankSalaryTransfer;
    }

    public void setSalaryTransferRequestBankAccountDo(SalaryTransferRequestBankAccountDo BankSalaryTransfer) {
        this.BankSalaryTransfer = BankSalaryTransfer;
    }


    public int getFormid() {
        return Formid;
    }

    public void setFormid(int Formid) {
        this.Formid = Formid;
    }


    public C3CardATMSwitchClaimDO getC3CardATMSwitchClaimDO() {
        return C3CardATMSwitchClaim;
    }

    public void setC3CardATMSwitchClaimDO(C3CardATMSwitchClaimDO C3CardATMSwitchClaim) {
        this.C3CardATMSwitchClaim = C3CardATMSwitchClaim;
    }


    /*****************************************    end   ***************************************************/


    public FamilyResidenceVisitVisaModel getFamilyResidenceVisitVisa() {
        return FamilyResidenceVisitVisa;
    }

    public void setFamilyResidenceVisitVisa(FamilyResidenceVisitVisaModel familyResidenceVisitVisa) {
        FamilyResidenceVisitVisa = familyResidenceVisitVisa;
    }

    public DrivingLicenceDO getDrivingLicence() {
        return DrivingLicence;
    }


    public void setDrivingLicence(DrivingLicenceDO drivingLicence) {
        DrivingLicence = drivingLicence;
    }

    public VisaLetterDO getVisaLetter() {
        return VisaLetter;
    }

    public void setVisaLetter(VisaLetterDO visaLetter) {
        VisaLetter = visaLetter;
    }

    public EmploymentLetterDO getEmploymentLetter() {
        return EmploymentLetter;
    }

    public void setEmploymentLetter(EmploymentLetterDO employmentLetter) {
        EmploymentLetter = employmentLetter;
    }

    public AdvanceHousingAllowanceDO getAdvanceHousingAllowance() {
        return AdvanceHousingAllowance;
    }

    public void setAdvanceHousingAllowance(AdvanceHousingAllowanceDO advanceHousingAllowance) {
        AdvanceHousingAllowance = advanceHousingAllowance;
    }

    public MedicalExpenseClaimDO getMedicalExpenseClaim() {
        return MedicalExpenseClaim;
    }

    public void setMedicalExpenseClaim(MedicalExpenseClaimDO medicalExpenseClaim) {
        MedicalExpenseClaim = medicalExpenseClaim;
    }

    public void setContactDetail(msgModel msgModel) {
        msgModel = msgModel;
    }

    public BankAccountUpdateDO getBankAccountUpdate() {
        return BankAccountUpdate;
    }

    public void setBankAccountUpdate(BankAccountUpdateDO bankAccountUpdate) {
        BankAccountUpdate = bankAccountUpdate;
    }

    public ChangeofSalaryBankAccountDO getChangeofSalaryBankAccount() {
        return ChangeofSalaryBankAccount;
    }

    public void setChangeofSalaryBankAccount(ChangeofSalaryBankAccountDO changeofSalaryBankAccount) {
        ChangeofSalaryBankAccount = changeofSalaryBankAccount;
    }

    public BankLoanDO getBankLoan() {
        return BankLoan;
    }

    public void setBankLoan(BankLoanDO bankLoan) {
        BankLoan = bankLoan;
    }

    public PayrollCardDO getPayrollCard() {
        return PayrollCard;
    }

    public void setPayrollCard(PayrollCardDO payrollCard) {
        PayrollCard = payrollCard;
    }

    public InsuranceCardFamilyDetailsModel getInsuranceCardFamilyDetailsModel() {
        return InsuranceCardFamilyDetailsModel;
    }

    public void setInsuranceCardFamilyDetailsModel(InsuranceCardFamilyDetailsModel insuranceCardFamilyDetailsModel) {
        InsuranceCardFamilyDetailsModel = insuranceCardFamilyDetailsModel;
    }

    public HospitalLetterDO getHospitalLetter() {
        return HospitalLetter;
    }

    public void setHospitalLetter(HospitalLetterDO hospitalLetter) {
        HospitalLetter = hospitalLetter;
    }

    public void setGrievance(GrievanceDO grievance) {
        Grievance = grievance;
    }

    public GrievanceDO getGrievance() {
        return Grievance;
    }

    public LeaveRequestDO getLeaveRequest() {
        return LeaveRequest;
    }

    public void setLeaveRequest(LeaveRequestDO leaveRequest) {
        LeaveRequest = leaveRequest;
    }

    public AuthorizationtoDriveCompanyVehicleDO getAuthorizationtoDriveCompanyVehicle() {
        return AuthorizationtoDriveCompanyVehicle;
    }

    public void setAuthorizationtoDriveCompanyVehicle(AuthorizationtoDriveCompanyVehicleDO authorizationtoDriveCompanyVehicle) {
        AuthorizationtoDriveCompanyVehicle = authorizationtoDriveCompanyVehicle;
    }

    private AuthorizationtoDriveCompanyVehicleDO AuthorizationtoDriveCompanyVehicle;

    public void setInsurance(InsuranceCardModel insurance) {
        this.InsuranceCard = insurance;
    }

    public ArrayList<String> getArrServiceAttachments() {
        return arrServiceAttachments;
    }

    public void setArrServiceAttachments(ArrayList<String> arrServiceAttachments) {
        this.arrServiceAttachments = arrServiceAttachments;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
