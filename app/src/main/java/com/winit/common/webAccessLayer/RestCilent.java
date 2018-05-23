package com.winit.common.webAccessLayer;

import com.winit.common.Preference;

import java.util.TreeMap;

/**
 * Created by Girish Velivela on 8/18/2016.
 *
 * Class used
 *  1) Decides the Request is post or Get service
 *  2) Form an Url that need to request
 *  3) Prepares an headers of
 *
 */

public class RestCilent {

    public Object processRequest(ServiceUrls.ServiceAction serviceAction, String data){

        HttpHelper httpHelper = new HttpHelper();
        String baseUrl = ServiceUrls.MAIN_URL + serviceAction.getValue();
        String methodType = serviceAction.getMethod();
        String request = null;
        TreeMap<String,String> headers = null;
        Preference preference = Preference.getInstance();

        switch (serviceAction) {
            case GET_COMMENTS:
            case GET_CATEGORIES:
            case POST_LOGIN_DATA:
            case GET_LIKES:
            case POST_OTP_REQUEST:
            case Load_CALENDER_DATA:
            case GET_THOUGHT_OF_THE_DAY:
            case GET_FORM_STATUS:
            case POST_SIGN_UP:
            case NOTIFICATION_DETAILS:
            case POST_OTP_VALIDATION:
            case POST_STAFF_REGISTER:
            case GET_FORGET_PASSWORD:
            case GET_CHANGE_PASSWORD:
            case GET_TRACKING_DETAIL:
            case GET_PAYSLIP_DETAIL:
            case GET_INBOX:
            case GET_CHAT_GROUP:
            case GET_LOG_OUT_SERVICE:
            case GET_EMPLOYEE_NAMES:
            case GET_USER_DOCUMENTS:
            case GET_CONTACT_US_LIST:
                headers = new TreeMap<>();
                baseUrl += data;
                headers.put("Accept", "application/json");
                headers.put("UserID", "" + preference.getStringFromPreference(Preference.STAFF_NUMBER, ""));
                headers.put("DeviceID", "" + preference.getStringFromPreference(Preference.GCM_ID, ""));
                break;
            case SINGLE_FEEDS:
                headers = new TreeMap<>();
                baseUrl += data;
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("UserID", "" + preference.getStringFromPreference(Preference.STAFF_NUMBER, ""));
                headers.put("DeviceID", "" + preference.getStringFromPreference(Preference.GCM_ID, ""));
                break;
            case POST_HOVERING_SERVICE:
                headers = new TreeMap<>();
                request = data;
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("UserID", "" + preference.getStringFromPreference(Preference.STAFF_NUMBER, ""));
                headers.put("DeviceID", "" + preference.getStringFromPreference(Preference.GCM_ID, ""));
                headers.put("filters", "[]");
                break;
            case POST_UPDATE_NOTIFICATION_SETTINGS:
                request = data;
                headers = new TreeMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("UserID", "" + preference.getStringFromPreference(Preference.STAFF_NUMBER, ""));
                headers.put("DeviceID", "" + preference.getStringFromPreference(Preference.GCM_ID, ""));
                break;
            case GET_EMP_DETAILS:
            case GET_SERVICE_HISTORY:
                baseUrl += data;
                break;
            default:
                headers = new TreeMap<>();
                request = data;
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("UserID", "" + preference.getStringFromPreference(Preference.STAFF_NUMBER, ""));
                headers.put("DeviceID", "" + preference.getStringFromPreference(Preference.GCM_ID, ""));
                break;
        }

        return httpHelper.sendRequest(baseUrl, methodType, headers, request);
    }

    public Object processAttachementRequest(ServiceUrls.ServiceAction serviceAction, String strFilePath ,int serviceId)
    {
        HttpHelper httpHelper = new HttpHelper();
        String baseUrl = "";
        TreeMap<String,String> headers = null;
        switch (serviceAction)
        {
            case POST_SERVICE_IMAGE :
                headers = new TreeMap<>();
                baseUrl = String.format(ServiceUrls.POST_IMAGE_URL,serviceId);
                headers.put("Accept", "application/json");
                break;
            case UPDATE_PROFILE_PIC :
                headers = new TreeMap<>();
                baseUrl = String.format(ServiceUrls.UPDATE_PROFILE_PIC,serviceId);
                headers.put("Accept", "application/json");
                break;
            default:
                headers = new TreeMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                break;
        }

        return httpHelper.uploadFile(baseUrl,strFilePath,headers);
    }



    public Object processSignatureRequest(ServiceUrls.ServiceAction serviceAction, String strFilePath, int serviceId, int formId)
    {
        HttpHelper httpHelper = new HttpHelper();
        String baseUrl = "";
        TreeMap<String,String> headers = null;
        switch (serviceAction)
        {
            case UPLOAD_SIGNATURE :
                headers = new TreeMap<>();
                baseUrl = String.format(ServiceUrls.UPLOAD_SIGNATURE,serviceId,formId);
                headers.put("Accept", "application/json");
                break;
            default:
                headers = new TreeMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                break;
        }

        return httpHelper.uploadFile(baseUrl,strFilePath,headers);
    }
}
