package com.winit.iKonnect.module.login;


import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.CalendarUtil;
import com.winit.common.util.FileUtils;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.dataobject.HoveringDo;
import com.winit.iKonnect.dataobject.LoginDo;
import com.winit.iKonnect.dataobject.response.EmpModelDO;
import com.winit.iKonnect.dataobject.response.LoginResponse;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.login.interactors.AsyncLoginInteractor;
import com.winit.iKonnect.module.login.interactors.IAsyncLoginInteractor;

import java.io.File;
import java.util.Date;
import java.util.Locale;


import static com.winit.iKonnect.module.login.ILoginView.PASSWORD;
import static com.winit.iKonnect.module.login.ILoginView.STAFFID;

/**
 * on 9/25/2016.
 */

public class LoginPresenter extends BasePresenter implements ILoginPresenter, AsyncLoginInteractor.OnLoginFinishedListener {

    private IAsyncLoginInteractor interactor;
    private ILoginView view;


    public LoginPresenter(ILoginView view) {
        super(view);
        this.view = view;
        this.interactor = new AsyncLoginInteractor(this);
    }

    @Override
    public void onError(String Message) {
        view.showAlert(Message);
    }

    @Override
    public void onSuccess(Object object) {
        if (object != null && object instanceof HoveringDo) {
            HoveringDo hoveringDo = (HoveringDo) object;
            preference.saveIntInPreference(Preference.NotificationCount, hoveringDo.NotificationCount);
            preference.saveIntInPreference(Preference.PostCount, hoveringDo.PostCount);
            preference.saveIntInPreference(Preference.ServiceCount, hoveringDo.ServiceCount);
        } else if (object != null && object instanceof LoginResponse) {
            final LoginResponse loginResponse = (LoginResponse) object;
            FileUtils.deleteDir(new File(AppConstants.CACHE_DIR_PATH));
            if (loginResponse.getEmpModel()!=null) {
                if (!loginResponse.getEmpModel().getStaffNumber().equalsIgnoreCase(preference.getStringFromPreference(Preference.STAFF_NUMBER, "")))
                    FileUtils.deleteDir(new File(AppConstants.PRIVATE_CACHE_DIR_PATH));
                view.showAlert(isArabic ? loginResponse.getStatusMessageAr() : loginResponse.getStatusMessageEn());
                storeUserDetails(loginResponse.getEmpModel());
            }
        }
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

    }

    @Override
    public void submit(final String StaffNumber, String deviceType, String deviceId, String password, String FCMID) {
        if (StringUtils.isEmpty(StaffNumber)) {
            view.showAlert(STAFFID);
        } else if (StringUtils.isEmpty(password)) {
            view.showAlert(PASSWORD);
        } else {
            LoginDo login = new LoginDo();
            login.setDeviceId(deviceId);
            login.setDeviceType(deviceType);
            login.setPassword(password);
            login.setStaffNumber(StaffNumber);
            login.setFCMID(FCMID);
            interactor.signIn(login);
        }

    }

    @Override
    public void postHovering() {
        interactor.postHoveringData();
    }

    @Override
    public void loadData() {

    }
}
