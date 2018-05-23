package com.winit.common.gcm;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.httpmanager.BitmapUtil;
import com.winit.common.util.StringUtils;
import com.winit.common.webAccessLayer.HttpHelper;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.NotificationDO;
import com.winit.iKonnect.parser.NotificationParser;
import com.winit.iKonnect.ui.activities.ChatActivity;
import com.winit.iKonnect.ui.activities.ChatRoomActivity;
import com.winit.iKonnect.ui.activities.DashboardActivity;
import com.winit.iKonnect.ui.activities.FeedActivity;
import com.winit.iKonnect.ui.activities.InboxActivity;
import com.winit.iKonnect.ui.activities.LoginActivity;
import com.winit.iKonnect.ui.activities.NotificationActivity;
import com.winit.iKonnect.ui.activities.NotificationDetailActivity;
import com.winit.iKonnect.ui.activities.PersonalChatRoomActivity;
import com.winit.iKonnect.ui.activities.TrackServiceActivity;
import com.winit.iKonnect.ui.activities.TrackServiceDetails;
import com.winit.iKonnect.ui.activities.UserNavigationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

/**
 * Created by home on 10/7/2017.
 */

public class IkonnectFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyGcmListenerService";
    public static int NOTIFICATION_ID = 10000;
    public static final int DECODING_MAX_PIXELS_DEFAULT = 600 * 800;
    NotificationDO notificationDO;
    public Preference preference;
    public String title="";
    public String bdy="";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        if(remoteMessage!=null && remoteMessage.getNotification()!=null && remoteMessage.getData()!=null)
        {
            try
            {
                String message = remoteMessage.getData().toString();
                JSONObject jsonNotification = new JSONObject(message);
                if(jsonNotification!=null && jsonNotification.has("message"))
                {
                    NotificationParser notificationParser = new NotificationParser();
                    notificationParser.parse(new StringBuilder(jsonNotification.get("message").toString()));
                    notificationDO = (NotificationDO) notificationParser.getData();
                    if(notificationDO != null) {
                        if (!StringUtils.isEmpty(notificationDO.getImagePath()))
                        {
                            generateNotification(getPoster(ServiceUrls.NOTIFICATION_DATA+notificationDO.getImagePath()));
                        } else {
                            if(notificationDO.getType().equalsIgnoreCase("OtherDevice"))
                            {
                                Intent i = new Intent();
                                i.setClass(this, NotificationActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.putExtra("message", notificationDO.getMsg());
                                i.putExtra("title", title);
                                startActivity(i);
                            }else
                                generateNotification(null);
                        }
                    }
                }
                else
                {
                   /* ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
                    List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
                    if(taskInfo.get(0).topActivity.getClassName().equalsIgnoreCase("com.winit.iKonnect.ui.activities.PersonalChatRoomActivity") || taskInfo.get(0).topActivity.getClassName().equalsIgnoreCase("com.winit.iKonnect.ui.activities.ChatRoomActivity"))
                    {

                    }
                    else
                    {
                        RemoteMessage.Notification  notification = remoteMessage.getNotification();
                        JSONObject jsonBody = new JSONObject(remoteMessage.getData().toString());
                        String body = notification.getBody();
                        String title = notification.getTitle();

                        String ChatKey="",GroupName="",GroupStaffNumber ="",StaffNumber ="";
                        if(jsonBody.has(ConstantExtraKey.ChatGroupKey))
                            ChatKey=jsonBody.getString(ConstantExtraKey.ChatGroupKey);

                        if(jsonBody.has(ConstantExtraKey.ChatGroupName))
                            GroupName=jsonBody.getString(ConstantExtraKey.ChatGroupName);

                        if(jsonBody.has(ConstantExtraKey.GroupStaffNumber))
                            GroupStaffNumber=jsonBody.getString(ConstantExtraKey.GroupStaffNumber);

                        if(jsonBody.has(ConstantExtraKey.StaffNumber))
                            StaffNumber =jsonBody.getString(ConstantExtraKey.StaffNumber);

                        sendNotification(body,title,StaffNumber,GroupStaffNumber ,ChatKey,GroupName);
                    }*/
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getPoster(String imagePath){
        Response response = (Response) new HttpHelper().sendRequest(imagePath, ServiceUrls.METHOD_GET, null, "");
        if (response != null && response.data != null) {
            if(DECODING_MAX_PIXELS_DEFAULT>= response.contentLength)
                return BitmapFactory.decodeStream((InputStream) response.data);
            else
                return BitmapUtil.decodeStream((InputStream) response.data, DECODING_MAX_PIXELS_DEFAULT);
        }
        return null;
    }

    private void sendNotification(String messageBody, String title,String staffNumber,String StaffName,String groupChatKey,String groupName)
    {
        Intent intent=null;

        if(!TextUtils.isEmpty(groupChatKey))
        {
            /*intent = new Intent(this, ChatRoomActivity.class);
            intent.putExtra(ConstantExtraKey.ChatGroupName,groupName);
            intent.putExtra(ConstantExtraKey.ChatGroupKey,groupChatKey);*/
        }
        else if(!TextUtils.isEmpty(staffNumber))
        {
          /*  intent = new Intent(this, PersonalChatRoomActivity.class);
            intent.putExtra(ConstantExtraKey.StaffNumber,staffNumber);*/
        }
        PendingIntent pendingIntent=null;
        if(intent!=null)
            pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(!TextUtils.isEmpty(title)?title:"iKonnect")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);
        if(pendingIntent!=null)
            notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void generateNotification(Bitmap bigPicture)
    {
        Intent intent = new Intent(this, InboxActivity.class);
        preference= Preference.getInstance();
        title=notificationDO.getTitle();
        if(preference.getbooleanFromPreference(preference.IS_LOGGED_IN,false))
        {
            if(notificationDO.getType().equalsIgnoreCase(AppConstants.Notification_ServiceRequest))
            {
                intent = new Intent(this, TrackServiceDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(ConstantExtraKey.NOTIFICATION_OBJECT,notificationDO);
                title = notificationDO.getMsg();
            }
            else if(notificationDO.getType().equalsIgnoreCase(AppConstants.Notification_POST_TYPE))
            {
                intent = new Intent(this, FeedActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(ConstantExtraKey.NOTIFICATION_OBJECT,notificationDO);
            }

            else if(notificationDO.getType().equalsIgnoreCase(AppConstants.Notification))
            {
                intent = new Intent(this, NotificationDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(ConstantExtraKey.IS_FROM_NOTIFICATION,true);
                intent.putExtra(ConstantExtraKey.NOTIFICATION_OBJECT,notificationDO);
            }
        }
        else
//            intent = new Intent(this, UserNavigationActivity.class);
            intent = new Intent(this, LoginActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(!TextUtils.isEmpty(title)?title:"iKonnect")
                .setContentText(notificationDO.getMsg())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(bigPicture != null)
        {
            NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
            notiStyle.bigPicture(bigPicture);
            notiStyle.setSummaryText(notificationDO.getMsg());
            notificationBuilder.setStyle(notiStyle);
        }
//        notificationManager.notify(notificationDO.getId() , notificationBuilder.build());
        notificationManager.notify(NOTIFICATION_ID++ , notificationBuilder.build());
    }

}
