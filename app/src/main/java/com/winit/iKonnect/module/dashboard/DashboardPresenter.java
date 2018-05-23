package com.winit.iKonnect.module.dashboard;


import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.dataobject.ServiceDO;
import com.winit.iKonnect.dataobject.ServiceResponseData;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.dashboard.interactors.FeedsInteractor;
import com.winit.iKonnect.module.dashboard.interactors.IFeedsInteractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Girish Velivela on 4/11/16.
 */

public class DashboardPresenter extends BasePresenter implements IDashboardPresenter, IFeedsInteractor.OnFeedsListener {

    private IDashboardView view;
    private ArrayList<ServiceDO> serviceDOs;

    private IFeedsInteractor interactor;

    public DashboardPresenter(IDashboardView view) {
        super(view);
        this.view = view;
        interactor = new FeedsInteractor(this);
    }

    public ArrayList<ServiceDO> getServiceDOs() {
        return serviceDOs;
    }

    @Override
    public void getThoughtOfTheDayFromService(String Staffno) {
        interactor.getThoughtOfTheDay(Staffno);
    }

    @Override
    public void getFormActivationStatus() {
        interactor.getFormStatus();
    }

    @Override
    public void onError(final String message) {
        if (message.equalsIgnoreCase("1234")) {
            view.gotFormData(null);
        } else
            view.showAlert(message);
    }

    @Override
    public void onSuccess(final List<FeedsDO> feeds) {

    }

    @Override
    public void gotMessege(String s, String s_arabic) {
        if (s != null && !TextUtils.isEmpty(s) && s_arabic != null && !TextUtils.isEmpty(s_arabic))
            view.StoreThoughtoftheDay(s, s_arabic);
    }

    @Override
    public void gotFormData(final HashMap<String, ServiceResponseData> hmFormDataDetail) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                view.gotFormData(hmFormDataDetail);//Handler
            }
        }, 10);
    }

    @Override
    public void loadData(HashMap<String, ServiceResponseData> hmFormDataDetail) {
        serviceDOs = new ArrayList<>();
        ServiceDO serviceDO = new ServiceDO();

        if (hmFormDataDetail == null)// if error occurred in the server than all form will displayed
        {

            ServiceDO bankAcccountServiceDo = new ServiceDO();
            bankAcccountServiceDo.serviceName = IKonnectApplication.mContext.getString(R.string.Bank_Account_Update);
            bankAcccountServiceDo.serviceLogo = R.drawable.form_one;
            bankAcccountServiceDo.serviceType = ServiceDO.ServiceType.BANK_ACCOUNT_UPDATE;

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Salary_Transfer_Request);
            serviceDO.serviceLogo = R.drawable.form_one;
            serviceDO.serviceType = ServiceDO.ServiceType.SALARY_TRANSFER_REQUEST;
            bankAcccountServiceDo.bankAccountUpdateDos.add(serviceDO);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.C3_Card_ATM_witch_Claim_Service_Request);
            serviceDO.serviceLogo = R.drawable.form_one;
            serviceDO.serviceType = ServiceDO.ServiceType.C3_CARD_ATM_WITCH_CLAIM_SERVICE_REQUEST;
            bankAcccountServiceDo.bankAccountUpdateDos.add(serviceDO);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.C3_Card_Replacement_Service_Request);
            serviceDO.serviceLogo = R.drawable.form_one;
            serviceDO.serviceType = ServiceDO.ServiceType.C3_CARD_REPLACEMENT_SERVICE_REQUEST;
            bankAcccountServiceDo.bankAccountUpdateDos.add(serviceDO);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.C3_Card_Statement_Service_Request);
            serviceDO.serviceLogo = R.drawable.form_one;
            serviceDO.serviceType = ServiceDO.ServiceType.C3_CARD_STATEMENT_SERVICE_REQUEST;
            bankAcccountServiceDo.bankAccountUpdateDos.add(serviceDO);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.C3_Card_Cancellation_Service_Request);
            serviceDO.serviceLogo = R.drawable.form_one;
            serviceDO.serviceType = ServiceDO.ServiceType.C3_CARD_CANCELLATION_SERVICE_REQUEST;
            bankAcccountServiceDo.bankAccountUpdateDos.add(serviceDO);

            serviceDOs.add(bankAcccountServiceDo);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Business_Card_Request);
            serviceDO.serviceLogo = R.drawable.form_two;
            serviceDO.serviceType = ServiceDO.ServiceType.BUSINESS_CARD_REQUEST;
            serviceDOs.add(serviceDO);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Business_Travel_Request);
            serviceDO.serviceLogo = R.drawable.form_three;
            serviceDO.serviceType = ServiceDO.ServiceType.BUSINESS_TRAVEL_REQUEST;
            serviceDOs.add(serviceDO);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Commitment_Form);
            serviceDO.serviceLogo = R.drawable.form_four;
            serviceDO.serviceType = ServiceDO.ServiceType.COMMITMENT_FORM;
            serviceDOs.add(serviceDO);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.housing_allowance);
            serviceDO.serviceLogo = R.drawable.form_five;
            serviceDO.serviceType = ServiceDO.ServiceType.HOUSING_ALLOWANCE;
            serviceDOs.add(serviceDO);

            ServiceDO hrRequestForLeave = new ServiceDO();
            hrRequestForLeave.serviceName = IKonnectApplication.mContext.getString(R.string.HR_Service_Request_For_Leave);
            hrRequestForLeave.serviceLogo = R.drawable.form_three;
            hrRequestForLeave.serviceType = ServiceDO.ServiceType.HR_SERVICE_REQUEST;

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Leave_Application);
            serviceDO.serviceLogo = R.drawable.form_seven;
            serviceDO.serviceType = ServiceDO.ServiceType.LEAVE_APPLICATION;
            hrRequestForLeave.hrServiceRequestDos.add(serviceDO);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Leave_Joining);
            serviceDO.serviceLogo = R.drawable.form_six;
            serviceDO.serviceType = ServiceDO.ServiceType.LEAVE_JOINING;
            hrRequestForLeave.hrServiceRequestDos.add(serviceDO);

            serviceDOs.add(hrRequestForLeave);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Passport_Release_Request);
            serviceDO.serviceLogo = R.drawable.form_eight;
            serviceDO.serviceType = ServiceDO.ServiceType.PASSPORT_RELEASE_REQUEST;
            serviceDOs.add(serviceDO);

            ServiceDO salaryRequest = new ServiceDO();
            salaryRequest.serviceName = IKonnectApplication.mContext.getString(R.string.Salary_Request);
            salaryRequest.serviceLogo = R.drawable.form_nine;
            salaryRequest.serviceType = ServiceDO.ServiceType.SALARY_REQUEST;

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Salary_Certificate_Request);
            serviceDO.serviceLogo = R.drawable.form_nine;
            serviceDO.serviceType = ServiceDO.ServiceType.SALARY_CERTIFICATE_REQUEST;
            salaryRequest.salaryRequestDos.add(serviceDO);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Salary_Transfer_Letter_Request);
            serviceDO.serviceLogo = R.drawable.form_nine;
            serviceDO.serviceType = ServiceDO.ServiceType.SALARY_TRANSFER_LETTER_REQUEST;
            salaryRequest.salaryRequestDos.add(serviceDO);
            serviceDOs.add(salaryRequest);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.System_Access_Request);
            serviceDO.serviceLogo = R.drawable.form_ten;
            serviceDO.serviceType = ServiceDO.ServiceType.SYSTEM_ACCESS_REQUEST;
            serviceDOs.add(serviceDO);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Transport_Loan_Request);
            serviceDO.serviceLogo = R.drawable.form_eleven;
            serviceDO.serviceType = ServiceDO.ServiceType.TRANSPORT_LOAN_REQUEST;
            serviceDOs.add(serviceDO);

            //visa
            ServiceDO visit_visaDo = new ServiceDO();
            visit_visaDo.serviceName = IKonnectApplication.mContext.getString(R.string.Visit_Visa);
            visit_visaDo.serviceLogo = R.drawable.form_twelve;
            visit_visaDo.serviceType = ServiceDO.ServiceType.VISIT_VISA;

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Visit_Visa_for_Spouse_Children);
            serviceDO.serviceLogo = R.drawable.form_twelve;
            serviceDO.serviceType = ServiceDO.ServiceType.VISIT_VISA_FOR_SPOUSE_CHILDREN;
            visit_visaDo.visaVisitDos.add(serviceDO);


            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Visit_Visa_for_In_laws_children);
            serviceDO.serviceLogo = R.drawable.form_twelve;
            serviceDO.serviceType = ServiceDO.ServiceType.VISIT_VISA_FOR_IN_LAWS_CHILDREN;
            visit_visaDo.visaVisitDos.add(serviceDO);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Family_Joining_Visa_for_Spouse_children);
            serviceDO.serviceLogo = R.drawable.form_twelve;
            serviceDO.serviceType = ServiceDO.ServiceType.FAMILY_JOINING_VISA_FOR_SPOUSE_CHILDREN;
            visit_visaDo.visaVisitDos.add(serviceDO);

            serviceDO = new ServiceDO();
            serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Family_Joining_Visa_for_Parents);
            serviceDO.serviceLogo = R.drawable.form_twelve;
            serviceDO.serviceType = ServiceDO.ServiceType.FAMILY_JOINING_VISA_FOR_PARENTS;
            visit_visaDo.visaVisitDos.add(serviceDO);

            serviceDOs.add(visit_visaDo);

        } else {

            if (preference.getStringFromPreference(Preference.STAFF_WORK_COUNTRY, "").equalsIgnoreCase("UAE")) {
                if (hmFormDataDetail.containsKey("Salary Transfer Request")
                        || hmFormDataDetail.containsKey("C3 Card Replacement Service Request")
                        || hmFormDataDetail.containsKey("C3 Card Statement Service Request")
                        || hmFormDataDetail.containsKey("C3 Card Cancellation Service Request")
                        || hmFormDataDetail.containsKey("C3 Card ATM Switch Claim Service Request")) {
                    ServiceDO bankAcccountServiceDo = new ServiceDO();
                    bankAcccountServiceDo.serviceName = IKonnectApplication.mContext.getString(R.string.Bank_Account_Update);
                    bankAcccountServiceDo.serviceLogo = R.drawable.form_one;
                    bankAcccountServiceDo.serviceType = ServiceDO.ServiceType.BANK_ACCOUNT_UPDATE;


                   if (hmFormDataDetail.containsKey("Salary Transfer Request")) {
                        serviceDO = new ServiceDO();
                        serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Salary_Transfer_Request);
                        serviceDO.serviceLogo = R.drawable.form_one;
                        serviceDO.serviceType = ServiceDO.ServiceType.SALARY_TRANSFER_REQUEST;
                        bankAcccountServiceDo.bankAccountUpdateDos.add(serviceDO);

                    }

                    if (hmFormDataDetail.containsKey("C3 Card ATM Switch Claim Service Request")) {
                        serviceDO = new ServiceDO();
                        serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.C3_Card_ATM_witch_Claim_Service_Request);
                        serviceDO.serviceLogo = R.drawable.form_one;
                        serviceDO.serviceType = ServiceDO.ServiceType.C3_CARD_ATM_WITCH_CLAIM_SERVICE_REQUEST;
                        bankAcccountServiceDo.bankAccountUpdateDos.add(serviceDO);
                    }

                    if (hmFormDataDetail.containsKey("C3 Card Replacement Service Request")) {
                        serviceDO = new ServiceDO();
                        serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.C3_Card_Replacement_Service_Request);
                        serviceDO.serviceLogo = R.drawable.form_one;
                        serviceDO.serviceType = ServiceDO.ServiceType.C3_CARD_REPLACEMENT_SERVICE_REQUEST;
                        bankAcccountServiceDo.bankAccountUpdateDos.add(serviceDO);
                    }

                    if (hmFormDataDetail.containsKey("C3 Card Statement Service Request")) {
                        serviceDO = new ServiceDO();
                        serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.C3_Card_Statement_Service_Request);
                        serviceDO.serviceLogo = R.drawable.form_one;
                        serviceDO.serviceType = ServiceDO.ServiceType.C3_CARD_STATEMENT_SERVICE_REQUEST;
                        bankAcccountServiceDo.bankAccountUpdateDos.add(serviceDO);
                    }
                    if (hmFormDataDetail.containsKey("C3 Card Cancellation Service Request")) {
                        serviceDO = new ServiceDO();
                        serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.C3_Card_Cancellation_Service_Request);
                        serviceDO.serviceLogo = R.drawable.form_one;
                        serviceDO.serviceType = ServiceDO.ServiceType.C3_CARD_CANCELLATION_SERVICE_REQUEST;
                        bankAcccountServiceDo.bankAccountUpdateDos.add(serviceDO);
                    }

                    serviceDOs.add(bankAcccountServiceDo);

                }

            } else {
                if (hmFormDataDetail.containsKey("Salary Transfer Request")) {
                    serviceDO = new ServiceDO();
                    serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Salary_Transfer_Request);
                    serviceDO.serviceLogo = R.drawable.form_one;
                    serviceDO.serviceType = ServiceDO.ServiceType.SALARY_TRANSFER_REQUEST;
                    serviceDOs.add(serviceDO);
                }
            }

            if (hmFormDataDetail.containsKey("Business Card Request")) {
                serviceDO = new ServiceDO();
                serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Business_Card_Request);
                serviceDO.serviceLogo = R.drawable.form_two;
                serviceDO.serviceType = ServiceDO.ServiceType.BUSINESS_CARD_REQUEST;
                serviceDOs.add(serviceDO);
            }
            if (hmFormDataDetail.containsKey("Business Travel Request")) {
                serviceDO = new ServiceDO();
                serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Business_Travel_Request);
                serviceDO.serviceLogo = R.drawable.form_fourteen_new;
                serviceDO.serviceType = ServiceDO.ServiceType.BUSINESS_TRAVEL_REQUEST;
                serviceDOs.add(serviceDO);
            }

            if (hmFormDataDetail.containsKey("Reach Out Commitment Form")) {
                serviceDO = new ServiceDO();
                serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Commitment_Form);
                serviceDO.serviceLogo = R.drawable.form_four;
                serviceDO.serviceType = ServiceDO.ServiceType.COMMITMENT_FORM;
                serviceDOs.add(serviceDO);
            }

            if (hmFormDataDetail.containsKey("Housing Allowance Advance Request")) {
                serviceDO = new ServiceDO();
                serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.housing_allowance);
                serviceDO.serviceLogo = R.drawable.form_five;
                serviceDO.serviceType = ServiceDO.ServiceType.HOUSING_ALLOWANCE;
                serviceDOs.add(serviceDO);
            }

            if (hmFormDataDetail.containsKey("Leave Application") ||
                    hmFormDataDetail.containsKey("Leave Joining")) {
                ServiceDO hrRequestForLeave = new ServiceDO();
                hrRequestForLeave.serviceName = IKonnectApplication.mContext.getString(R.string.HR_Service_Request_For_Leave);
                hrRequestForLeave.serviceLogo = R.drawable.form_three;
                hrRequestForLeave.serviceType = ServiceDO.ServiceType.HR_SERVICE_REQUEST;

                if (hmFormDataDetail.containsKey("Leave Application")) {
                    serviceDO = new ServiceDO();
                    serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Leave_Application);
                    serviceDO.serviceLogo = R.drawable.form_seven;
                    serviceDO.serviceType = ServiceDO.ServiceType.LEAVE_APPLICATION;
                    hrRequestForLeave.hrServiceRequestDos.add(serviceDO);
                }
                if (hmFormDataDetail.containsKey("Leave Joining")) {
                    serviceDO = new ServiceDO();
                    serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Leave_Joining);
                    serviceDO.serviceLogo = R.drawable.form_six;
                    serviceDO.serviceType = ServiceDO.ServiceType.LEAVE_JOINING;
                    hrRequestForLeave.hrServiceRequestDos.add(serviceDO);
                }

                serviceDOs.add(hrRequestForLeave);
            }


            if (hmFormDataDetail.containsKey("Passport Release Request")) {
                serviceDO = new ServiceDO();
                serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Passport_Release_Request);
                serviceDO.serviceLogo = R.drawable.form_eight;
                serviceDO.serviceType = ServiceDO.ServiceType.PASSPORT_RELEASE_REQUEST;
                serviceDOs.add(serviceDO);
            }


            if (hmFormDataDetail.containsKey("Salary Certificate Request") ||
                    hmFormDataDetail.containsKey("Salary Transfer Letter Request")
                    ) {
                ServiceDO salaryRequest = new ServiceDO();
                salaryRequest.serviceName = IKonnectApplication.mContext.getString(R.string.Salary_Request);
                salaryRequest.serviceLogo = R.drawable.form_nine;
                salaryRequest.serviceType = ServiceDO.ServiceType.SALARY_REQUEST;


                if (hmFormDataDetail.containsKey("Salary Certificate Request")) {
                    serviceDO = new ServiceDO();
                    serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Salary_Certificate_Request);
                    serviceDO.serviceLogo = R.drawable.form_nine;
                    serviceDO.serviceType = ServiceDO.ServiceType.SALARY_CERTIFICATE_REQUEST;
                    salaryRequest.salaryRequestDos.add(serviceDO);
                }


                if (hmFormDataDetail.containsKey("Salary Transfer Letter Request ")) {
                    serviceDO = new ServiceDO();
                    serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Salary_Transfer_Letter_Request);
                    serviceDO.serviceLogo = R.drawable.form_nine;
                    serviceDO.serviceType = ServiceDO.ServiceType.SALARY_TRANSFER_LETTER_REQUEST;
                    salaryRequest.salaryRequestDos.add(serviceDO);
                }
                serviceDOs.add(salaryRequest);

            }


            if (hmFormDataDetail.containsKey("System Access Request")) {
                serviceDO = new ServiceDO();
                serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.System_Access_Request);
                serviceDO.serviceLogo = R.drawable.form_ten;
                serviceDO.serviceType = ServiceDO.ServiceType.SYSTEM_ACCESS_REQUEST;
                serviceDOs.add(serviceDO);
            }
            if (!(preference.getStringFromPreference(Preference.STAFF_NATIONALITY, "").equalsIgnoreCase(AppConstants.NATIONALITY)))
            {
                if (hmFormDataDetail.containsKey("Transport Loan Request")) {
                    serviceDO = new ServiceDO();
                    serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Transport_Loan_Request);
                    serviceDO.serviceLogo = R.drawable.form_eleven;
                    serviceDO.serviceType = ServiceDO.ServiceType.TRANSPORT_LOAN_REQUEST;
                    serviceDOs.add(serviceDO);
                }
            }


            // if nationality Oman and working country Oman are not eligible for visa requests , And Employee working in UAE are also not Eligible

            if (preference.getStringFromPreference(Preference.STAFF_WORK_COUNTRY, "").equalsIgnoreCase(AppConstants.STAFF_WORK_COUNTRY)
                    && !preference.getStringFromPreference(Preference.STAFF_NATIONALITY, "").equalsIgnoreCase(AppConstants.NATIONALITY) )
            {
                if (hmFormDataDetail.containsKey("Visit Visa for In laws, children")
                        || hmFormDataDetail.containsKey("Family Joining Visa for Parent’s")
                        || hmFormDataDetail.containsKey("Visit Visa for Spouse, Children")
                        || hmFormDataDetail.containsKey("Family Joining Visa for Spouse, children")) {

                    ServiceDO visit_visaDo = new ServiceDO();
                    visit_visaDo.serviceName = IKonnectApplication.mContext.getString(R.string.Visit_Visa);
                    visit_visaDo.serviceLogo = R.drawable.form_twelve;
                    visit_visaDo.serviceType = ServiceDO.ServiceType.VISIT_VISA;


                    if (hmFormDataDetail.containsKey("Visit Visa for Spouse, children (below 18 years) and Parent/s")) {
                        serviceDO = new ServiceDO();
                        serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Visit_Visa_for_Spouse_Children);
                        serviceDO.serviceLogo = R.drawable.form_twelve;
                        serviceDO.serviceType = ServiceDO.ServiceType.VISIT_VISA_FOR_SPOUSE_CHILDREN;
                        visit_visaDo.visaVisitDos.add(serviceDO);
                    }


                    if (hmFormDataDetail.containsKey("Visit Visa for In laws, children (above 18 years)")) {
                        serviceDO = new ServiceDO();
                        serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Visit_Visa_for_In_laws_children);
                        serviceDO.serviceLogo = R.drawable.form_twelve;
                        serviceDO.serviceType = ServiceDO.ServiceType.VISIT_VISA_FOR_IN_LAWS_CHILDREN;
                        visit_visaDo.visaVisitDos.add(serviceDO);
                    }

                    if (hmFormDataDetail.containsKey("Family Joining Visa for Spouse, children (below 18 years)")) {
                        serviceDO = new ServiceDO();
                        serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Family_Joining_Visa_for_Spouse_children);
                        serviceDO.serviceLogo = R.drawable.form_twelve;
                        serviceDO.serviceType = ServiceDO.ServiceType.FAMILY_JOINING_VISA_FOR_SPOUSE_CHILDREN;
                        visit_visaDo.visaVisitDos.add(serviceDO);
                    }

                    if (hmFormDataDetail.containsKey("Family Joining Visa for Parent’s")) {
                        serviceDO = new ServiceDO();
                        serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Family_Joining_Visa_for_Parents);
                        serviceDO.serviceLogo = R.drawable.form_twelve;
                        serviceDO.serviceType = ServiceDO.ServiceType.FAMILY_JOINING_VISA_FOR_PARENTS;
                        visit_visaDo.visaVisitDos.add(serviceDO);
                    }

                    serviceDOs.add(visit_visaDo);

                }
            }
        }
        view.onServices();
    }

    @Override
    public void loadData() {

    }
}
