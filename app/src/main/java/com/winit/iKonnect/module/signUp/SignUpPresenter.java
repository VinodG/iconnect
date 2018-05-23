package com.winit.iKonnect.module.signUp;

import com.winit.common.Preference;
import com.winit.common.util.CalendarUtil;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.dataobject.response.EmpModelDO;
import com.winit.iKonnect.dataobject.response.LoginResponse;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.login.interactors.AsyncLoginInteractor;
import com.winit.iKonnect.module.login.interactors.IAsyncLoginInteractor;
import com.winit.iKonnect.module.signUp.interacter.ISignUpInteractor;
import com.winit.iKonnect.module.signUp.interacter.SignUpInteracter;
import com.winit.iKonnect.ui.activities.SignUpActivity;


import java.util.Locale;

import static com.winit.iKonnect.module.login.ILoginView.STAFFID;

/**
 * Created by Rahul.Yadav on 5/28/2017.
 */

public class SignUpPresenter extends BasePresenter implements ISignUpPresenter,SignUpInteracter.OnSignUpListener,AsyncLoginInteractor.OnLoginFinishedListener {

    private ISignUpView view ;
    private ISignUpInteractor interactor;
    private IAsyncLoginInteractor iAsyncLoginInteractor;


    public SignUpPresenter(SignUpActivity view) {
        super(view);
        this.view = view;
        this.interactor = new SignUpInteracter(this);
        iAsyncLoginInteractor = new AsyncLoginInteractor(this);
    }
    @Override
    public void submit(String StaffNumber) {
        if(StringUtils.isEmpty(StaffNumber)){
            view.showAlert(STAFFID);
        }
        else {
            iAsyncLoginInteractor.signUP(StaffNumber);
        }
    }

    @Override
    public void loadData() {

    }

    @Override
    public void submit(String StaffNumber, String mobile, String workCountry) {
            interactor.postData(StaffNumber,mobile,workCountry);
    }


    @Override
    public void onError(String Message) {
        view.showAlert(Message);
    }

    // For Otp
    @Override
    public void onSuccess(int status, String messag) {
    view.showAlert(messag);
    }

    // For Login or Signup
    @Override
    public void onSuccess(Object object) {
        LoginResponse loginResponse = (LoginResponse) object;
        if(loginResponse!=null&&loginResponse.getStatusCode()==200)
        {
            storeUserDetails(loginResponse.getEmpModel());
            interactor.postData(preference.getStringFromPreference(Preference.STAFF_NUMBER,""),preference.getStringFromPreference(Preference.STAFF_MOBILE,""),preference.getStringFromPreference(Preference.STAFF_WORK_COUNTRY,""));
        }
        else
          view.showAlert(isArabic? loginResponse.getStatusMessageAr() :loginResponse.getStatusMessageEn());


    }

    private void storeUserDetails(EmpModelDO loginResponseDO) {

        preference.saveStringInPreference(Preference.BAND, loginResponseDO.getBand());

        preference.saveStringInPreference(Preference.STAFF_WORK_COUNTRY, loginResponseDO.getWorkCountry());
        preference.saveStringInPreference(Preference.STAFF_PHOTO_URL, loginResponseDO.getPhotoUrl());
        preference.saveStringInPreference(Preference.COMPANY_CODE, loginResponseDO.getCompanyCode());
        preference.saveStringInPreference(Preference.CONFIRMATION, loginResponseDO.getConfirmation());
        preference.saveStringInPreference(Preference.COST_CTR, loginResponseDO.getCostCtr());
        preference.saveStringInPreference(Preference.ELIGIBILITY_FOR_HRA, loginResponseDO.getEligibilityForHRA());
        preference.saveStringInPreference(Preference.FIRST_NAME, loginResponseDO.getFirstName());
        preference.saveStringInPreference(Preference.FUNCTIONAL_AREA, loginResponseDO.getFunctionalArea());


        preference.saveStringInPreference(Preference.ID_NUMBER_EMIRATES_SELF, loginResponseDO.getIDNumberEmiratesSelf());
        preference.saveStringInPreference(Preference.ID_NUM_EMIRATES_SELF_EXPIREDATE, loginResponseDO.getIDNumberEmiratesSelf_ExpireDate());
        preference.saveStringInPreference(Preference.ID_NUMBER_PASSPORT_SLEF, loginResponseDO.getIDNumberPassportSlef());
        preference.saveStringInPreference(Preference.ID_NUMBER_PASSPORT_SLEF_EXPIREDATE, loginResponseDO.getIDNumberPassportSlef_ExpireDate());
        preference.saveStringInPreference(Preference.ID_NUMBER_RESIDENCE_VISA, loginResponseDO.getIDNumberResidenceVisa());
        preference.saveStringInPreference(Preference.ID_NUMBER_RESIDENCE_VISA_EXPIREDATE, loginResponseDO.getIDNumberResidenceVisa_ExpireDate());
        preference.saveStringInPreference(Preference.STAFF_NAME, loginResponseDO.getKnownAs());
        preference.saveStringInPreference(Preference.LAST_NAME, loginResponseDO.getLastName());
        preference.saveStringInPreference(Preference.STAFF_MOBILE, loginResponseDO.getMobile());
        preference.saveStringInPreference(Preference.STAFF_NATIONALITY, loginResponseDO.getNationality());
        preference.saveStringInPreference(Preference.STAFF_ORGUNIT, loginResponseDO.getOrgUnit());
        preference.saveStringInPreference(Preference.STAFF_PERSONAL_AREA, loginResponseDO.getPersonalArea());
        preference.saveStringInPreference(Preference.STAFF_PERSONAL_SUB_AREA, loginResponseDO.getPersonalSubArea());
        preference.saveStringInPreference(Preference.STAFF_POSITION, loginResponseDO.getPosition());
        preference.saveStringInPreference(Preference.STAFF_NUMBER, loginResponseDO.getStaffNumber());
        preference.saveStringInPreference(Preference.STAFF_STATUS, loginResponseDO.getStatus());
        preference.saveStringInPreference(Preference.TRANSPORT_ALLOWANCE, loginResponseDO.getTransportAllowance());
        preference.saveStringInPreference(Preference.ZZ_SECTOR, loginResponseDO.getZZSector());
        preference.saveStringInPreference(Preference.STAFF_EMAIL, loginResponseDO.getEmail());
        preference.saveStringInPreference(Preference.STAFF_ID, "" + loginResponseDO.getStaffNumber());


        preference.saveIntInPreference(Preference.LEAVE_BALANCE, loginResponseDO.getLeaveBalance());
        preference.saveBooleanInPreference(Preference.IS_ACTIVE, loginResponseDO.isactive());
        preference.saveBooleanInPreference(Preference.COMPANY_ACCOMODATION, loginResponseDO.isCompanyAccomodation());
        preference.saveStringInPreference(Preference.EMPLOYEE_SALARY, loginResponseDO.getBasicSalary() + "");
        preference.saveBooleanInPreference(Preference.BANK_LOAN, loginResponseDO.BankLoan);
        preference.saveStringInPreference(Preference.OUTSTANDING_HRA, loginResponseDO.getOutstandingHRA() + "");
        preference.saveStringInPreference(Preference.HOUSING_CUTOFF, loginResponseDO.getHousingCutOff()+"");

//        preference.saveStringInPreference(Preference.STAFF_RELIGION,loginResponseDO.getReligion());
//        preference.saveStringInPreference(Preference.STAFF_GRADE,loginResponseDO.getGrade());
        preference.saveStringInPreference(Preference.DEPARTMENT, loginResponseDO.getFunctionalArea());
        preference.saveStringInPreference(Preference.DOB, loginResponseDO.getBirthDate());
        preference.saveStringInPreference(Preference.JOINING_DATE, loginResponseDO.getJoiningDate());

        String hireDate = loginResponseDO.getHireDate();

        if (!hireDate.equalsIgnoreCase("")) {
            String date[] = hireDate.split("T");
            preference.saveStringInPreference(Preference.HIRE_DATE, CalendarUtil.getDate(date[0], CalendarUtil.DATE_PATTERN_SERVICE, CalendarUtil.DD_MM_YYYY_PATTERN, Locale.ENGLISH, "") + "");
        } else {
            preference.saveStringInPreference(Preference.HIRE_DATE, loginResponseDO.getHireDate() + "");
        }

        preference.saveStringInPreference(Preference.SPONSOR, loginResponseDO.getSponsor());
        preference.saveStringInPreference(Preference.BANK_NAME, loginResponseDO.getBankName());
        preference.saveStringInPreference(Preference.IBAN, loginResponseDO.getIBAN());
        preference.saveStringInPreference(Preference.ACCOUNT_NUMBER, loginResponseDO.getAccountNumber());

     /*   preference.saveStringInPreference(Preference.STAFF_NAME, loginResponseDO.getKnownAs());
        preference.saveStringInPreference(Preference.STAFF_NUMBER, loginResponseDO.getStaffNumber());
        preference.saveStringInPreference(Preference.STAFF_EMAIL, loginResponseDO.getEmail());
        preference.saveStringInPreference(Preference.STAFF_MOBILE, loginResponseDO.getMobile());
//        preference.saveStringInPreference(Preference.STAFF_RELIGION,loginResponseDO.getReligion());
        preference.saveStringInPreference(Preference.STAFF_POSITION, loginResponseDO.getPosition());
//        preference.saveStringInPreference(Preference.STAFF_GRADE,loginResponseDO.getGrade());
        preference.saveStringInPreference(Preference.STAFF_ID, "" + loginResponseDO.getStaffNumber());
        preference.saveStringInPreference(Preference.STAFF_NATIONALITY, loginResponseDO.getNationality());
        preference.saveStringInPreference(Preference.STAFF_ORGUNIT, loginResponseDO.getOrgUnit());
        preference.saveStringInPreference(Preference.STAFF_PERSONAL_AREA, loginResponseDO.getPersonalArea());
        preference.saveStringInPreference(Preference.STAFF_PERSONAL_SUB_AREA, loginResponseDO.getPersonalSubArea());
        preference.saveStringInPreference(Preference.STAFF_PHOTO_URL, loginResponseDO.getPhotoUrl());
        preference.saveStringInPreference(Preference.STAFF_STATUS, loginResponseDO.getStatus());
        preference.saveStringInPreference(Preference.STAFF_WORK_COUNTRY, loginResponseDO.getWorkCountry());
        preference.saveStringInPreference(Preference.DEPARTMENT, loginResponseDO.getFunctionalArea());
        preference.saveStringInPreference(Preference.FUNCTIONAL_AREA, loginResponseDO.getFunctionalArea());
        preference.saveStringInPreference(Preference.FIRST_NAME, loginResponseDO.getFirstName());
        preference.saveStringInPreference(Preference.EMPLOYEE_SALARY, loginResponseDO.getBasicSalary() + "");*/
    }
}
