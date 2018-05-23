package com.winit.common.constant;

import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;

import com.winit.common.application.IKonnectApplication;
import com.winit.iKonnect.dataobject.CardCancellationDO;
import com.winit.iKonnect.dataobject.ServiceDO;

import java.io.File;
import java.util.ArrayList;

/*
*
* This class has constants that used thourgh out the application.
*
* There are group constants with commented title.
* Declare a new constant in that group.
*
*  */
public class AppConstants {
    //Database Constants Start
    public static String DATABASE_PATH = "";
    public static String DATABASE_NAME = "EConnect.sqlite";
    public static final int DB_INSERT_SUCCESS = 1;
    public static final int DB_INSERT_FAIL = -1;
    //Database Constants End

    public static final int DONE = 200;
    public static final int LOGOUT_ERROR_CODE = 451;
    public static final String Logout = "Logout";


    // Typface Constant Start
    public static Typeface REGULAR, BOLD, MEDIUM, LIGHT, THIN, EXTRA_BOLD;
    // Typface Constant End


    public static String STORAGE_PATH = Environment.getExternalStorageDirectory() + "/.iKonnect";
    public static String DOWNLOAD_PATH = Environment.getExternalStorageDirectory() + "/iKonnect";
    public static String CACHE_DIR_PATH = Environment.getExternalStorageDirectory() + "/.iKonnectFiles/Cache";
    public static String PRIVATE_CACHE_DIR_PATH = IKonnectApplication.mContext.getFilesDir().toString() + "/Cache";

    public static String INTERNET_ERROR = "internet_error";
    public static String ARABIC = "Arabic";
    public static String ENGLISH = "English";
    public static String NATIONALITY = "OMANI";
    public static String AED = "AED";
    public static String STAFF_WORK_COUNTRY = "Oman";
    public static String OMR = "OMR";

    public static String ACTION_LOGOUT = "com.winit.ikonnect.ACTION.LOGOUT";
    public static String Key_Succes = "Key_Succes";


    /* BroadCast Action Start*/
    public static String REFRESH_FEEDS = "com.winit.ikonnect.refresh_feeds";
    public static String RESET_FEEDS = "com.winit.ikonnect.reset_feeds";
    public static String HR_SERVICES = "com.winit.ikonnect.hr_services";
    /* BroadCast Action End*/

    /* Request Codes Start*/
    public static final int SHARE_REQUEST_CODE = 101;
    public static final int DOWNLOAD_CODE = 1001;
    public static final String SERVER_KEY = "AAAAYi8a3fM:APA91bFugsQEaagKebHEN9d_tWPgC8MhVSRtNMVfrGYjBeCVCyjWpsbyoaY4YiZcoNMnBAFy31MaYIDV6j4ZilAEsw1Or5j0mpsbqj6fkGGl8qkGpSId0SM0DCbE1AZ717thHj3Dn6fh";
    /* Request Codes End*/

    final public static String DEVICE_TYPE = "Android";
    final public static String COMMENT_TYPE = "comments";
    final public static String FAVOURITE_TYPE = "favourite";
    final public static String FILTER_TYPE = "filter";
    final public static String ARR_FILTER_TYPE = "arrfilter";

    public static String Notification_ServiceRequest = "ServiceRequest";
    public static String Notification_POST_TYPE = "POST";
    public static String Notification = "Notification";
    public static final String SIGNATURE = "Signature";
    public static int SERVICE_ID = -1;


    //storing CardCancellationData

    public static CardCancellationDO cardCancellationDO;
    public static ServiceDO serviceDO;
    public static boolean popupFlag = false;
    public static boolean cancellationFlag = false;
    public static int count = 0;
    public static ArrayList<String>arrAttachments=new ArrayList<>();

    //track
    public static int track = 0;
    public static int LoginCount = 0;


    //for NavigatingTo TrackDetails page
    public static boolean navigation;


    public static void initializeTypeFace() {
        try {
            REGULAR = Typeface.createFromAsset(IKonnectApplication.mContext.getAssets(), "SanFranciscoDisplay_Regular.otf");
            BOLD = Typeface.createFromAsset(IKonnectApplication.mContext.getAssets(), "SanFranciscoDisplay_Bold.otf");
            MEDIUM = Typeface.createFromAsset(IKonnectApplication.mContext.getAssets(), "SanFranciscoDisplay_Medium.otf");
            LIGHT = Typeface.createFromAsset(IKonnectApplication.mContext.getAssets(), "SanFranciscoDisplay_Light.otf");
            EXTRA_BOLD = Typeface.createFromAsset(IKonnectApplication.mContext.getAssets(), "SanFranciscoDisplay_Bold.otf");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static File getOutputImageFile(String folder) {
        File captureImagesStorageDir = new File(Environment.getExternalStorageDirectory() + "/IKonnect/" + folder);
        if (!captureImagesStorageDir.exists()) {
            if (!captureImagesStorageDir.mkdirs()) {
                Log.d("iKonnect", "Oops! Failed create ");
                return null;
            }
        }

        String timestamp = System.currentTimeMillis() + "";
        File imageFile = new File(captureImagesStorageDir.getPath() + File.separator
                + "CAPTURE_" + timestamp + ".jpg");

        return imageFile;
    }


    public static File getSignatureImageFile(String folder) {
        File captureImagesStorageDir = new File(Environment.getExternalStorageDirectory() + "/iKonnect/" + folder);
        if (!captureImagesStorageDir.exists()) {
            if (!captureImagesStorageDir.mkdirs()) {
                Log.d("iKonnect", "Oops! Failed create ");
                return null;
            }
        }

        String timestamp = System.currentTimeMillis() + "";
        File imageFile = new File(captureImagesStorageDir.getPath() + File.separator
                + "signature" + ".jpg");
/*
		File imageFile = new File(captureImagesStorageDir.getPath() + File.separator
				+ "CAPTURE_" + timestamp + ".jpg");
*/

        return imageFile;
    }
}
