package com.winit.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.winit.common.application.IKonnectApplication;


@SuppressLint("CommitPrefEdits")
public class Preference {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor edit;
    private static Preference preference;

    public static final String DEVICE_DISPLAY_WIDTH = "DEVICE_DISPLAY_WIDTH";
    public static final String DEVICE_DISPLAY_HEIGHT = "DEVICE_DISPLAY_HEIGHT";
    public static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String DEVICEID = "DEVICEID";
    public static final String FCMID = "FCMID";
    public static final String GCM_ID = "GCM_ID";

    public static final String IS_FISRT_TIME_LAUNCH = "IS_FISRT_TIME_LAUNCH";
    public static final String IS_AGREE_WITH_TERMS = "IS_AGREE_WITH_TERMS";
    public static final String IS_FROM_FORGOT_PASSWORD = "IS_FROM_FORGOT_PASSWORD";

    /*--------------------------------------------------------------------------------*/
    /* For Staff User Detail information dont Touch it (Rahul) */
    /*--------------------------------------------------------------------------------*/
    public static final String BAND = "BAND";
    public static final String COMPANY_CODE = "COMPANY_CODE";
    public static final String CONFIRMATION = "CONFIRMATION";
    public static final String COST_CTR = "COST_CTR";
    public static final String ELIGIBILITY_FOR_HRA = "ELIGIBILITY_FOR_HRA";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String HIRE_DATE = "HIRE_DATE";
    public static final String ID_NUMBER_EMIRATES_SELF = "ID_NUMBER_EMIRATES_SELF";
    public static final String ID_NUM_EMIRATES_SELF_EXPIREDATE = "ID_NUM_EMIRATES_SELF_EXPIREDATE";
    public static final String ID_NUMBER_PASSPORT_SLEF = "ID_NUMBER_PASSPORT_SLEF";
    public static final String ID_NUMBER_PASSPORT_SLEF_EXPIREDATE = "ID_NUMBER_PASSPORT_SLEF_EXPIREDATE";
    public static final String ID_NUMBER_RESIDENCE_VISA = "ID_NUMBER_RESIDENCE_VISA";
    public static final String ID_NUMBER_RESIDENCE_VISA_EXPIREDATE = "ID_NUMBER_RESIDENCE_VISA_EXPIREDATE";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String TRANSPORT_ALLOWANCE = "TRANSPORT_ALLOWANCE";
    public static final String ZZ_SECTOR = "ZZ_SECTOR";
    public static final String LEAVE_BALANCE = "LEAVE_BALANCE";
    public static final String IS_ACTIVE = "IS_ACTIVE";
    public static final String COMPANY_ACCOMODATION = "COMPANY_ACCOMODATION";
    public static final String BANK_LOAN = "BANK_LOAN";

    public static final String STAFF_EMAIL = "STAFF_EMAIL";
    public static final String STAFF_GRADE = "STAFF_GRADE";
    public static final String STAFF_MOBILE = "STAFF_MOBILE";
    public static final String STAFF_NATIONALITY = "STAFF_NATIONALITY";
    public static final String STAFF_ORGUNIT = "STAFF_ORGUNIT";
    public static final String STAFF_PERSONAL_AREA = "STAFF_PERSONAL_AREA";
    public static final String STAFF_PERSONAL_SUB_AREA = "STAFF_PERSONAL_SUB_AREA";
    public static final String STAFF_PHOTO_URL = "STAFF_PHOTO_URL";
    public static final String STAFF_POSITION = "STAFF_POSITION";
    public static final String STAFF_RELIGION = "STAFF_RELIGION";
    public static final String STAFF_NUMBER = "STAFF_NUMBER";
    public static final String STAFF_ID = "STAFF_ID";
    public static final String STAFF_NAME = "STAFF_NAME";
    public static final String STAFF_STATUS = "STAFF_STATUS";
    public static final String STAFF_WORK_COUNTRY = "STAFF_WORK_COUNTRY";
    public static final String THOUGHT_OF_THE_DAY = "THOUGHT_OF_THE_DAY";
    public static final String THOUGHT_OF_THE_DAY_ARABIC = "THOUGHT_OF_THE_DAY_ARABIC";
    public static final String NotificationCount = "NotificationCount";
    public static final String PostCount = "PostCount";
    public static final String ServiceCount = "ServiceCount";
    public static final String PersonalChatCount = "PersonalChatCount";
    public static final String GroupChatCount = "GroupChatCount";


    public static final String EMPLOYEE_SALARY = "EMPLOYEE_SALARY";
    public static final String Full_Name = "FULL_NAME";
    public static final String DEPARTMENT = "DEPARTMENT";
    public static final String FUNCTIONAL_AREA = "FUNCTIONAL_AREA";
    public static final String OUTSTANDING_HRA = "OUTSTANDINGHRA;";
    public static final String HOUSING_CUTOFF="HOUSING_CUTOFF";
    public static final String DOB="DOB";
    public static final String JOINING_DATE="JOINING_DATE";
    public static final String DIVISION="DIVISION";
    public static final String SPONSOR="SPONSOR";
    public static final String BANK_NAME="BANK_NAME";
    public static final String IBAN="IBAN";
    public static final String ACCOUNT_NUMBER="ACCOUNT_NUMBER";


    public static final String ATTACHMENT_COUNT = "ATTACHMENT_COUNT";
	/*--------------------------------------------------------------------------------*/

    public Preference(Context context) {
        if (preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (edit == null)
            edit = preferences.edit();
    }

    public static Preference getInstance() {
        if (preference == null)
            return preference = new Preference(IKonnectApplication.mContext);
        else
            return preference;
    }

    public void saveStringInPreference(String strKey, String strValue) {
        edit.putString(strKey, strValue);
        commitPreference();
    }

    public void saveIntInPreference(String strKey, int value) {
        edit.putInt(strKey, value);
        commitPreference();
    }

    public void saveBooleanInPreference(String strKey, boolean value) {
        edit.putBoolean(strKey, value);
        commitPreference();
    }

    public void saveLongInPreference(String strKey, Long value) {
        edit.putLong(strKey, value);
        commitPreference();
    }

    public void saveDoubleInPreference(String strKey, String value) {
        edit.putString(strKey, value);
        commitPreference();
    }

    public void removeFromPreference(String strKey) {
        edit.remove(strKey);
        commitPreference();
    }

    public void commitPreference() {
        edit.commit();
    }

    public String getStringFromPreference(String strKey, String defaultValue) {
        return preferences.getString(strKey, defaultValue);
    }

    public boolean getbooleanFromPreference(String strKey, boolean defaultValue) {
        return preferences.getBoolean(strKey, defaultValue);
    }

    public int getIntFromPreference(String strKey, int defaultValue) {
        return preferences.getInt(strKey, defaultValue);
    }

    public double getDoubleFromPreference(String strKey, double defaultValue) {
        return Double.parseDouble(preferences.getString(strKey, "" + defaultValue));
    }

    public long getLongInPreference(String strKey) {
        long value = 0;
        try {
            value = preferences.getLong(strKey, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public void clearPreferences() {
        edit.clear();
        edit.commit();
    }

}
