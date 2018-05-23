package com.winit.common.webAccessLayer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.winit.common.Preference;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.dataobject.ContactUSDo;
import com.winit.iKonnect.dataobject.LoginDo;
import com.winit.iKonnect.dataobject.PostFeedActionDO;
import com.winit.iKonnect.dataobject.PostFeedDO;
import com.winit.iKonnect.dataobject.request.ServiceRequest;
import com.winit.iKonnect.dataobject.UpdateSettingNotificationDO;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by Girish Velivela on 5/16/2017.
 */

public class BuildJsonRequest {

    public static String getFeedsParams(String UserCode, String filters, String dateTime, int favourite) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(UserCode)
                .append("&filters=").append(filters)
                .append("&datetime=").append(StringUtils.encode(dateTime))
                .append("&favourite=").append(favourite);
        return builder.toString();
    }
    public static String getLogOutResponse(String StaffNumber, String DeviceId) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(StaffNumber)
                .append("&deviceId=").append(DeviceId);
        return builder.toString();
    }
    public static String getCalenderDetail(String StaffNumber, String fromDateTimeInUTC, String toDateTimeInUTC) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(StaffNumber)
                .append("&fromDateTimeInUTC=").append(fromDateTimeInUTC)
                .append("&toDateTimeInUTC=").append(StringUtils.encode(toDateTimeInUTC));
        return builder.toString();
    }

    public static String getPostParams(int postId) {
        StringBuilder builder = new StringBuilder();
        builder.append("?cmspostId=").append(postId);
        return builder.toString();
    }

    public static String getChatGroupParams(String staffNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(staffNumber);
        return builder.toString();
    }
    public static String getStaffDetail(String staffNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("?searchKey=").append(staffNumber);
        builder.append("&id=0");
        return builder.toString();
    }

    public static String getInboxParams(String staffNumber,String dateTime) {
        StringBuilder builder = new StringBuilder();
        builder.append("?staffNumber=").append(staffNumber)
                .append("&datetime=").append(dateTime);
        return builder.toString();
    }
    public static String getSinglePost(String id) {
        StringBuilder builder = new StringBuilder();
        builder.append("?id=").append(""+id);
        return builder.toString();
    }

    public static String getPostActionRequest(PostFeedActionDO postFeedActionDO) {
        Gson gson = new Gson();
        Type type = new TypeToken<PostFeedActionDO>() {}.getType();
        return /*"";*/gson.toJson(postFeedActionDO, type);
    }

    public static String getPostFeedRequest(PostFeedDO postFeedDO) {
        Gson gson = new Gson();
        Type type = new TypeToken<PostFeedDO>() {}.getType();
        return gson.toJson(postFeedDO, type);
    }

    public static String getPostFormRequest(ServiceRequest serviceRequest) {
        Gson gson = new Gson();
        Type type = new TypeToken<ServiceRequest>() {}.getType();
        return gson.toJson(serviceRequest, type);
    }
    public static String getPostFormRequest(LoginDo logindo) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(StringUtils.encode(logindo.getStaffNumber()))
                .append("&deviceType=").append("Android")
                .append("&deviceId=").append(logindo.getDeviceId())
                .append("&FCMDeviceToken=").append(logindo.getFCMID())
                .append("&password=").append(logindo.getPassword());
        return builder.toString();
    }
    public static String getPostStaffRegister(String staffNumber,String deviceType,String deviceId,String password) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(staffNumber)
                .append("&deviceType=").append(deviceType)
                .append("&deviceId=").append(deviceId)
                .append("&password=").append(password)
                .append("&FCMDeviceToken=").append(deviceId);
        return builder.toString();
    }
    public static String getPostOTPRequest(String staffNumber,String mobile,String workCountry) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(staffNumber)
                .append("&mobile=").append(mobile)
                .append("&workCountry=").append(workCountry);
        return builder.toString();
    }
    public static String getChangePasswordRequest(String staffNumber,String newPassword,String oldPassword) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(staffNumber)
                .append("&password=").append(newPassword)
                .append("&oldpassword=").append(oldPassword);
        return builder.toString();
    }
    public static String getPostForgetPasswordRequest(String staffNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(staffNumber);
        return builder.toString();
    }
    public static String getStaffno(String staffNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(staffNumber);

        return builder.toString();
    }
    public static String getPostSignUPRequest(String staffNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(staffNumber);
        return builder.toString();
    }

    public static String getPostServiceHistroyRequest(int  id) {
        StringBuilder builder = new StringBuilder();
        builder.append("?ServiceId=").append(id);
        return builder.toString();
    }
    public static String getPostDocumentsRequest(String  id) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(id);
        return builder.toString();
    }
    public static String getFilterEmployee(String staffNumber, int pos) {
        StringBuilder builder = new StringBuilder();
        builder.append("?searchKey=").append(staffNumber);
        builder.append("&id=").append(pos);
        return builder.toString();


    }
    public static String getEmployeeList(int startIndex, int endIndex, String searchtext) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StartIndex=").append(startIndex);
        builder.append("&EndIndex=").append(endIndex);
        builder.append("&SearchString=").append(searchtext);
        return builder.toString();


    }
    public static String getOtpValidateRequest(String staffNumber,String mobile,String otp) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(staffNumber)
                .append("&mobile=").append(mobile)
                .append("&otp=").append(otp);
        return builder.toString();


    }
    public static String getPostFormRequest(ContactUSDo contactusdo) {
        Gson gson = new Gson();
        Type type = new TypeToken<ContactUSDo>() {}.getType();
        return gson.toJson(contactusdo, type);
    }

    public static String getTrackServicesParams(String staffNumber, String status) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(staffNumber)
                .append("&status=").append(status);
        return builder.toString();
    }
    public static String getPaySlipParams(String staffNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("?StaffNumber=").append(staffNumber);
        return builder.toString();
    }
    public static String getNotificationUpdateService(UpdateSettingNotificationDO updateSettingNotificationDO) {
        Gson gson = new Gson();
        Type type = new TypeToken<UpdateSettingNotificationDO>() {}.getType();
        return gson.toJson(updateSettingNotificationDO, type);
    }

    public static String getFormStatus() {
        StringBuilder builder = new StringBuilder();
        builder.append("");
        return builder.toString();
    }
    public static String formChatRequest(String msg,String userName,String UserID, String FCMTOKEN)
    {
        try
        {
            Preference preference = Preference.getInstance();
            JSONObject jsonMain = new JSONObject();
            JSONObject jsonNotification = new JSONObject();
            JSONObject jsonData = new JSONObject();

            jsonMain.put("priority","high");
            jsonMain.put("to",FCMTOKEN);

            jsonNotification.put("body",msg);
            jsonNotification.put("title",userName);
            jsonNotification.put("click_action","OPEN_ACTIVITY_1");


            jsonData.put("StaffNumber",UserID);
            jsonMain.put("notification",jsonNotification);
            jsonMain.put("data",jsonData);

            return jsonMain.toString();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
    public static String formChatRequestforGrp(String msg,String titile, String grpName,String StaffNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n" +
                " \"priority\" : \"high\",\n" +
                "  \"to\": \""+"/topics/"+grpName+"\",\n" +
                "  \n" +
                "   \"notification\": {\n" +
                "    \"GroupName\": \""+grpName+"\",\n" +
                "    \"StaffNumber\":\"\",\n" +
                "    \"text\":\""+msg+"\"\n" +","+
                "    \"title\":\""+titile+"\","+
                "    \"GroupStaffNumber\":\""+StaffNumber+"\""+
                "  }\n" +
                "}");
        return builder.toString();
    }
}
