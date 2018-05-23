package com.winit.iKonnect.dataobject;

import android.graphics.drawable.Drawable;

import com.winit.common.application.IKonnectApplication;
import com.winit.iKonnect.R;

import java.util.ArrayList;

/**
 * Created by Girish Velivela on 5/15/2017.
 */

public class ServiceDO extends BaseDO {

    public String serviceName;
    public int serviceLogo;
    public ServiceType serviceType;

    public ArrayList<ServiceDO> visaVisitDos = new ArrayList<>();
    public ArrayList<ServiceDO> hrServiceRequestDos = new ArrayList<>();
    public ArrayList<ServiceDO> salaryRequestDos = new ArrayList<>();
    public ArrayList<ServiceDO> bankAccountUpdateDos = new ArrayList<>();

    public enum ServiceType {
        BANK_ACCOUNT_UPDATE,
        SALARY_TRANSFER_REQUEST,
        C3_CARD_REPLACEMENT_SERVICE_REQUEST,
        C3_CARD_STATEMENT_SERVICE_REQUEST,
        C3_CARD_CANCELLATION_SERVICE_REQUEST,
        C3_CARD_ATM_WITCH_CLAIM_SERVICE_REQUEST,

        HR_SERVICE_REQUEST,
        LEAVE_APPLICATION,
        LEAVE_JOINING,

        HOUSING_ALLOWANCE,
        TRANSPORT_LOAN_REQUEST,
        BUSINESS_CARD_REQUEST,
        COMMITMENT_FORM,
        BUSINESS_TRAVEL_REQUEST,

        SALARY_REQUEST,
        SALARY_CERTIFICATE_REQUEST,
        SALARY_TRANSFER_LETTER_REQUEST,

        SYSTEM_ACCESS_REQUEST,
        PASSPORT_RELEASE_REQUEST,

        VISIT_VISA,
        VISIT_VISA_FOR_IN_LAWS_CHILDREN,
        FAMILY_JOINING_VISA_FOR_PARENTS,
        VISIT_VISA_FOR_SPOUSE_CHILDREN,
        FAMILY_JOINING_VISA_FOR_SPOUSE_CHILDREN
    }

    public static String getFormName(int formId) {
        switch (formId) {

            case 16:
                return IKonnectApplication.mContext.getString(R.string.Leave_Application);
            case 18:
                return IKonnectApplication.mContext.getString(R.string.Salary_Transfer_Request);
            case 19:
                return IKonnectApplication.mContext.getString(R.string.C3_Card_ATM_witch_Claim_Service_Request);
            case 20:
                return IKonnectApplication.mContext.getString(R.string.C3_Card_Replacement_Service_Request);
            case 21:
                return IKonnectApplication.mContext.getString(R.string.C3_Card_Statement_Service_Request);
            case 22:
                return IKonnectApplication.mContext.getString(R.string.C3_Card_Cancellation_Service_Request);
            case 23:
                return IKonnectApplication.mContext.getString(R.string.Business_Card_Request);
            case 24:
                return IKonnectApplication.mContext.getString(R.string.Business_Travel_Request);
            case 25:
                return IKonnectApplication.mContext.getString(R.string.Commitment_Form);
            case 26:
                return IKonnectApplication.mContext.getString(R.string.Housing_Allowance);
            case 27:
                return IKonnectApplication.mContext.getString(R.string.Leave_Joining);
            case 28:
                return IKonnectApplication.mContext.getString(R.string.Passport_Release_Request);
            case 29:
                return IKonnectApplication.mContext.getString(R.string.Salary_Certificate_Request);
            case 30:
                return IKonnectApplication.mContext.getString(R.string.Salary_Transfer_Letter_Request);
            case 31:
                return IKonnectApplication.mContext.getString(R.string.System_Access_Request);
            case 32:
                return IKonnectApplication.mContext.getString(R.string.Transport_Loan_Request);
            case 33:
                return IKonnectApplication.mContext.getString(R.string.Visit_Visa_for_Spouse_Children);
            case 34:
                return IKonnectApplication.mContext.getString(R.string.Visit_Visa_for_In_laws_children);
            case 35:
                return IKonnectApplication.mContext.getString(R.string.Family_Joining_Visa_for_Spouse_children);
            case 36:
                return IKonnectApplication.mContext.getString(R.string.Family_Joining_Visa_for_Parents);


        }
        return "";
    }


    public static Drawable getFormImages(int formId) {

        switch (formId) {

            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_form_one);

            case 23:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_form_two);

            case 24:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_form_fourteen_new);

            case 25:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_form_four);

            case 26:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_form_five);

            case 27:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_form_six);

            case 16:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_form_seven);

            case 28:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_form_eight);

            case 29:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_form_nine);

            case 30:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_form_nine);

            case 31:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_form_ten);

            case 32:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_form_eleven);

            case 33:
            case 34:
            case 35:
            case 36:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_form_twelve);
        }
        return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_default_icon);
    }


    public static Drawable getMainFormImages(int formId) {

        switch (formId) {

            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.form_one);


            case 23:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.form_two);

            case 24:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.form_fourteen_new);

            case 25:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.form_four);

            case 26:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.form_five);

            case 27:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.form_six);

            case 16:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.form_six);

            case 28:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.form_eight);

            case 29:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.form_nine);

            case 30:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.form_nine);

            case 31:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.form_ten);

            case 32:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.form_eleven);

            case 33:
            case 34:
            case 35:
            case 36:
                return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.form_twelve);
        }
        return IKonnectApplication.mContext.getResources().getDrawable(R.drawable.track_default_icon);
    }


}
