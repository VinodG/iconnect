package com.winit.common.webAccessLayer;


import android.widget.Toast;

import com.winit.common.application.IKonnectApplication;
import com.winit.common.util.LogUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Girish Velivela on 11-07-2016.
 */
public class ServiceUrls {


    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";


    //    Live URL's
    public static final String LIVE_MAIN_URL = "http://dev4.winitsoftware.com/eConnectApi/api/eConnect/";

    /********************************************IKONNECT URLS Start *****************************************************/

    /************************************** Test Local URLS ****************************************************/

    //    public static final String PROFILE_PIC           = "http://192.168.154.16/iKonnectapiNew/Data/ProfilePics/";
    //    public static final String FEEDS_DATA            = "http://192.168.154.16/IkonnectNew2/Data/CmsPost/";
    //    public static final String NOTIFICATION_DATA     = "http://192.168.154.16/IkonnectNew2/Data/Notification/";
    //    http://192.168.154.16/IkonnectImages/
    //    public static final String POST_IMAGE_URL               = "http://192.168.154.16/iKonnectapiNew/UploadImage2.aspx?module=servicerequest&Serviceid=0&Formid=0&id=%s";
    //    public static final String UPDATE_PROFILE_PIC           = "http://192.168.154.16/ikonnectnew2/UploadImage.aspx?module=ProfilePics&Serviceid=0&Formid=0&id=%s";
    //    public static final String UPLOAD_SIGNATURE             = "http://192.168.154.16/ikonnectnew2/UploadImage.aspx?id=0&Serviceid=%s&module=Signature&Formid=%s";


    public static final String MAIN_URL                     = "http://192.168.154.16/iKonnectapiNew/api/ikonnect/";
    public static final String PROFILE_PIC                  = "http://192.168.154.16/IkonnectImages/";
    public static final String FEEDS_DATA                   = "http://192.168.154.16/IkonnectImages/";
    public static final String NOTIFICATION_DATA            = "http://192.168.154.16/IkonnectImages/";
    public static final String POST_IMAGE_URL               = "http://192.168.154.16/iKonnectapiNew/UploadImage.aspx?module=servicerequest&id=%s";
    public static final String UPDATE_PROFILE_PIC           = "http://192.168.154.16/iKonnectapiNew/UploadImage2.aspx?module=ProfilePics&Serviceid=0&Formid=0&id=%s";
    public static final String UPLOAD_SIGNATURE             = "http://192.168.154.16/iKonnectapiNew/UploadImage2.aspx?id=0&Serviceid=%s&module=Signature&Formid=%s";
    public static final String DOCUMENTS_URL                = "http://192.168.154.16/ikonnectnew2/";
    public static final String ATTACHMENTS_HISTORY_URL      = "http://192.168.154.16/IkonnectImages/";
    public static final String TermsAndCondition            = "http://ikonnect.winitsoftware.com/Data/TermsConditions/Terms.html";



    /************************************** Test Local URLS End****************************************************/




    /************************************** Test Service URLS Start ****************************************************/

//    public static final String PROFILE_PIC                  = "http://ikonnect.winitsoftware.com/Data/ProfilePics/";
//    public static final String FEEDS_DATA                   = "http://ikonnect.winitsoftware.com/Data/CmsPost/";
//    public static final String NOTIFICATION_DATA            = "http://ikonnect.winitsoftware.com/Data/Notification/";
//    public static final String DOCUMENTS_URL                = "http://ikonnect.winitsoftware.com/";
//    public static final String DOCUMENTS_URL                = "http://ikonnectApi.winitsoftware.com/";
//    public static final String ATTACHMENTS_HISTORY_URL      = "http://ikonnectApi.winitsoftware.com/";



 /*   public static final String MAIN_URL                     = "http://ikonnectApi.winitsoftware.com/api/Ikonnect/";
    public static final String PROFILE_PIC                  = "http://ikonnectSap.winitsoftware.com/";
    public static final String FEEDS_DATA                   = "http://ikonnectSap.winitsoftware.com/";
    public static final String NOTIFICATION_DATA            = "http://ikonnectSap.winitsoftware.com/";
    public static final String POST_IMAGE_URL               = "http://ikonnectapi.winitsoftware.com/UploadImage.aspx?id=%s&module=servicerequest";
    public static final String UPDATE_PROFILE_PIC           = "http://ikonnectapi.winitsoftware.com/UploadImage2.aspx?module=ProfilePics&Serviceid=0&Formid=0&id=%s";
    public static final String UPLOAD_SIGNATURE             = "http://ikonnectapi.winitsoftware.com/UploadImage2.aspx?id=0&Serviceid=%s&module=Signature&Formid=%s";
    public static final String DOCUMENTS_URL                = "http://ikonnectSAP.winitsoftware.com/";
    public static final String ATTACHMENTS_HISTORY_URL      = "http://ikonnect.winitsoftware.com/";
    public static final String TermsAndCondition            = "http://ikonnect.winitsoftware.com/Data/TermsConditions/Terms.html";
*/

    /************************************** Test Service URLS End****************************************************/




    /************************************** Live URLS Start****************************************************/


  /*  public static final String MAIN_URL                     = "http://172.16.7.217:9090/api/Ikonnect/";
    public static final String PROFILE_PIC                  = "http://172.16.7.217:9091/IkonnectImages/";
    public static final String FEEDS_DATA                   = "http://172.16.7.217:9091/IkonnectImages/";
    public static final String NOTIFICATION_DATA            = "http://172.16.7.217:9091/IkonnectImages/";
    public static final String POST_IMAGE_URL               = "http://172.16.7.217:9090/UploadImage.aspx?id=%s&module=servicerequest";
    public static final String UPDATE_PROFILE_PIC           = "http://172.16.7.217:9090/UploadImage2.aspx?module=ProfilePics&Serviceid=0&Formid=0&id=%s";
    public static final String UPLOAD_SIGNATURE             = "http://172.16.7.217:9090/UploadImage2.aspx?id=0&Serviceid=%s&module=Signature&Formid=%s";
    public static final String DOCUMENTS_URL                = "http://172.16.7.217:9091/IkonnectImages/";
    public static final String ATTACHMENTS_HISTORY_URL      = "http://172.16.7.217:9091/IkonnectImages/";
    public static final String TermsAndCondition             = "http://172.16.7.217:9090/Data/TermsConditions/Terms.html";
*/


    /************************************** Live URLS End****************************************************/


    /*********************************** iKonnect Urls end**************************************************/

//    public static final String TermsAndCondition = "http://ikonnect.winitsoftware.com/Data/TermsConditions/Terms.html";

    public static final String TermsAndCondition_Ar = "http://ikonnect.winitsoftware.com/Data/TermsConditions/Terms.html";
    public static final String SERVER_KEY = "AAAABpv-O54:APA91bHtB8D5hgok9TqH0wt27oEUhJekdkeGvkk0zkW0iVNsl2Lpy4pCtuEfFA1fA1lors0BV5sUsBWH4ZyAmEoN1Yx2yBFLislhl3t_41dQac0jJjLG_70pVVQ0Rm78anpexUb78rei";

    // always order need to be followed
    public enum
    ServiceAction {
        NONE("", false, ""),
        LOGIN("AuthenticateUser", false, METHOD_POST),
        FEEDS("GetPostsFeed", false, METHOD_POST),
        SINGLE_FEEDS("GetPostFeed", false, METHOD_GET),
        GET_COMMENTS("GetPostComments", false, METHOD_GET),
        GET_CATEGORIES("CategoryList", false, METHOD_GET),
        GET_LIKES("GetPostLikes", false, METHOD_GET),
        NEW_SERVICE_REQUEST("NewServiceRequest", false, METHOD_POST),
        POST_FEED_ACTION("PostAction", false, METHOD_POST),
        POST_LOGIN_DATA("StaffLogin", false, METHOD_GET),
        POST_SIGN_UP("StaffSignUp", false, METHOD_GET),
        POST_OTP_REQUEST("StaffOTP", false, METHOD_GET),
        GET_TRACKING_DETAIL("GetListOfServiceRequests", false, METHOD_GET),
        GET_PAYSLIP_DETAIL("GetRFCData", false, METHOD_GET),
        NOTIFICATION_DETAILS("NotificationDetails", false, METHOD_GET),
        Load_CALENDER_DATA("MyCalendar", false, METHOD_GET),
        POST_CONTACT_US("PostContactUs", false, METHOD_POST),
        POST_OTP_VALIDATION("ValidateOTP", false, METHOD_GET),
        POST_STAFF_REGISTER("StaffRegister", false, METHOD_GET),
        UPLOAD_SIGNATURE("UploadSignature", false, METHOD_POST),
        POST_SERVICE_IMAGE("PostImage", false, METHOD_GET),
        UPDATE_PROFILE_PIC("UpdateProfilePic", false, METHOD_GET),
        GET_THOUGHT_OF_THE_DAY("getQuote", false, METHOD_GET),
        GET_FORM_STATUS("ServiceList", false, METHOD_GET),
        GET_FORGET_PASSWORD("ForgotPassword", false, METHOD_GET),
        GET_CHAT_GROUP("GetChatGroups", false, METHOD_GET),
        GET_INBOX("MyEnotice", false, METHOD_GET),
        GET_CHANGE_PASSWORD("ChangePassword", false, METHOD_GET),
        GET_WELCOME_MESSAGE("GetWelcomeMessage", false, METHOD_GET),
        GET_EMPLOYEE_NAMES("ChatMembers", false, METHOD_GET),
        GET_LOG_OUT_SERVICE("Logout", false, METHOD_GET),
        POST_HOVERING_SERVICE("HoveringService", false, METHOD_POST),
        POST_UPDATE_NOTIFICATION_SETTINGS("updateNotificationSetting", false, METHOD_POST),
        GET_SERVICE_HISTORY("GetServiceDetails", false, METHOD_GET),
        GET_USER_DOCUMENTS("GetUserDocuments", false, METHOD_GET),
        GET_CONTACT_US_LIST("GetTopicList", false, METHOD_GET),
        GET_EMP_DETAILS("GetEmpDetails", false, METHOD_GET);


        private String value;
        private String method;
        private boolean isPostEnable;

        ServiceAction(String value, boolean isPostEnable, String method) {
            this.value = value;
            this.method = method;
            this.isPostEnable = isPostEnable;
        }

        public boolean getIsPostEnable() {
            return isPostEnable;
        }

        public String getValue() {
            return value;
        }

        public String getMethod() {
            return method;
        }

        private static final Map<String, ServiceAction> hmSoapActions = new LinkedHashMap<>();

        static {
            for (ServiceAction soapAction : ServiceAction.values()) {
                if (LogUtils.isLogEnable && hmSoapActions.containsKey(soapAction.value)) {
                    Toast.makeText(IKonnectApplication.mContext, "Duplicate SoapAction " + soapAction.value, Toast.LENGTH_LONG).show();
                }
                hmSoapActions.put(soapAction.value, soapAction);
            }
        }
    }

}
