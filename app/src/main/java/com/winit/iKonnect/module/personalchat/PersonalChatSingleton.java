package com.winit.iKonnect.module.personalchat;

import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpHelper;
import com.winit.common.webAccessLayer.ServiceUrls;

import java.util.TreeMap;

/**
 * Created by Ashoka.Reddy on 7/19/2017.
 */

public class PersonalChatSingleton {
    private static final PersonalChatSingleton ourInstance = new PersonalChatSingleton();

    public static PersonalChatSingleton getInstance() {
        return ourInstance;
    }

    private PersonalChatSingleton() {
    }
    public void notifiedData(final String msg,final String userName,final String UserID, final String FCMTOKEN)
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                TreeMap<String,String> headers  = new TreeMap<>();;
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "key="+ ServiceUrls.SERVER_KEY);
                HttpHelper httpHelper = new HttpHelper();
                httpHelper.sendRequest("https://fcm.googleapis.com/fcm/send", "POST", headers, BuildJsonRequest.formChatRequest(msg, userName,UserID, FCMTOKEN));
            }
        }).start();

    }
    public void notifiedDataforGrp(final String msg,final String grpName, final String titile,final String StaffNumber)
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                TreeMap<String,String> headers  = new TreeMap<>();;
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "key="+ ServiceUrls.SERVER_KEY);
                HttpHelper httpHelper = new HttpHelper();
                httpHelper.sendRequest("https://fcm.googleapis.com/fcm/send", "POST", headers, BuildJsonRequest.formChatRequestforGrp(msg, titile, grpName, StaffNumber));
            }
        }).start();

    }

}
