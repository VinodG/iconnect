package com.winit.iKonnect.module.form;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.util.CalendarUtil;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.BusinessCardDo;
import com.winit.iKonnect.dataobject.BusinessTravelDo;
import com.winit.iKonnect.dataobject.C3CardATMSwitchClaimDO;
import com.winit.iKonnect.dataobject.C3CardReplacementDO;
import com.winit.iKonnect.dataobject.C3CardStatementDO;
import com.winit.iKonnect.dataobject.CardCancellationDO;
import com.winit.iKonnect.dataobject.CommitmentFormDO;
import com.winit.iKonnect.dataobject.FormRequestDO;
import com.winit.iKonnect.dataobject.HousingAllowanceDO;
import com.winit.iKonnect.dataobject.LeaveApplicationDO;
import com.winit.iKonnect.dataobject.LeaveJoiningDO;
import com.winit.iKonnect.dataobject.PassportReleaseDO;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.SalaryCertificateRequestDO;
import com.winit.iKonnect.dataobject.SalaryTransferLetterRequestDO;
import com.winit.iKonnect.dataobject.SalaryTransferRequestBankAccountDo;
import com.winit.iKonnect.dataobject.ServiceDO;
import com.winit.iKonnect.dataobject.ServiceRequestDO;
import com.winit.iKonnect.dataobject.ServiceResponseData;
import com.winit.iKonnect.dataobject.SystemAccessDO;
import com.winit.iKonnect.dataobject.TransportLoanDO;
import com.winit.iKonnect.dataobject.VisaSpouseChildrenDO;
import com.winit.iKonnect.dataobject.request.ServiceRequest;
import com.winit.iKonnect.dataobject.request.ServiceRequestBody;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.form.interactors.AsyncFormInteractor;
import com.winit.iKonnect.module.form.interactors.IAsyncFormInteractor;
import com.winit.iKonnect.ui.activities.BaseActivity;
import com.winit.iKonnect.ui.activities.SalaryTransferRequestBankAccount;
import com.winit.iKonnect.ui.activities.TransportLoanRequestActivity;

import java.util.ArrayList;
import java.util.HashMap;


import static com.winit.common.Preference.ATTACHMENT_COUNT;
import static com.winit.common.Preference.BANK_LOAN;
import static com.winit.common.Preference.COMPANY_ACCOMODATION;
import static com.winit.common.Preference.CONFIRMATION;
import static com.winit.common.Preference.OUTSTANDING_HRA;
import static com.winit.common.util.CalendarUtil.EEEE_PATTERN;
import static com.winit.common.util.CalendarUtil.findTwoDaysCount;
import static com.winit.common.util.CalendarUtil.getDay;
import static com.winit.common.util.CalendarUtil.getDifferenceOfYear;
import static com.winit.common.util.StringUtils.getString;
import static com.winit.iKonnect.R.id.btnOK;
import static com.winit.iKonnect.R.id.et_leave_request;
import static com.winit.iKonnect.R.id.totalDay;
import static com.winit.iKonnect.R.string.amount;


/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class FormPresenter extends BasePresenter implements IFormPresenter, IAsyncFormInteractor.OnFormPostListener {

    private ArrayList<RadioBTNDO> radioBTNDOs;
    private ArrayList<String> attachments;

    private IAsyncFormInteractor iAsyncFormInteractor;
    private IFormView iFormView;
    protected ServiceDO serviceDO;
    private Context context, mcontext;
    private CalendarUtil calendarUtil;


    public FormPresenter(IFormView iFormView) {
        super(iFormView);
        this.iFormView = iFormView;

        this.iAsyncFormInteractor = new AsyncFormInteractor(this);
        context = IKonnectApplication.mContext;
    }

    public FormPresenter(ServiceDO serviceDO, IFormView iFormView) {
        super(iFormView);
        this.serviceDO = serviceDO;
        this.iFormView = iFormView;
        mcontext = (BaseActivity) iFormView;
        this.iAsyncFormInteractor = new AsyncFormInteractor(this);
        context = IKonnectApplication.mContext;
    }

    @Override
    public void setAttachment(String path) {
        if (attachments == null)
            attachments = new ArrayList<>();
        attachments.add(path);
    }

    @Override
    public void loadData() {
    }

    @Override
    public void removeAttachment(String path) {
        attachments.remove(path);
    }

    /******************************************* Bank Account Update Salary Transfer Request Start*********************************************************/

    @Override
    public void submitBankAccountUpdateSalaryTransferRequest(String reason, final String bankName, final String accountNumber,
                                                             final String Iban, final String signature) {

        if (StringUtils.isEmpty(bankName)) {
            iFormView.showAlert(IFormView.ENTER_BANK_NAME);
        } else if (StringUtils.isEmpty(accountNumber)) {
            iFormView.showAlert(IFormView.ENTER_ACCOUNT_NO);
        } else if (StringUtils.isEmpty(Iban) && !(preference.getStringFromPreference(Preference.STAFF_WORK_COUNTRY, "N/A").equalsIgnoreCase("Oman"))) {
            iFormView.showAlert(IFormView.ENTER_IBAN);
        } else if (!StringUtils.isEmpty(Iban) && !(Iban.trim().length() == 23)) {
            iFormView.showAlert(IFormView.ENTER_23DIGITS_IBAN);
        } else {
            ((BaseActivity) mcontext).showSignatureDialog();
            if (((BaseActivity) mcontext).btnOK != null) {
                ((BaseActivity) mcontext).btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!((BaseActivity) mcontext).customerSignature.isSigned()) {
                            ((BaseActivity) mcontext).mCustomDialogSign.dismiss();
                            ((BaseActivity) mcontext).hideLoader();
                            ((BaseActivity) mcontext).showCustomDialog(context, "Alert !",
                                    "Signature is mandatory.",
                                    context.getString(R.string.OK), null, "scroll");
                        } else {
                            if (((BaseActivity) mcontext).mCustomDialogSign != null) {
                                ((BaseActivity) mcontext).mCustomDialogSign.dismiss();
                            }
                            ((BaseActivity) mcontext).postSignature();

                            final SalaryTransferRequestBankAccountDo BankSalaryTransfer = new SalaryTransferRequestBankAccountDo();
                            BankSalaryTransfer.setBankName(bankName);
                            BankSalaryTransfer.setAccountNo(accountNumber);
                            BankSalaryTransfer.setIBAN(Iban);
                            BankSalaryTransfer.setSignature(signature);
                            BankSalaryTransfer.setStaffName(preference.getStringFromPreference(Preference.STAFF_NAME, ""));
                            BankSalaryTransfer.setStaffNumber(preference.getStringFromPreference(Preference.STAFF_NUMBER, ""));
                            BankSalaryTransfer.setDepartment(preference.getStringFromPreference(Preference.DEPARTMENT, ""));

                            if (AppConstants.cancellationFlag) {
                                submitForm(AppConstants.serviceDO.serviceType, AppConstants.cardCancellationDO);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        submitForm(serviceDO.serviceType, BankSalaryTransfer);
                                    }
                                },5000);
                               /* new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(3000);

                                            submitForm(serviceDO.serviceType, BankSalaryTransfer);

                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }).start();*/


                            } else {
                                submitForm(serviceDO.serviceType, BankSalaryTransfer);
                            }
                        }
                    }
                });
            }
        }
    }
    /******************************************* Bank Account Update Salary Transfer Request end*********************************************************/

    /****************************************Atm Switch Claim Form start **************************************************/

    @Override
    public void submitAtmSwitchClaim(String reason, final String cardNumber, final String cardMemberMobileNumber, final String transactionDate, final String transactionTime, final String amountWithDraw, final String amountRecived, final String amountDisputed, final String bankName, final String atmLocation, final String additionalComment, final String signature) {

        if (StringUtils.isEmpty(cardMemberMobileNumber)) {
            iFormView.showAlert(iFormView.EMPTY_CARDHOLDER_MOBILE_NUMBER);
        } else if (StringUtils.isEmpty(transactionDate)) {
            iFormView.showAlert(iFormView.EMPTY_DATE);
        } else if (StringUtils.isEmpty(transactionTime)) {
            iFormView.showAlert(iFormView.EMPTY_TIME);
        } else if (StringUtils.isEmpty(amountWithDraw)) {
            iFormView.showAlert(iFormView.EMPTY_AMOUNT_WITHDRAW);
        } else if (Long.parseLong(amountWithDraw) == 0) {
            iFormView.showAlert(iFormView.ZEROVALUE);
        } else if (StringUtils.isEmpty(amountRecived)) {
            iFormView.showAlert(iFormView.EMPTY_AMOUNT_RECIVED);
        } else if (StringUtils.isEmpty(amountDisputed)) {
            iFormView.showAlert(iFormView.EMPTY_AMOUNTDISPUTED);
        } else if (StringUtils.toInt(amountWithDraw) < StringUtils.toInt(amountRecived)) {
            iFormView.showAlert(iFormView.RANGEEXCEED);
        } else if (StringUtils.isEmpty(bankName)) {
            iFormView.showAlert(iFormView.ENTER_BANK_NAME);
        } else if (StringUtils.isEmpty(atmLocation)) {
            iFormView.showAlert(iFormView.EMPTY_ATM_LOCATION);
        } else {
            ((BaseActivity) mcontext).showSignatureDialog();
            if (((BaseActivity) mcontext).btnOK != null) {
                ((BaseActivity) mcontext).btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!((BaseActivity) mcontext).customerSignature.isSigned()) {
                            ((BaseActivity) mcontext).mCustomDialogSign.dismiss();
                            ((BaseActivity) mcontext).hideLoader();
                            ((BaseActivity) mcontext).showCustomDialog(context, "Alert !",
                                    "Signature is mandatory.",
                                    context.getString(R.string.OK), null, "scroll");
                        } else {
                            if (((BaseActivity) mcontext).mCustomDialogSign != null) {
                                ((BaseActivity) mcontext).mCustomDialogSign.dismiss();
                            }
                            ((BaseActivity) mcontext).postSignature();
                            C3CardATMSwitchClaimDO C3CardATMSwitchClaim = new C3CardATMSwitchClaimDO();
                            C3CardATMSwitchClaim.setCardNumber(cardNumber);
                            C3CardATMSwitchClaim.setCardmemberMobileNumber(cardMemberMobileNumber);
                            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
                            C3CardATMSwitchClaim.setTransactionDate(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(transactionDate,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));
                            C3CardATMSwitchClaim.setTransactionTime(transactionTime);
                            C3CardATMSwitchClaim.setAmountWithdrawn(amountWithDraw);
                            C3CardATMSwitchClaim.setAmountReceived(amountRecived);
                            C3CardATMSwitchClaim.setAmountDisputed(amountDisputed);
                            C3CardATMSwitchClaim.setBankName(bankName);
                            C3CardATMSwitchClaim.setATMLocation(atmLocation);
                            C3CardATMSwitchClaim.setAdditionalComments(additionalComment);
                            C3CardATMSwitchClaim.setSignature(signature);

                            submitForm(serviceDO.serviceType, C3CardATMSwitchClaim);

                        }

                    }
                });
            }
        }

    }
    /****************************************Atm Switch Claim Form end **************************************************/
    /****************************************  Card Replacement Service Form start **************************************************/

    @Override
    public void submitCardReplacementService(String reason,
                                             final String cardType,
                                             final String cardRetainment,
                                             final String CompanyName,
                                             final String BranchID,
                                             final String AuthorizedPerson,
                                             final String ContactNumber,
                                             final String CardMemberFullName,
                                             final String CardholderMobileNumber,
                                             final String EmployeeID, final String C3PayrollCardNumber, final String Signature) {


        if (StringUtils.isEmpty(cardType) && cardType.equalsIgnoreCase("Captured/Retained by ATM") && StringUtils.isEmpty(cardRetainment) && StringUtils.isEmpty(CardholderMobileNumber)) {
            iFormView.showAlert(IFormView.EMPTY_CARD_STATUS);
        } else if (StringUtils.isEmpty(cardType)) {
            iFormView.showAlert(IFormView.EMPTY_CARD_TYPE_STATUS);
        } else if (cardType.equalsIgnoreCase("Captured/Retained by ATM") && StringUtils.isEmpty(cardRetainment)) {
            iFormView.showAlert(IFormView.EMPTY_CHECKBOX);
        } else if (StringUtils.isEmpty(CardholderMobileNumber)) {
            iFormView.showAlert(iFormView.EMPTY_CARDHOLDER_MOBILE_NUMBER);
        } else {
            ((BaseActivity) mcontext).showSignatureDialog();
            if (((BaseActivity) mcontext).btnOK != null) {
                ((BaseActivity) mcontext).btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!((BaseActivity) mcontext).customerSignature.isSigned()) {
                            ((BaseActivity) mcontext).mCustomDialogSign.dismiss();
                            ((BaseActivity) mcontext).hideLoader();
                            ((BaseActivity) mcontext).showCustomDialog(context, "Alert !",
                                    "Signature is mandatory.",
                                    context.getString(R.string.OK), null, "scroll");
                        } else {
                            if (((BaseActivity) mcontext).mCustomDialogSign != null) {
                                ((BaseActivity) mcontext).mCustomDialogSign.dismiss();
                            }
                            ((BaseActivity) mcontext).postSignature();
                            C3CardReplacementDO C3CardReplacement = new C3CardReplacementDO();

                            C3CardReplacement.setCardType(cardType);
                            C3CardReplacement.setCapturedRetainedbyATM(cardRetainment);
                            C3CardReplacement.setCompanyName(CompanyName);
                            C3CardReplacement.setBranchID(BranchID);
                            C3CardReplacement.setAuthorizedPerson(AuthorizedPerson);
                            C3CardReplacement.setContactNumber(ContactNumber);
                            C3CardReplacement.setCardMemberFullName(CardMemberFullName);
                            C3CardReplacement.setCardholderMobileNumber(CardholderMobileNumber);
                            C3CardReplacement.setEmployeeID(EmployeeID);
                            C3CardReplacement.setC3PayrollCardNumber(C3PayrollCardNumber);
                            C3CardReplacement.setSignature(Signature);

                            submitForm(serviceDO.serviceType, C3CardReplacement);

                        }

                    }
                });
            }
        }
    }
    /****************************************  Card Replacement Service Form end **************************************************/

    /****************************************  Card Statement Service Form start **************************************************/

    @Override
    public void submitCardStatementService(String reason, final String cardStatementType,
                                           final String startDate, final String endDate,
                                           final String CompanyName, final String BranchID,
                                           final String AuthorizedPerson, final String ContactNumber,
                                           final String CardMemberFullName, final String CardholderMobileNumber,
                                           final String EmployeeID, final String C3PayrollCardNumber, final String Signature) {

        if (StringUtils.isEmpty(cardStatementType)) {
            iFormView.showAlert(iFormView.EMPTY_CARD_STATEMENT_TYPE);
        } else if (StringUtils.isEmpty(startDate)) {
            iFormView.showAlert(iFormView.EMPTY_DATE);
        } else if (StringUtils.isEmpty(endDate)) {
            iFormView.showAlert(iFormView.EMPTY_END_DATE);
        }else if (CalendarUtil.CompareDates(startDate,endDate)==1){
            iFormView.showAlert(iFormView.VALID_DATE_SELECTION);
        }
        else if (StringUtils.isEmpty(ContactNumber)) {
            iFormView.showAlert(iFormView.EMPTY_CONTACTNUMBER);
        } else if (StringUtils.isEmpty(CardholderMobileNumber)) {
            iFormView.showAlert(iFormView.EMPTY_MOBILE);
        } else {
            ((BaseActivity) mcontext).showSignatureDialog();
            if (((BaseActivity) mcontext).btnOK != null) {
                ((BaseActivity) mcontext).btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!((BaseActivity) mcontext).customerSignature.isSigned()) {
                            ((BaseActivity) mcontext).mCustomDialogSign.dismiss();
                            ((BaseActivity) mcontext).hideLoader();
                            ((BaseActivity) mcontext).showCustomDialog(context, "Alert !",
                                    "Signature is mandatory.",
                                    context.getString(R.string.OK), null, "scroll");
                        } else {
                            if (((BaseActivity) mcontext).mCustomDialogSign != null) {
                                ((BaseActivity) mcontext).mCustomDialogSign.dismiss();
                            }
                            ((BaseActivity) mcontext).postSignature();

                            C3CardStatementDO C3CardStatement = new C3CardStatementDO();
                            C3CardStatement.setCardsStatement(cardStatementType);
                            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
                            C3CardStatement.setStartDate(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(startDate,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));
                            C3CardStatement.setEndDate(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(endDate,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));
                            //Company details field is not avilable in design so I am passing nothing
                            C3CardStatement.setCompanyDetails("N/A");
                            C3CardStatement.setCompanyName(CompanyName);
                            C3CardStatement.setBranchID(BranchID);
                            C3CardStatement.setAuthorizedPerson(AuthorizedPerson);
                            C3CardStatement.setContactNumber(ContactNumber);
                            C3CardStatement.setCardMemberFullName(CardMemberFullName);
                            C3CardStatement.setCardholderMobileNumber(CardholderMobileNumber);
                            C3CardStatement.setEmployeeID(EmployeeID);
                            C3CardStatement.setC3PayrollCardNumber(C3PayrollCardNumber);
                            C3CardStatement.setSignature(Signature);

                            submitForm(serviceDO.serviceType, C3CardStatement);
                        }

                    }
                });
            }
        }

    }

    /****************************************  Card Statement Service Form end **************************************************/


    /**************************************** Card Cancellation Form start **************************************************/

    @Override
    public void submitCardCancellation(String reason, String CompanyName, String CompanyDetails, String BranchID, String AuthorizedPerson, String ContactNumber, String CardMemberFullName, String CardholderMobileNumber, String employeeId, String C3PayrollCardNumber, String others) {

        if (StringUtils.isEmpty(reason)) {
            iFormView.showAlert(iFormView.EMPTY_REASON);
        } else if (StringUtils.isEmpty(CardholderMobileNumber)) {
            iFormView.showAlert(iFormView.EMPTY_CARDHOLDER_MOBILE_NUMBER);
        } else if (reason.equalsIgnoreCase("Others") && others.equalsIgnoreCase("")) {
            iFormView.showAlert(iFormView.OTHER_INSTRUCTIONS);
        } else {

            CardCancellationDO C3CardCancelation = new CardCancellationDO();
            if (!reason.equalsIgnoreCase("Others"))
                C3CardCancelation.setReasonForCancelation(reason);
            else
                C3CardCancelation.setReasonForCancelation(others);
            C3CardCancelation.setCompanyDetails("");
            C3CardCancelation.setCompanyName(CompanyName);
            C3CardCancelation.setBranchID(BranchID);
            C3CardCancelation.setAuthorizedPersone(AuthorizedPerson);
            C3CardCancelation.setContactNumber(ContactNumber);
            C3CardCancelation.setCardMemberFullName(CardMemberFullName);
            C3CardCancelation.setCardholderMobileNumber(CardholderMobileNumber);
            C3CardCancelation.setC3PayrollCardNumber(C3PayrollCardNumber);
            C3CardCancelation.setEmployee_ID_No(employeeId);
            C3CardCancelation.setSignature("");

            //holding object for latter use
            AppConstants.cardCancellationDO = C3CardCancelation;
            AppConstants.serviceDO = serviceDO;


            //  submitForm(serviceDO.serviceType, C3CardCancelation);

            // ((BaseActivity) mcontext).showCustomDialog(mcontext, context.getString(R.string.alert), "Please submit salary transfer request form to be proceed", context.getString(R.string.OK), context.getString(R.string.cancel), "CardCancel");


            //alert


            ((BaseActivity) mcontext).hideLoader();
            AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
            builder.setMessage("Please submit Salary Transfer Request form to proceed")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            AppConstants.cancellationFlag = true;
                            AppConstants.popupFlag = true;


                            ServiceDO serviceDO = new ServiceDO();
                            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Salary_Transfer_Request);
                            serviceDO.serviceLogo = R.drawable.form_one;
                            serviceDO.serviceType = ServiceDO.ServiceType.SALARY_TRANSFER_REQUEST;
                            Intent intent = new Intent(mcontext, SalaryTransferRequestBankAccount.class);
                            intent.putExtra(ConstantExtraKey.SERVICE_OBJECT, serviceDO);
                            (mcontext).startActivities(new Intent[]{intent});
//                            ((BaseActivity) mcontext).finish();


                        }
                    })


                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });


            final AlertDialog alert = builder.create();


            alert.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.app_color));
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.app_color));

                }
            });
            alert.setTitle("Alert");
            alert.show();


        }


    }
    /**************************************** Card Cancellation Form end **************************************************/


    /**************************************** Bussiness Card Form start **************************************************/

    @Override
    public void submitBussinessCard(String reason, String Emirate, String POBox, String TelNo, String Ext, String Fax, String Mobile_CUG, String Email, String WebSite) {


        if (StringUtils.isEmpty(Emirate)) {
            iFormView.showAlert(iFormView.EMPTY_CITY);
        } else if (StringUtils.isEmpty(POBox)) {
            iFormView.showAlert(iFormView.EMPTY_PO_BOX);
        } else if (StringUtils.isEmpty(TelNo)) {
            iFormView.showAlert(iFormView.EMPTY_TELNO);
        } else if (StringUtils.isEmpty(Fax)) {
            iFormView.showAlert(iFormView.EMPTY_FAX);
        } else if (StringUtils.isEmpty(Mobile_CUG)) {
            iFormView.showAlert(iFormView.EMPTY_MOBILE);
        } else if (StringUtils.isEmpty(Email)) {
            iFormView.showAlert(iFormView.EMPTY_EMAIL);
        } else if (StringUtils.isEmpty(WebSite)) {
            iFormView.showAlert(iFormView.EMPTY_WEBSITE);
        } else {

            if (!isValidEmail(Email)) {
                iFormView.showAlert(iFormView.VALID_EMAIL);
            } else {
                BusinessCardDo BusinessCard = new BusinessCardDo();
                BusinessCard.setEmirate(Emirate);
                BusinessCard.setPOBox(POBox);
                BusinessCard.setTelNo(TelNo);
                BusinessCard.setExt(Ext);
                BusinessCard.setFax(Fax);
                BusinessCard.setMobile_CUG(Mobile_CUG);
                BusinessCard.setEmail(Email);
                BusinessCard.setWebsite(WebSite);


                submitForm(serviceDO.serviceType, BusinessCard);
            }

        }

    }
    /**************************************** Bussiness Card Form end **************************************************/

    /**************************************** Bussiness Travel Request Form start **************************************************/

    @Override
    public void submitBussinessTravelRequest(String reason, String Destinations, String PurposeofTravel, String StartDate, String EndDate, String ContactNumberduringBusinessTravel, String TicketRequired, String Sector, String VisaRequired, String HotelAccommodation, String CostDebitto, String AnyOtherRequirements) {

        if (StringUtils.isEmpty(Destinations)) {

            iFormView.showAlert(iFormView.EMPTY_DESTINATION);
        } else if (StringUtils.isEmpty(PurposeofTravel)) {
            iFormView.showAlert(iFormView.EMPTY_PURPOSE_OF_TRAVEL);
        } else if (StringUtils.isEmpty(StartDate)) {
            iFormView.showAlert(iFormView.START_DATE);
        } else if (StringUtils.isEmpty(EndDate)) {
            iFormView.showAlert(iFormView.END_DATE);
        } else if (StringUtils.isEmpty(ContactNumberduringBusinessTravel)) {
            iFormView.showAlert(iFormView.EMPTY_MOBILE);
        } else if (StringUtils.isEmpty(TicketRequired)) {
            iFormView.showAlert(iFormView.SELECT_TICKETTYPE);
        } else if (TicketRequired.equalsIgnoreCase("yes") && (Sector.length() <= 0)) {
            iFormView.showAlert(iFormView.EMPTY_SECTOR);
        } else if (StringUtils.isEmpty(VisaRequired)) {
            iFormView.showAlert(iFormView.EMPTY_VISA);
        } else if (StringUtils.isEmpty(HotelAccommodation)) {
            iFormView.showAlert(iFormView.HOSTEL_ACCOMIDATION);
        } else if (StringUtils.isEmpty(CostDebitto)) {
            iFormView.showAlert(iFormView.COST_DEBIT_TO);
        } else {

            if (TicketRequired.equalsIgnoreCase("yes") && (Sector.length() <= 0)) {

                iFormView.showAlert(iFormView.EMPTY_SECTOR);

            } else if (!(ContactNumberduringBusinessTravel.length() == 9 || ContactNumberduringBusinessTravel.length() == 10 ||
                    ContactNumberduringBusinessTravel.length() == 11)) {
                iFormView.showAlert(iFormView.NUMBER_LENGHT);
            } else if (!(calendarUtil.getDifference(StartDate, calendarUtil.DD_MMM_YYYY_PATTERN, EndDate, calendarUtil.DD_MMM_YYYY_PATTERN) > 0)) {
                iFormView.showAlert(iFormView.VALID_DATE_SELECTION);
            }
/*
            } else if (!(findDateDifference(StartDate, EndDate) > 0)) {
                iFormView.showAlert(iFormView.VALID_DATE_SELECTION);
            } */
            else {


                BusinessTravelDo BusinessTravel = new BusinessTravelDo();
                BusinessTravel.setDestinations(Destinations);
                BusinessTravel.setPurposeofTravel(PurposeofTravel);
                //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
                BusinessTravel.setStartDate(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(StartDate,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));
                BusinessTravel.setEndDate(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(EndDate,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

                BusinessTravel.setContactNumberduringBusinessTravel(ContactNumberduringBusinessTravel);
                BusinessTravel.setTicketRequired(TicketRequired);
                BusinessTravel.setSector(Sector);
                BusinessTravel.setVisaRequired(VisaRequired);
                BusinessTravel.setHotelAccommodation(HotelAccommodation);
                BusinessTravel.setCostDebitto(CostDebitto);
                BusinessTravel.setAnyOtherRequirements(AnyOtherRequirements);

                submitForm(serviceDO.serviceType, BusinessTravel);

            }
        }


    }
    /**************************************** Bussiness Travel Request Form end **************************************************/

    /*************************************** Commitment Form start **************************************************/
    @Override
    public void CommitmentForm(String contributionAmt, String remarks) {
        if (StringUtils.isEmpty(contributionAmt)) {
            iFormView.showAlert(iFormView.CONTRIBUTION_AMT);
        } else {
            CommitmentFormDO CommitmentForm = new CommitmentFormDO();
            CommitmentForm.setContributionAmount(contributionAmt);
            CommitmentForm.setRemarks(remarks);

            submitForm(serviceDO.serviceType, CommitmentForm);
        }
    }

    /**************************************** Commitment Form end **************************************************/

    /**************************************** House Allowance Form start **************************************************/

    @Override
    public void submitHouseAllowance(final String name, String s, final String location, final String designation, final String department, final String advanceRequest, final String staffNo) {
        if (StringUtils.isEmpty(advanceRequest)) {
            iFormView.showAlert(iFormView.ADVANCEREQUEST);

        }
        else if (preference.getStringFromPreference(CONFIRMATION, "").equalsIgnoreCase("")) {

            iFormView.showAlert(IFormView.CONFIRMATION_EMPTY);
        }
        else if (!preference.getStringFromPreference(CONFIRMATION, "").equalsIgnoreCase("z0") &&
                !preference.getStringFromPreference(CONFIRMATION, "").equalsIgnoreCase("z9"))
        {
            iFormView.showAlert(IFormView.CONFIRMATION_NOT_EMPTY);
        }
        else if (preference.getbooleanFromPreference(COMPANY_ACCOMODATION, false)) {
            iFormView.showAlert(IFormView.COMPANY_ACCOMIDATION);
        }
        else if (preference.getbooleanFromPreference(BANK_LOAN, false)) {
            iFormView.showAlert(IFormView.BANK_LOAN);
        }
        else if (!preference.getStringFromPreference(OUTSTANDING_HRA, "0.0").equalsIgnoreCase("null") && Double.parseDouble(preference.getStringFromPreference(OUTSTANDING_HRA, "0.0")) > 0.0) {
            iFormView.showAlert(IFormView.OUTSTANDING_HRA);
        }
        else if (preference.getIntFromPreference(ATTACHMENT_COUNT, 0) <= 0
                && (advanceRequest.equalsIgnoreCase("4")
                || advanceRequest.equalsIgnoreCase("5")
                || advanceRequest.equalsIgnoreCase("6"))) {
            iFormView.showAlert(IFormView.ATTACHMENT_EMPTY);
        } else {

            HousingAllowanceDO HousingAllowance = new HousingAllowanceDO();
            HousingAllowance.setName(name);
            HousingAllowance.setLocation(location);
            HousingAllowance.setDesignation(designation);
            HousingAllowance.setDepartment(department);
            if (Long.parseLong(advanceRequest) == 1) {
                HousingAllowance.setAdvanceRequest(advanceRequest + " Month");
            } else {
                HousingAllowance.setAdvanceRequest(advanceRequest + " Months");
            }

            HousingAllowance.setStaffNumber(staffNo);

            submitForm(serviceDO.serviceType, HousingAllowance);
        }
    }
    /**************************************** House Allowance Form end **************************************************/

    /**************************************** Leave Joining Form start **************************************************/

    @Override
    public void submitLeaveJoining(String reason, String startDate, String document) {
        if (StringUtils.isEmpty(startDate)) {
            iFormView.showAlert(iFormView.EMPTY_DATE);
        } else {

            LeaveJoiningDO LeaveJoining = new LeaveJoiningDO();
            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
            LeaveJoining.setResumedWork(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(startDate,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

            LeaveJoining.setApproved_NotApproved("Not approved");
            LeaveJoining.setReasoniflatewithsupportingdocuments(document);
            // LeaveJoining.setSupportingDocuments(document);
            submitForm(serviceDO.serviceType, LeaveJoining);
        }
    }

    /**************************************** Leave Joining Form end **************************************************/

    /**************************************** Leave Application Form Form start **************************************************/

    @Override
    public void submitLeaveApplicationForm(String reason, String typeOfLeave, String startDate,
                                           String endDate, String salaryAdvance, String contact_number,
                                           String doYouWant_leave_salry_advance, int MaxPos, String resonForEmergency) {

        String[] s1 = salaryAdvance.split(" ");
        String strBand = preference.getStringFromPreference(Preference.BAND, "");

        if (StringUtils.isEmpty(typeOfLeave)) {
            iFormView.showAlert(iFormView.EMPTY_TYPE_LEAVE);
        } else if (typeOfLeave.equalsIgnoreCase("Emergency Leave") && StringUtils.isEmpty(resonForEmergency)) {
            iFormView.showAlert(iFormView.EMPTY_EMERGENCY);
        } else if (StringUtils.isEmpty(startDate)) {
            iFormView.showAlert(iFormView.START_DATE);
        } else if (StringUtils.isEmpty(endDate)) {
            iFormView.showAlert(iFormView.END_DATE);
        } else if (StringUtils.isEmpty(salaryAdvance)) {
            iFormView.showAlert(iFormView.SELEC_SALARY_ADVANCE);
        } else if (!s1[0].equalsIgnoreCase("") && Integer.parseInt(s1[0]) == 0) {
            iFormView.showAlert(iFormView.WEEKEND);
        }else if (StringUtils.isEmpty(doYouWant_leave_salry_advance)){
            iFormView.showAlert(iFormView.DOYOUWANT_LEAVE_SALRY_ADVANCE);
        } else if (StringUtils.isEmpty(contact_number)) {
            iFormView.showAlert(iFormView.EMPTY_MOBILE);
        } else if ((StringUtils.isEmpty(doYouWant_leave_salry_advance)) && ((strBand.equalsIgnoreCase("B3")) ||
                (strBand.equalsIgnoreCase("B4")) || (strBand.equalsIgnoreCase("B5")) ||
                (strBand.equalsIgnoreCase("B6")))) {
            iFormView.showAlert(iFormView.DOYOUWANT_LEAVE_SALRY_ADVANCE);
        }
        else if ((strBand.equalsIgnoreCase("B7")
                || strBand.equalsIgnoreCase("B8") ||
                strBand.equalsIgnoreCase("B9"))
                && (doYouWant_leave_salry_advance.equalsIgnoreCase("Yes"))
                && !s1[0].equalsIgnoreCase("") && Integer.parseInt(s1[0]) < 15) {
            iFormView.showAlert(iFormView.ADVANCE_SALARY);
        } else if (typeOfLeave.contains("Sick Leave") && !s1[0].equalsIgnoreCase("") && Integer.parseInt(s1[0]) > 1 && MaxPos < 1) {
            iFormView.showAlert(iFormView.ATTACHMENT);
        } else {


            LeaveApplicationDO AnnualLeave = new LeaveApplicationDO();
            AnnualLeave.setTypeofLeave(typeOfLeave);
//            AnnualLeave.setLeaveStartDate(startDate);
//            AnnualLeave.setLeaveEndDate(endDate);

            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern

            AnnualLeave.setLeaveStartDate(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(startDate,CalendarUtil.FULL_DATE_FORMATE,CalendarUtil.DD_MMM_YYYY_PATTERN));
            AnnualLeave.setLeaveEndDate(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(endDate,CalendarUtil.FULL_DATE_FORMATE,CalendarUtil.DD_MMM_YYYY_PATTERN));

            AnnualLeave.setTotalLeaves(salaryAdvance);
            AnnualLeave.setContactNo(contact_number);
            AnnualLeave.setDoyouWantLeaveSalary(doYouWant_leave_salry_advance);
            AnnualLeave.setReasonEmergency(resonForEmergency);

            submitForm(serviceDO.serviceType, AnnualLeave);
        }
    }

    /**************************************** Leave Application Form Form end **************************************************/

    /*************************** Passport Release start ************************************************/
    @Override
    public void PassportRelease(String purposeOfRelease, String startDate, String endDate, String etOther) {

        if (StringUtils.isEmpty(purposeOfRelease)) {
            iFormView.showAlert(iFormView.PURPOSE_OF_RELEASE);
        } else if (purposeOfRelease.equalsIgnoreCase("Others") && StringUtils.isEmpty(etOther)) {
            iFormView.showAlert(iFormView.PURPOSE_OF_RELEASE_OTHERS);
        } else if (StringUtils.isEmpty(startDate)) {
            iFormView.showAlert(iFormView.START_DATE);
        } else if (StringUtils.isEmpty(endDate)) {
            iFormView.showAlert(iFormView.END_DATE);
        } else {
            PassportReleaseDO PassportRelease = new PassportReleaseDO();
            if (purposeOfRelease.equalsIgnoreCase("Others")) {
                PassportRelease.setPurposeofpassportrelease(etOther);
            } else {
                PassportRelease.setPurposeofpassportrelease(purposeOfRelease);
            }
            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
            PassportRelease.setDateborrowed(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(startDate,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));
            PassportRelease.setDatetobereturned(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(endDate,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

            submitForm(serviceDO.serviceType, PassportRelease);
        }
    }
    /*************************** Passport Release end ************************************************/


    /***************************** Salary Certificate Request start*************************************************/
    @Override
    public void SalaryCertificateRequest(String address, String purpose, String otherInstructions) {
        if (StringUtils.isEmpty(address)) {
            iFormView.showAlert(IFormView.ADDRESSEDTO);
        } else if (StringUtils.isEmpty(purpose)) {
            iFormView.showAlert(IFormView.PURPOSE);
        } else {
            SalaryCertificateRequestDO SalaryCertificateRequest = new SalaryCertificateRequestDO();
            SalaryCertificateRequest.setAddressedto(address);
            SalaryCertificateRequest.setPurpose(purpose);
            SalaryCertificateRequest.setOtherInstructions(otherInstructions);

            submitForm(serviceDO.serviceType, SalaryCertificateRequest);
        }
    }

    /***************************** Salary Certificate Request end*************************************************/

    /***************************** Salary Transfer Letter Request start*************************************************/

    @Override
    public void SalaryTransferLetterRequest(String currentBankName, String currentIBANno, String salaryTransferToSameBank, String bankName
            , String IBANno, String haveExistingLoans, String loanBankName, String totalLoanTaken, String tvLoanAppliedOn, String outStanding_asOfday
            , String lastInstal_due, String monthlyInstallment, String purposeOfLoan, String bankDetailsName, String totalLoanAmt
            , String monthlyInstal_50, String haveCreditCard, String ccBankName, String ccOutStandingAmt, String confirm, String approval, String request, String accountNumber) {

        if (StringUtils.isEmpty(salaryTransferToSameBank)) {
            iFormView.showAlert(iFormView.SALARY_TRANS_TO_SAME_BANK);
        } else if (salaryTransferToSameBank.equalsIgnoreCase("No")) {
            if (StringUtils.isEmpty(bankName)) {
                iFormView.showAlert(iFormView.BANK_NAME);
            } else if (StringUtils.isEmpty(accountNumber)) {
                iFormView.showAlert(IFormView.ENTER_ACCOUNT_NO);
            } else if (StringUtils.isEmpty(IBANno) && !(preference.getStringFromPreference(Preference.STAFF_WORK_COUNTRY, "N/A").equalsIgnoreCase("Oman"))) {
                iFormView.showAlert(IFormView.IBAN_NO);
            } else if (!StringUtils.isEmpty(IBANno) && !(IBANno.trim().length() == 23)) {
                iFormView.showAlert(IFormView.ENTER_23DIGITS_IBAN);
            } else if (StringUtils.isEmpty(haveExistingLoans)) {
                iFormView.showAlert(iFormView.HAVE_EXISTING_LOANS);
            } else if (haveExistingLoans.equalsIgnoreCase("Yes")) {
                if (StringUtils.isEmpty(loanBankName)) {
                    iFormView.showAlert(iFormView.LOAN_BANK_NAME);
                } else if (StringUtils.isEmpty(totalLoanTaken)) {
                    iFormView.showAlert(iFormView.TOTAL_LOAN_TAKEN);
                } else if (StringUtils.isEmpty(tvLoanAppliedOn)) {
                    iFormView.showAlert(iFormView.LOAN_APPLIED_ON);
                } else if (StringUtils.isEmpty(outStanding_asOfday)) {
                    iFormView.showAlert(iFormView.OUTSTANDING_ASOFDAY);
                } else if (StringUtils.isEmpty(lastInstal_due)) {
                    iFormView.showAlert(iFormView.LAST_INSTAL_DUE);
                } else if (StringUtils.isEmpty(monthlyInstallment)) {
                    iFormView.showAlert(iFormView.MONTHLY_INSTAL);
                } else if (StringUtils.isEmpty(haveCreditCard)) {
                    iFormView.showAlert(iFormView.HAVE_CREDIT_CARD);
                } else if (haveCreditCard.equalsIgnoreCase("Yes")) {
                    if (StringUtils.isEmpty(ccBankName)) {
                        iFormView.showAlert(iFormView.CC_BANK_NAME);
                    } else if (StringUtils.isEmpty(ccOutStandingAmt)) {
                        iFormView.showAlert(iFormView.CREDIT_LIMIT);
                    } else if (StringUtils.isEmpty(purposeOfLoan)) {
                        iFormView.showAlert(iFormView.PURPOSE_OF_LOAN);
                    } else if (StringUtils.isEmpty(bankDetailsName)) {
                        iFormView.showAlert(iFormView.BANK_NAME);
                    } else if (StringUtils.isEmpty(totalLoanAmt)) {
                        iFormView.showAlert(iFormView.TOTAL_LOAN_AMT_REQ);
                    } else if (StringUtils.isEmpty(monthlyInstal_50)) {
                        iFormView.showAlert(iFormView.MONTHLY_INSTAL_50);
                    } else if (StringUtils.isEmpty(confirm)) {
                        iFormView.showAlert(iFormView.DECLA_CONFIRM);
                    } else if (StringUtils.isEmpty(approval)) {
                        iFormView.showAlert(iFormView.DECLA_APPROVAL);
                    } else if (StringUtils.isEmpty(request)) {
                        iFormView.showAlert(iFormView.DECLA_REQUEST);
                    } else {
                        moveToSalaryTransferLetterRequest(currentBankName, currentIBANno, salaryTransferToSameBank, bankName, IBANno, haveExistingLoans, loanBankName
                                , totalLoanTaken, tvLoanAppliedOn, outStanding_asOfday, lastInstal_due, monthlyInstallment, purposeOfLoan, bankDetailsName
                                , totalLoanAmt, monthlyInstal_50, haveCreditCard, ccBankName, ccOutStandingAmt, confirm, approval, request, accountNumber);
                    }
                } else {

                    checkDetailsOfProposedBankLoan(currentBankName, currentIBANno, salaryTransferToSameBank, bankName, IBANno, haveExistingLoans, loanBankName
                            , totalLoanTaken, tvLoanAppliedOn, outStanding_asOfday, lastInstal_due, monthlyInstallment, purposeOfLoan, bankDetailsName
                            , totalLoanAmt, monthlyInstal_50, haveCreditCard, ccBankName, ccOutStandingAmt, confirm, approval, request, accountNumber);
                }
            } else {
                checkDetailsOfProposedBankLoan(currentBankName, currentIBANno, salaryTransferToSameBank, bankName, IBANno, haveExistingLoans, loanBankName
                        , totalLoanTaken, tvLoanAppliedOn, outStanding_asOfday, lastInstal_due, monthlyInstallment, purposeOfLoan, bankDetailsName
                        , totalLoanAmt, monthlyInstal_50, haveCreditCard, ccBankName, ccOutStandingAmt, confirm, approval, request, accountNumber);
            }
        } else {
            if (StringUtils.isEmpty(haveExistingLoans)) {
                iFormView.showAlert(iFormView.HAVE_EXISTING_LOANS);
            } else if (haveExistingLoans.equalsIgnoreCase("Yes")) {
                if (StringUtils.isEmpty(loanBankName)) {
                    iFormView.showAlert(iFormView.LOAN_BANK_NAME);
                } else if (StringUtils.isEmpty(totalLoanTaken)) {
                    iFormView.showAlert(iFormView.TOTAL_LOAN_TAKEN);
                } else if (StringUtils.isEmpty(tvLoanAppliedOn)) {
                    iFormView.showAlert(iFormView.LOAN_APPLIED_ON);
                } else if (StringUtils.isEmpty(outStanding_asOfday)) {
                    iFormView.showAlert(iFormView.OUTSTANDING_ASOFDAY);
                } else if (StringUtils.isEmpty(lastInstal_due)) {
                    iFormView.showAlert(iFormView.LAST_INSTAL_DUE);
                } else if (StringUtils.isEmpty(monthlyInstallment)) {
                    iFormView.showAlert(iFormView.MONTHLY_INSTAL);
                } else {

                    checkDetailsOfProposedBankLoan(currentBankName, currentIBANno, salaryTransferToSameBank, bankName, IBANno, haveExistingLoans, loanBankName
                            , totalLoanTaken, tvLoanAppliedOn, outStanding_asOfday, lastInstal_due, monthlyInstallment, purposeOfLoan, bankDetailsName
                            , totalLoanAmt, monthlyInstal_50, haveCreditCard, ccBankName, ccOutStandingAmt, confirm, approval, request, accountNumber);

                }
            } else {

                checkDetailsOfProposedBankLoan(currentBankName, currentIBANno, salaryTransferToSameBank, bankName, IBANno, haveExistingLoans, loanBankName
                        , totalLoanTaken, tvLoanAppliedOn, outStanding_asOfday, lastInstal_due, monthlyInstallment, purposeOfLoan, bankDetailsName
                        , totalLoanAmt, monthlyInstal_50, haveCreditCard, ccBankName, ccOutStandingAmt, confirm, approval, request, accountNumber);
            }
        }
    }

    public void checkDetailsOfProposedBankLoan(String currentBankName, String currentIBANno, String salaryTransferToSameBank, String bankName
            , String IBANno, String haveExistingLoans, String loanBankName, String totalLoanTaken, String tvLoanAppliedOn, String outStanding_asOfday
            , String lastInstal_due, String monthlyInstallment, String purposeOfLoan, String bankDetailsName, String totalLoanAmt
            , String monthlyInstal_50, String haveCreditCard, String ccBankName, String ccOutStandingAmt, String confirm, String approval, String request, String accountNumber)
    {

        if (StringUtils.isEmpty(purposeOfLoan)) {
            iFormView.showAlert(iFormView.PURPOSE_OF_LOAN);
        } else if (StringUtils.isEmpty(bankDetailsName)) {
            iFormView.showAlert(iFormView.BANK_NAME);
        } else if (StringUtils.isEmpty(totalLoanAmt)) {
            iFormView.showAlert(iFormView.TOTAL_LOAN_AMT_REQ);
        } else if (StringUtils.isEmpty(monthlyInstal_50)) {
            iFormView.showAlert(iFormView.MONTHLY_INSTAL_50);
        } else if (StringUtils.isEmpty(haveCreditCard)) {
            iFormView.showAlert(iFormView.HAVE_CREDIT_CARD);
        } else if (haveCreditCard.equalsIgnoreCase("Yes")) {
            if (StringUtils.isEmpty(ccBankName)) {
                iFormView.showAlert(iFormView.CC_BANK_NAME);
            } else if (StringUtils.isEmpty(ccOutStandingAmt)) {
                iFormView.showAlert(iFormView.CREDIT_LIMIT);
            }
            else if (StringUtils.isEmpty(confirm)) {
                iFormView.showAlert(iFormView.DECLA_CONFIRM);
            } else if (StringUtils.isEmpty(approval)) {
                iFormView.showAlert(iFormView.DECLA_APPROVAL);
            } else if (StringUtils.isEmpty(request)) {
                iFormView.showAlert(iFormView.DECLA_REQUEST);
            }else {
                moveToSalaryTransferLetterRequest(currentBankName, currentIBANno, salaryTransferToSameBank, bankName, IBANno, haveExistingLoans, loanBankName
                        , totalLoanTaken, tvLoanAppliedOn, outStanding_asOfday, lastInstal_due, monthlyInstallment, purposeOfLoan, bankDetailsName
                        , totalLoanAmt, monthlyInstal_50, haveCreditCard, ccBankName, ccOutStandingAmt, confirm, approval, request, accountNumber);
            }

        }else {

            if (StringUtils.isEmpty(confirm)) {
                iFormView.showAlert(iFormView.DECLA_CONFIRM);
            } else if (StringUtils.isEmpty(approval)) {
                iFormView.showAlert(iFormView.DECLA_APPROVAL);
            } else if (StringUtils.isEmpty(request)) {
                iFormView.showAlert(iFormView.DECLA_REQUEST);
            }else {
                moveToSalaryTransferLetterRequest(currentBankName, currentIBANno, salaryTransferToSameBank, bankName, IBANno, haveExistingLoans, loanBankName
                        , totalLoanTaken, tvLoanAppliedOn, outStanding_asOfday, lastInstal_due, monthlyInstallment, purposeOfLoan, bankDetailsName
                        , totalLoanAmt, monthlyInstal_50, haveCreditCard, ccBankName, ccOutStandingAmt, confirm, approval, request, accountNumber);
            }
        }
    }



    public void moveToSalaryTransferLetterRequest(String currentBankName, String currentIBANno, String salaryTransferToSameBank, String bankName
            , String IBANno, String haveExistingLoans, String loanBankName, String totalLoanTaken, String tvLoanAppliedOn, String outStanding_asOfday
            , String lastInstal_due, String monthlyInstallment, String purposeOfLoan, String bankDetailsName, String totalLoanAmt
            , String monthlyInstal_50, String haveCreditCard, String ccBankName, String ccOutStandingAmt, String confirm, String approval, String request, String accountNumber) {


        SalaryTransferLetterRequestDO SalaryTransferLetterRequest = new SalaryTransferLetterRequestDO();
        SalaryTransferLetterRequest.setCurrentsalaryAccountBankName(currentBankName);
        SalaryTransferLetterRequest.setCurrentsalaryIBAN(currentIBANno);
        SalaryTransferLetterRequest.setSalaryTransfersameBank(salaryTransferToSameBank);
        SalaryTransferLetterRequest.setBankName(bankName);
        SalaryTransferLetterRequest.setIBANNumber(IBANno);
        SalaryTransferLetterRequest.setDoyouhaveanyexistingBankloans(haveExistingLoans);
        SalaryTransferLetterRequest.setExistingBankName(loanBankName);
        SalaryTransferLetterRequest.setTotalLoanTaken(totalLoanTaken);
        //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
        SalaryTransferLetterRequest.setLoanAppliedon(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(tvLoanAppliedOn,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

        SalaryTransferLetterRequest.setOutstandingasofDate(outStanding_asOfday);
        //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
        SalaryTransferLetterRequest.setLastinstallmentdue(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(lastInstal_due,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

        SalaryTransferLetterRequest.setMonthlyinstallment(monthlyInstallment);
        SalaryTransferLetterRequest.setPurposeofLoan(purposeOfLoan);
        SalaryTransferLetterRequest.setProposedBankName(bankDetailsName);
        SalaryTransferLetterRequest.setTotalLoanAmreq(totalLoanAmt);
        SalaryTransferLetterRequest.setMonthlyInst_50(monthlyInstal_50);
//        SalaryTransferLetterRequest.setMonthlyInst_50(haveCreditCard);
        SalaryTransferLetterRequest.setCreditBankName(ccBankName);
        SalaryTransferLetterRequest.setOutstandingAmount(ccOutStandingAmt);
        SalaryTransferLetterRequest.setDec_confirm(confirm);
        SalaryTransferLetterRequest.setDec_approval(approval);
        SalaryTransferLetterRequest.setDec_request(request);
        SalaryTransferLetterRequest.setAccountNumber(accountNumber);
        submitForm(serviceDO.serviceType, SalaryTransferLetterRequest);
    }

    /***************************** Salary Transfer Letter Request end ************************************/


    /*********************************** System Access Request start *************************************************************/

    @Override
    public void SystemAccess(String StaffNo, String Department, String Designation, String sap, String network, String email, String internet, String extension, String remarks) {
        if (StringUtils.isEmpty(sap) && StringUtils.isEmpty(network) && StringUtils.isEmpty(email) && StringUtils.isEmpty(internet) && StringUtils.isEmpty(extension)) {
            iFormView.showAlert(iFormView.SAP);
        } else {
            SystemAccessDO SystemAccess = new SystemAccessDO();
            SystemAccess.setStaffNo(StaffNo);
            SystemAccess.setDepartment(Department);
            SystemAccess.setDesignation(Designation);
            SystemAccess.setSAP(sap);
            SystemAccess.setSystemNetwork(network);
            SystemAccess.setEmail(email);
            SystemAccess.setInternet(internet);
            SystemAccess.setTelephoneExtension(extension);
            SystemAccess.setRemarks(remarks);

            submitForm(serviceDO.serviceType, SystemAccess);
        }
    }

    /*********************************** System Access Request end *************************************************************/

    /*********************************** Transport Loan start *************************************************************/
    @Override
    public void TransportLoan(final String isUAEDri_license, final String staffNo, final String name, final String department, final String designation
            , final String brandNeworPre, final String VehicleMake, final String VehicleModel, final String OdometerRead, final String ChassisNo
            , String NewVehicleMake, String NewVehicleModel, String NewChassisNo, final String amount, final String etEngineNo, String etNewEngineNo, int MaxPos) {

        String band = preference.getStringFromPreference(Preference.BAND, "N/A");
        String confirmation = preference.getStringFromPreference(CONFIRMATION, "");
        String transportLoan = preference.getStringFromPreference(Preference.TRANSPORT_ALLOWANCE, "No");

        if (!transportLoan.equalsIgnoreCase("Yes")) {
            iFormView.showAlert(iFormView.TRANSPORT_LOAN_NOT_ELIGIBLE);
        }
        else if ((band.equalsIgnoreCase("B8") || band.equalsIgnoreCase("B9")) &&
                !(confirmation.equalsIgnoreCase("z0") || confirmation.equalsIgnoreCase("z9"))) {
            iFormView.showAlert(iFormView.TRANSPORT_LOAN);
        }
        else if (StringUtils.isEmpty(isUAEDri_license)) {
            iFormView.showAlert(iFormView.UAE_DRI_LICENSE);
        }
        else if (isUAEDri_license.equalsIgnoreCase("No")) {
            iFormView.showAlert(iFormView.DRI_LICENSE);
        }
        else if (StringUtils.isEmpty(amount)) {
            iFormView.showAlert(iFormView.AMOUNT);
        }
       /* else if (StringUtils.isEmpty(staffNo)) {
            iFormView.showAlert(iFormView.STAFF_NO);
        } else if (StringUtils.isEmpty(name)) {
            iFormView.showAlert(iFormView.STAFF_NAME);
        } else if (StringUtils.isEmpty(department)) {
            iFormView.showAlert(iFormView.DEPARTMENT);
        } else if (StringUtils.isEmpty(designation)) {
            iFormView.showAlert(iFormView.DESIGNATION);
        } */
        else if (StringUtils.isEmpty(brandNeworPre)) {
            iFormView.showAlert(iFormView.BRAND_NEW_OR_PRE);
        } else if (brandNeworPre.equalsIgnoreCase("New")) {

            if (StringUtils.isEmpty(NewVehicleMake)) {
                iFormView.showAlert(iFormView.NEW_VEHICLE_MAKE);
            } else if (StringUtils.isEmpty(NewVehicleModel)) {
                iFormView.showAlert(iFormView.NEW_VEHICLE_MODEL);
            } else if (StringUtils.isEmpty(NewChassisNo)) {
                iFormView.showAlert(iFormView.NEW_CHASSIS_NO);
            } else if (StringUtils.isEmpty(etNewEngineNo)) {
                iFormView.showAlert(iFormView.ENGINE_NO);
            } else if (MaxPos <1){
                iFormView.showAlert(iFormView.ATTACHMENT_TRANSP_NEW);
            }
            else {
                moveToTransportLoan(isUAEDri_license, staffNo, name, department, designation
                        , brandNeworPre, NewVehicleMake, NewVehicleModel, NewChassisNo, amount, etNewEngineNo);
            }
        } else if (brandNeworPre.equalsIgnoreCase("Pre-Owned")) {

            if (StringUtils.isEmpty(VehicleMake)) {
                iFormView.showAlert(iFormView.VEHICLE_MAKE);
            } else if (StringUtils.isEmpty(VehicleModel)) {
                iFormView.showAlert(iFormView.VEHICLE_MODEL);
            } else if (!StringUtils.isEmpty(VehicleModel)) {
                int diffYear = getDifferenceOfYear(VehicleModel);
                if (diffYear >= 5) {
                    iFormView.showAlert(iFormView.VEHICLE_MODEL_);
                }
                else if (StringUtils.isEmpty(OdometerRead))
                {
                    iFormView.showAlert(iFormView.ODOMETER_READ);
                } else if (!StringUtils.isEmpty(OdometerRead))
                {
                    long diffOdo = Long.parseLong(OdometerRead);
                    if (diffOdo > 100000) {
                        iFormView.showAlert(iFormView.ODOMETER_READ_);
                    }
                    else if (StringUtils.isEmpty(ChassisNo)) {
                        iFormView.showAlert(iFormView.CHASSIS_NO);
                    }
                    else if (StringUtils.isEmpty(etEngineNo)) {
                        iFormView.showAlert(iFormView.ENGINE_NO);
                    }
                    else if (! (MaxPos >= 1)) {

                        iFormView.showAlert(iFormView.ATTACHMENT_TRANSP);
                    }
                     else {
                        ((BaseActivity) mcontext).hideLoader();
                        ((BaseActivity) mcontext).showAttachmentsPopup(context.getString(R.string.alert),context.getString(R.string.transportLoan_attach_popup2));
                        if (((BaseActivity) mcontext).Ok != null) {

                            ((BaseActivity) mcontext).Ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ((BaseActivity)mcontext).attachmentsDialog.dismiss();

                                    moveToTransportLoan(isUAEDri_license, staffNo, name, department, designation
                                            , brandNeworPre, VehicleMake, VehicleModel, OdometerRead, ChassisNo, amount, etEngineNo);

                                }
                            });
                        }
                    }
                }
            }
        }
    }

    public void moveToTransportLoan(String isUAEDri_license, String staffNo, String name, String department, String designation
            , String brandNeworPre, String NewVehicleMake, String NewVehicleModel, String NewChassisNo, String amount, String etNewEngineNo) {

        TransportLoanDO TransportLoan = new TransportLoanDO();
        TransportLoan.setIsUAEDri_License(isUAEDri_license);
        TransportLoan.setStaffNo(staffNo);
        TransportLoan.setName(name);
        TransportLoan.setDepartment(department);
        TransportLoan.setDesignation(designation);
        TransportLoan.setBrandNeworPre(brandNeworPre);
        TransportLoan.setVehicleMake(NewVehicleMake);
        TransportLoan.setVehicleModel(NewVehicleModel);
        TransportLoan.setChassisNo(NewChassisNo);
        TransportLoan.setLoanamount(amount);
        TransportLoan.setEnginenumber(etNewEngineNo);

        submitForm(serviceDO.serviceType, TransportLoan);
    }

    public void moveToTransportLoan(String isUAEDri_license, String staffNo, String name, String department, String designation
            , String brandNeworPre, String VehicleMake, String VehicleModel, String OdometerRead, String ChassisNo, String amount, String etEngineNo) {

        TransportLoanDO TransportLoan = new TransportLoanDO();
        TransportLoan.setIsUAEDri_License(isUAEDri_license);
        TransportLoan.setStaffNo(staffNo);
        TransportLoan.setName(name);
        TransportLoan.setDepartment(department);
        TransportLoan.setDesignation(designation);
        TransportLoan.setBrandNeworPre(brandNeworPre);
        TransportLoan.setVehicleMake(VehicleMake);
        TransportLoan.setVehicleModel(VehicleModel);
        TransportLoan.setOdometerRead(OdometerRead);
        TransportLoan.setChassisNo(ChassisNo);
        TransportLoan.setLoanamount(amount);
        TransportLoan.setEnginenumber(etEngineNo);

        submitForm(serviceDO.serviceType, TransportLoan);
    }
    /*********************************** Transport Loan end *************************************************************/


    /**************************************** Visa Spouse Children Form start **************************************************/
    @Override
    public void VisaSpouseChildren(String name, String FatherName, String MotherName, String Nationality, String DateOfBirth
            , String PlaceOfBirth, String PassportNo, String PlaceOfIssue, String DateOfIssue, String DateOfExpiry, String Profession
            , String DateOfLastEntry, String AddrAbroad, String R_ShipWithApplicant, String anyBankLoan, String anyHRAdvance
            , String anyTransportAdvance, String creditCards) {
        if (StringUtils.isEmpty(name)) {
            iFormView.showAlert(iFormView.NAME_AS_PASSPORT);
        } else if (StringUtils.isEmpty(FatherName)) {
            iFormView.showAlert(iFormView.FATHER_NAME);
        } else if (StringUtils.isEmpty(MotherName)) {
            iFormView.showAlert(iFormView.MOTHER_NAME);
        } else if (StringUtils.isEmpty(Nationality)) {
            iFormView.showAlert(iFormView.NATIONALITY);
        } else if (StringUtils.isEmpty(DateOfBirth)) {
            iFormView.showAlert(iFormView.DATE_OF_BIRTH);
        } else if (StringUtils.isEmpty(PlaceOfBirth)) {
            iFormView.showAlert(iFormView.PLACE_OF_BIRTH);
        } else if (StringUtils.isEmpty(PassportNo)) {
            iFormView.showAlert(iFormView.PASSPORT_NO);
        } else if (StringUtils.isEmpty(PlaceOfIssue)) {
            iFormView.showAlert(iFormView.PLACE_OF_ISSUE);
        } else if (StringUtils.isEmpty(DateOfIssue)) {
            iFormView.showAlert(iFormView.DATE_OF_ISSUE);
        } else if (StringUtils.isEmpty(DateOfExpiry)) {
            iFormView.showAlert(iFormView.DATE_OF_EXPIRY);
        } else if (StringUtils.isEmpty(DateOfLastEntry)) {
            iFormView.showAlert(iFormView.DATE_OF_LAST_ENTRY);
        } else if (StringUtils.isEmpty(AddrAbroad)) {
            iFormView.showAlert(iFormView.ADDR_ABROAD);
        } else if (StringUtils.isEmpty(R_ShipWithApplicant)) {
            iFormView.showAlert(iFormView.R_SHIP_WITH_APPLI);
        } else if (StringUtils.isEmpty(anyBankLoan)) {
            iFormView.showAlert(iFormView.ANY_BANK_LOAN);
        } else if (StringUtils.isEmpty(anyHRAdvance)) {
            iFormView.showAlert(iFormView.ANY_HR_ADVANCE);
        } else if (StringUtils.isEmpty(anyTransportAdvance)) {
            iFormView.showAlert(iFormView.ANY_TRANS_ADVANCE);
        } else if (StringUtils.isEmpty(creditCards)) {
            iFormView.showAlert(iFormView.CREDIT_CARDS);
        } else {
            VisaSpouseChildrenDO VisaSpouseChildren = new VisaSpouseChildrenDO();
            VisaSpouseChildren.setPassportName(name);
            VisaSpouseChildren.setFathername(FatherName);
            VisaSpouseChildren.setMothername(MotherName);
            VisaSpouseChildren.setNationality(Nationality);

            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
            VisaSpouseChildren.setDOB(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfBirth,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

            VisaSpouseChildren.setPlaceofBirth(PlaceOfBirth);
            VisaSpouseChildren.setPPNumber(PassportNo);
            VisaSpouseChildren.setPlaceofIssue(PlaceOfIssue);
            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
            VisaSpouseChildren.setDateofIssue(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfIssue,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));
            VisaSpouseChildren.setDateofExpiry(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfExpiry,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

            VisaSpouseChildren.setProfessionpassport(Profession);
            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
            VisaSpouseChildren.setDatelastentryOman(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfLastEntry,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

            VisaSpouseChildren.setAddrAbroad(AddrAbroad);
            VisaSpouseChildren.setR_shipwithapplicant(R_ShipWithApplicant);
            VisaSpouseChildren.setAnyBankLoan(anyBankLoan);
            VisaSpouseChildren.setAnyHRAdvance(anyHRAdvance);
            VisaSpouseChildren.setAnyTransportAdvance(anyTransportAdvance);
            VisaSpouseChildren.setCreditCards(creditCards);

            submitForm(serviceDO.serviceType, VisaSpouseChildren);
        }
    }

    /**************************************** Visa Spouse Children Form end **************************************************/

    /**************************************** Visa In-Laws Children Form start **************************************************/

    @Override
    public void VisaInLawsChildren(String name, String FatherName, String MotherName, String Nationality, String DateOfBirth
            , String PlaceOfBirth, String PassportNo, String PlaceOfIssue, String DateOfIssue, String DateOfExpiry, String Profession
            , String DateOfLastEntry, String AddrAbroad, String R_ShipWithApplicant, String Reasonrequest, String anyBankLoan, String anyHRAdvance
            , String anyTransportAdvance, String creditCards) {
        if (StringUtils.isEmpty(name)) {
            iFormView.showAlert(iFormView.NAME_AS_PASSPORT);
        } else if (StringUtils.isEmpty(FatherName)) {
            iFormView.showAlert(iFormView.FATHER_NAME);
        } else if (StringUtils.isEmpty(MotherName)) {
            iFormView.showAlert(iFormView.MOTHER_NAME);
        } else if (StringUtils.isEmpty(Nationality)) {
            iFormView.showAlert(iFormView.NATIONALITY);
        } else if (StringUtils.isEmpty(DateOfBirth)) {
            iFormView.showAlert(iFormView.DATE_OF_BIRTH);
        } else if (StringUtils.isEmpty(PlaceOfBirth)) {
            iFormView.showAlert(iFormView.PLACE_OF_BIRTH);
        } else if (StringUtils.isEmpty(PassportNo)) {
            iFormView.showAlert(iFormView.PASSPORT_NO);
        } else if (StringUtils.isEmpty(PlaceOfIssue)) {
            iFormView.showAlert(iFormView.PLACE_OF_ISSUE);
        } else if (StringUtils.isEmpty(DateOfIssue)) {
            iFormView.showAlert(iFormView.DATE_OF_ISSUE);
        } else if (StringUtils.isEmpty(DateOfExpiry)) {
            iFormView.showAlert(iFormView.DATE_OF_EXPIRY);
        } else if (StringUtils.isEmpty(DateOfLastEntry)) {
            iFormView.showAlert(iFormView.DATE_OF_LAST_ENTRY);
        } else if (StringUtils.isEmpty(AddrAbroad)) {
            iFormView.showAlert(iFormView.ADDR_ABROAD);
        } else if (StringUtils.isEmpty(R_ShipWithApplicant)) {
            iFormView.showAlert(iFormView.R_SHIP_WITH_APPLI);
        } else if (StringUtils.isEmpty(Reasonrequest)) {
            iFormView.showAlert(iFormView.REASON_REQUEST);
        } else if (StringUtils.isEmpty(anyBankLoan)) {
            iFormView.showAlert(iFormView.ANY_BANK_LOAN);
        } else if (StringUtils.isEmpty(anyHRAdvance)) {
            iFormView.showAlert(iFormView.ANY_HR_ADVANCE);
        } else if (StringUtils.isEmpty(anyTransportAdvance)) {
            iFormView.showAlert(iFormView.ANY_TRANS_ADVANCE);
        } else if (StringUtils.isEmpty(creditCards)) {
            iFormView.showAlert(iFormView.CREDIT_CARDS);
        } else {
            VisaSpouseChildrenDO VisaInLawsChildren = new VisaSpouseChildrenDO();
            VisaInLawsChildren.setPassportName(name);
            VisaInLawsChildren.setFathername(FatherName);
            VisaInLawsChildren.setMothername(MotherName);
            VisaInLawsChildren.setNationality(Nationality);
            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
            VisaInLawsChildren.setDOB(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfBirth,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

            VisaInLawsChildren.setPlaceofBirth(PlaceOfBirth);
            VisaInLawsChildren.setPPNumber(PassportNo);
            VisaInLawsChildren.setPlaceofIssue(PlaceOfIssue);
            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
            VisaInLawsChildren.setDateofIssue(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfIssue,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));
            VisaInLawsChildren.setDateofExpiry(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfExpiry,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

            VisaInLawsChildren.setProfessionpassport(Profession);
            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
            VisaInLawsChildren.setDatelastentryOman(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfLastEntry,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

            VisaInLawsChildren.setAddrAbroad(AddrAbroad);
            VisaInLawsChildren.setR_shipwithapplicant(R_ShipWithApplicant);
            VisaInLawsChildren.setReasonrequest(Reasonrequest);
            VisaInLawsChildren.setAnyBankLoan(anyBankLoan);
            VisaInLawsChildren.setAnyHRAdvance(anyHRAdvance);
            VisaInLawsChildren.setAnyTransportAdvance(anyTransportAdvance);
            VisaInLawsChildren.setCreditCards(creditCards);

            submitForm(serviceDO.serviceType, VisaInLawsChildren);
        }
    }
    /**************************************** Visa In-Laws Children Form end **************************************************/

    /**************************************** Family Visa Spouse Children Form start **************************************************/

    @Override
    public void FamilyVisaSpouseChildren(String name, String FatherName, String MotherName, String Nationality, String DateOfBirth, String PlaceOfBirth, String PassportNo, String PlaceOfIssue, String DateOfIssue, String DateOfExpiry, String Profession, String DateOfLastEntry, String AddrAbroad, String R_ShipWithApplicant, String anyBankLoan, String anyHRAdvance, String anyTransportAdvance, String creditCards) {

        if (StringUtils.isEmpty(name)) {
            iFormView.showAlert(iFormView.NAME_AS_PASSPORT);
        } else if (StringUtils.isEmpty(FatherName)) {
            iFormView.showAlert(iFormView.FATHER_NAME);
        } else if (StringUtils.isEmpty(MotherName)) {
            iFormView.showAlert(iFormView.MOTHER_NAME);
        } else if (StringUtils.isEmpty(Nationality)) {
            iFormView.showAlert(iFormView.NATIONALITY);
        } else if (StringUtils.isEmpty(DateOfBirth)) {
            iFormView.showAlert(iFormView.DATE_OF_BIRTH);
        } else if (StringUtils.isEmpty(PlaceOfBirth)) {
            iFormView.showAlert(iFormView.PLACE_OF_BIRTH);
        } else if (StringUtils.isEmpty(PassportNo)) {
            iFormView.showAlert(iFormView.PASSPORT_NO);
        } else if (StringUtils.isEmpty(PlaceOfIssue)) {
            iFormView.showAlert(iFormView.PLACE_OF_ISSUE);
        } else if (StringUtils.isEmpty(DateOfIssue)) {
            iFormView.showAlert(iFormView.DATE_OF_ISSUE);
        } else if (StringUtils.isEmpty(DateOfExpiry)) {
            iFormView.showAlert(iFormView.DATE_OF_EXPIRY);
        } else if (StringUtils.isEmpty(DateOfLastEntry)) {
            iFormView.showAlert(iFormView.DATE_OF_LAST_ENTRY);
        } else if (StringUtils.isEmpty(AddrAbroad)) {
            iFormView.showAlert(iFormView.ADDR_ABROAD);
        } else if (StringUtils.isEmpty(R_ShipWithApplicant)) {
            iFormView.showAlert(iFormView.R_SHIP_WITH_APPLI);
        } else if (StringUtils.isEmpty(anyBankLoan)) {
            iFormView.showAlert(iFormView.ANY_BANK_LOAN);
        } else if (StringUtils.isEmpty(anyHRAdvance)) {
            iFormView.showAlert(iFormView.ANY_HR_ADVANCE);
        } else if (StringUtils.isEmpty(anyTransportAdvance)) {
            iFormView.showAlert(iFormView.ANY_TRANS_ADVANCE);
        } else if (StringUtils.isEmpty(creditCards)) {
            iFormView.showAlert(iFormView.CREDIT_CARDS);
        } else {
            VisaSpouseChildrenDO FamilyVisaSpouseChildren = new VisaSpouseChildrenDO();
            FamilyVisaSpouseChildren.setPassportName(name);
            FamilyVisaSpouseChildren.setFathername(FatherName);
            FamilyVisaSpouseChildren.setMothername(MotherName);
            FamilyVisaSpouseChildren.setNationality(Nationality);
            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
            FamilyVisaSpouseChildren.setDOB(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfBirth,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

            FamilyVisaSpouseChildren.setPlaceofBirth(PlaceOfBirth);
            FamilyVisaSpouseChildren.setPPNumber(PassportNo);
            FamilyVisaSpouseChildren.setPlaceofIssue(PlaceOfIssue);
            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
            FamilyVisaSpouseChildren.setDateofIssue(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfIssue,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));
            FamilyVisaSpouseChildren.setDateofExpiry(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfExpiry,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

            FamilyVisaSpouseChildren.setProfessionpassport(Profession);
            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
            FamilyVisaSpouseChildren.setDatelastentryOman(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfLastEntry,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

            FamilyVisaSpouseChildren.setAddrAbroad(AddrAbroad);
            FamilyVisaSpouseChildren.setR_shipwithapplicant(R_ShipWithApplicant);
            FamilyVisaSpouseChildren.setAnyBankLoan(anyBankLoan);
            FamilyVisaSpouseChildren.setAnyHRAdvance(anyHRAdvance);
            FamilyVisaSpouseChildren.setAnyTransportAdvance(anyTransportAdvance);
            FamilyVisaSpouseChildren.setCreditCards(creditCards);

            submitForm(serviceDO.serviceType, FamilyVisaSpouseChildren);
        }
    }
    /**************************************** Family Visa Spouse Children Form end **************************************************/

    /**************************************** Family Visa Parents Form start **************************************************/

    @Override
    public void FamilyVisaParents(String name, String FatherName, String MotherName, String Nationality, String DateOfBirth
            , String PlaceOfBirth, String PassportNo, String PlaceOfIssue, String DateOfIssue, String DateOfExpiry, String Profession
            , String DateOfLastEntry, String AddrAbroad, String R_ShipWithApplicant, String Reasonrequest, String anyBankLoan, String anyHRAdvance
            , String anyTransportAdvance, String creditCards) {
        if (StringUtils.isEmpty(name)) {
            iFormView.showAlert(iFormView.NAME_AS_PASSPORT);
        } else if (StringUtils.isEmpty(FatherName)) {
            iFormView.showAlert(iFormView.FATHER_NAME);
        } else if (StringUtils.isEmpty(MotherName)) {
            iFormView.showAlert(iFormView.MOTHER_NAME);
        } else if (StringUtils.isEmpty(Nationality)) {
            iFormView.showAlert(iFormView.NATIONALITY);
        } else if (StringUtils.isEmpty(DateOfBirth)) {
            iFormView.showAlert(iFormView.DATE_OF_BIRTH);
        } else if (StringUtils.isEmpty(PlaceOfBirth)) {
            iFormView.showAlert(iFormView.PLACE_OF_BIRTH);
        } else if (StringUtils.isEmpty(PassportNo)) {
            iFormView.showAlert(iFormView.PASSPORT_NO);
        } else if (StringUtils.isEmpty(PlaceOfIssue)) {
            iFormView.showAlert(iFormView.PLACE_OF_ISSUE);
        } else if (StringUtils.isEmpty(DateOfIssue)) {
            iFormView.showAlert(iFormView.DATE_OF_ISSUE);
        } else if (StringUtils.isEmpty(DateOfExpiry)) {
            iFormView.showAlert(iFormView.DATE_OF_EXPIRY);
        } else if (StringUtils.isEmpty(DateOfLastEntry)) {
            iFormView.showAlert(iFormView.DATE_OF_LAST_ENTRY);
        } else if (StringUtils.isEmpty(AddrAbroad)) {
            iFormView.showAlert(iFormView.ADDR_ABROAD);
        } else if (StringUtils.isEmpty(R_ShipWithApplicant)) {
            iFormView.showAlert(iFormView.R_SHIP_WITH_APPLI);
        } else if (StringUtils.isEmpty(Reasonrequest)) {
            iFormView.showAlert(iFormView.REASON_REQUEST);
        } else if (StringUtils.isEmpty(anyBankLoan)) {
            iFormView.showAlert(iFormView.ANY_BANK_LOAN);
        } else if (StringUtils.isEmpty(anyHRAdvance)) {
            iFormView.showAlert(iFormView.ANY_HR_ADVANCE);
        } else if (StringUtils.isEmpty(anyTransportAdvance)) {
            iFormView.showAlert(iFormView.ANY_TRANS_ADVANCE);
        } else if (StringUtils.isEmpty(creditCards)) {
            iFormView.showAlert(iFormView.CREDIT_CARDS);
        } else {
            VisaSpouseChildrenDO FamilyVisaParents = new VisaSpouseChildrenDO();
            FamilyVisaParents.setPassportName(name);
            FamilyVisaParents.setFathername(FatherName);
            FamilyVisaParents.setMothername(MotherName);
            FamilyVisaParents.setNationality(Nationality);
            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
            FamilyVisaParents.setDOB(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfBirth,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

            FamilyVisaParents.setPlaceofBirth(PlaceOfBirth);
            FamilyVisaParents.setPPNumber(PassportNo);
            FamilyVisaParents.setPlaceofIssue(PlaceOfIssue);
            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
            FamilyVisaParents.setDateofIssue(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfIssue,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));
            FamilyVisaParents.setDateofExpiry(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfExpiry,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

            FamilyVisaParents.setProfessionpassport(Profession);
            //for sending to backend from dd-MMM-yyyy to dd/MM/yyyy pattern
            FamilyVisaParents.setDatelastentryOman(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(DateOfLastEntry,CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.DD_MM_YYYY_DATE_PATTERN));

            FamilyVisaParents.setAddrAbroad(AddrAbroad);
            FamilyVisaParents.setR_shipwithapplicant(R_ShipWithApplicant);
            FamilyVisaParents.setReasonrequest(Reasonrequest);
            FamilyVisaParents.setAnyBankLoan(anyBankLoan);
            FamilyVisaParents.setAnyHRAdvance(anyHRAdvance);
            FamilyVisaParents.setAnyTransportAdvance(anyTransportAdvance);
            FamilyVisaParents.setCreditCards(creditCards);

            submitForm(serviceDO.serviceType, FamilyVisaParents);
        }
    }


    /**************************************** Family Visa Parents Form end **************************************************/


    protected void submitForm(ServiceDO.ServiceType serviceType, FormRequestDO formRequestDO) {
        formRequestDO.setStaffNumber(preference.getStringFromPreference(Preference.STAFF_NUMBER, ""));
        formRequestDO.setStatus("Pending");
        formRequestDO.setRequestedDate("" + CalendarUtil.getCurrentDateTimeInTFormate());
        ServiceRequestBody serviceRequestBody = new ServiceRequestBody();
        switch (serviceType) {


            /**************************Bank Account update   ************************************/
            case SALARY_TRANSFER_REQUEST:
                serviceRequestBody.setSalaryTransferRequestBankAccountDo((SalaryTransferRequestBankAccountDo) formRequestDO);
                break;

            case C3_CARD_ATM_WITCH_CLAIM_SERVICE_REQUEST:
                serviceRequestBody.setC3CardATMSwitchClaimDO((C3CardATMSwitchClaimDO) formRequestDO);
                break;

            case C3_CARD_REPLACEMENT_SERVICE_REQUEST:
                serviceRequestBody.setC3CardReplacement((C3CardReplacementDO) formRequestDO);
                break;

            case C3_CARD_CANCELLATION_SERVICE_REQUEST:
                serviceRequestBody.setC3CardCancelation((CardCancellationDO) formRequestDO);
                break;

            case C3_CARD_STATEMENT_SERVICE_REQUEST:
                serviceRequestBody.setC3CardStatement((C3CardStatementDO) formRequestDO);
                break;
            /**************************Bank Account update End************************************/

            /**************************    Visa Visit Start************************************/

            case VISIT_VISA_FOR_SPOUSE_CHILDREN:
                serviceRequestBody.setVisaSpouseChildren((VisaSpouseChildrenDO) formRequestDO);
                break;
            case VISIT_VISA_FOR_IN_LAWS_CHILDREN:
                serviceRequestBody.setVisaInLawsChildren((VisaSpouseChildrenDO) formRequestDO);
                break;
            case FAMILY_JOINING_VISA_FOR_PARENTS:
                serviceRequestBody.setFamilyVisaParents((VisaSpouseChildrenDO) formRequestDO);
                break;
            case FAMILY_JOINING_VISA_FOR_SPOUSE_CHILDREN:
                serviceRequestBody.setFamilyVisaSpouseChildren((VisaSpouseChildrenDO) formRequestDO);
                break;

            /**************************    Visa Visit End************************************/

            /**************************    HR Service Request Start************************************/

            case LEAVE_JOINING:
                serviceRequestBody.setLeaveJoining((LeaveJoiningDO) formRequestDO);
                break;
            case LEAVE_APPLICATION:
                serviceRequestBody.setAnnualLeave((LeaveApplicationDO) formRequestDO);
                break;

            /**************************    Hr Service Request End************************************/

            /**************************    Salary Request Start************************************/
            case SALARY_TRANSFER_LETTER_REQUEST:
                serviceRequestBody.setSalaryTransferLetterRequest((SalaryTransferLetterRequestDO) formRequestDO);
                break;
            case SALARY_CERTIFICATE_REQUEST:
                serviceRequestBody.setSalaryCertificateRequest((SalaryCertificateRequestDO) formRequestDO);
                break;

            /**************************     Salary Request End************************************/

            case PASSPORT_RELEASE_REQUEST:
                serviceRequestBody.setPassportRelease((PassportReleaseDO) formRequestDO);
                break;
            case HOUSING_ALLOWANCE:
                serviceRequestBody.setHousingAllowance((HousingAllowanceDO) formRequestDO);
                break;
            case TRANSPORT_LOAN_REQUEST:
                serviceRequestBody.setTransportLoan((TransportLoanDO) formRequestDO);
                break;
            case COMMITMENT_FORM:
                serviceRequestBody.setCommitmentForm((CommitmentFormDO) formRequestDO);
                break;

            case BUSINESS_CARD_REQUEST:
                serviceRequestBody.setBusinessCard((BusinessCardDo) formRequestDO);
                break;

            case BUSINESS_TRAVEL_REQUEST:
                serviceRequestBody.setBusinessTravel((BusinessTravelDo) formRequestDO);
                break;

            case SYSTEM_ACCESS_REQUEST:
                serviceRequestBody.setSystemAccess((SystemAccessDO) formRequestDO);
                break;
        }
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setServiceRequestBody(serviceRequestBody);
        iAsyncFormInteractor.postForm(serviceRequest);
    }

    protected boolean validateQuestion() {
        if (radioBTNDOs != null) {
            for (RadioBTNDO radioBTNDO : radioBTNDOs) {
                if (StringUtils.isEmpty(radioBTNDO.ans))
                    return false;
            }
            return true;
        }
        return false;
    }

    private void uploadAttachments(ServiceRequestDO serviceRequestDO) {
        if (serviceRequestDO != null) {
            ServiceRequest serviceRequest = new ServiceRequest();
            ServiceRequestBody serviceRequestBody = new ServiceRequestBody();

            /*********** If it from Card Cancelation ************************/
            if (serviceRequestDO.getFormid()==22){
                serviceRequestBody.setServiceId(serviceRequestDO.getId());
                serviceRequestBody.setArrServiceAttachments(AppConstants.arrAttachments);
                serviceRequest.setServiceRequestBody(serviceRequestBody);

                if (AppConstants.arrAttachments!=null && AppConstants.arrAttachments.size()>0){

                    iAsyncFormInteractor.postAttachments(serviceRequest);
//                    AppConstants.arrAttachments.clear();
                }
            }else {
                serviceRequestBody.setServiceId(serviceRequestDO.getId());
                serviceRequestBody.setArrServiceAttachments(attachments);
                serviceRequest.setServiceRequestBody(serviceRequestBody);

                if (attachments != null && attachments.size() > 0)
                    iAsyncFormInteractor.postAttachments(serviceRequest);
            }

/*
            if (attachments != null && attachments.size() > 0)
                iAsyncFormInteractor.postAttachments(serviceRequest);*/

        }
    }

    @Override
    public void onSuccess(final String message, final ServiceRequestDO serviceRequestDO) {

        if (serviceRequestDO != null) {
            uploadAttachments(serviceRequestDO);
            AppConstants.SERVICE_ID=serviceRequestDO.getId();
        }
        if (serviceRequestDO != null)
            uploadSignature(serviceRequestDO);

        handler.postResult(new Runnable() {
            @Override
            public void run() {
                if (AppConstants.popupFlag) {
                    iFormView.showAlert("Kindly Submit Your Existing C3 card to HR for Cancellation", AppConstants.serviceDO);
                    AppConstants.popupFlag = false;
                    AppConstants.count = AppConstants.count + 1;

                } else if (AppConstants.count == 1) {
                    AppConstants.count = 0;
                } else {
                    iFormView.showAlert(message, serviceDO);
                }
            }
        });
    }

    @Override
    public void onSuccess(final String message) {
        handler.postResult(new Runnable() {
            @Override
            public void run() {
                iFormView.showAlert(message, null);
            }
        });
    }

    @Override
    public void gotFormData(final HashMap<String, ServiceResponseData> hmFormDataDetail) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                iFormView.gotFormData(hmFormDataDetail);
            }
        }, 10);
    }

    @Override
    public void onError(final String Message) {
        handler.postResult(new Runnable() {
            @Override
            public void run() {
                iFormView.showAlert(Message);
            }
        });

    }

    private void uploadSignature(ServiceRequestDO serviceRequestDO) {
        if (serviceRequestDO != null) {
            ArrayList<String> arrayList = new ArrayList<>();
            String path = preference.getStringFromPreference(AppConstants.SIGNATURE, "");
            arrayList.add(path);

            ServiceRequest serviceRequest = new ServiceRequest();
            ServiceRequestBody serviceRequestBody = new ServiceRequestBody();

            serviceRequestBody.setServiceId(serviceRequestDO.getId());
            serviceRequestBody.setArrServiceAttachments(arrayList);
            serviceRequest.setServiceRequestBody(serviceRequestBody);
            serviceRequestBody.setFormid(serviceRequestDO.getFormid());

            if (!StringUtils.isEmpty(path))
                iAsyncFormInteractor.postSignature(serviceRequest);

            if (AppConstants.cancellationFlag) {
                AppConstants.cancellationFlag = false;
            } else {
                preference.removeFromPreference(AppConstants.SIGNATURE);
            }

        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    @Override
    public ArrayList<String> getAttachments() {
        return attachments;
    }

}
