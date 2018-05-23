package com.winit.iKonnect.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.winit.common.CustomBuilder;
import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.FileUtils;
import com.winit.common.util.LogUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.NameIDDo;
import com.winit.iKonnect.module.SplashScreen.ISplashScreenView;
import com.winit.iKonnect.module.SplashScreen.SplashScreenPresentor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;



import static com.winit.common.application.IKonnectApplication.*;

/**
 * Created by Girish Velivela on 11/4/2016.
 */

public class SplashActivity extends BaseActivity implements ISplashScreenView
{

    @Nullable
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Nullable
    @Bind(R.id.ll_dots)
    LinearLayout llDots;

    @Nullable
    @Bind(R.id.btn_skip)
    Button btnSkip;
    @Nullable
    @Bind(R.id.btn_next)
    Button btnNext;
    private Vector<NameIDDo> vecLanguage;
    private String isLanguageSelected = "";
    private SplashScreenPresentor presentor;

    @Override
    protected void initialize() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        inflater.inflate(R.layout.activity_splash, flBody, true);
        ButterKnife.bind(this);
        AppConstants.DATABASE_PATH = getApplication().getFilesDir().toString() + "/";
        IKonnectApplication iKonnectApplication = (IKonnectApplication) getApplicationContext();
        iKonnectApplication.setRoot(FirebaseDatabase.getInstance().getReference().getRoot().child("ChatGroups"));
        initializeControls();
        presentor = new SplashScreenPresentor(SplashActivity.this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        vecLanguage = new Vector<NameIDDo>();
        NameIDDo nameIDDo=new NameIDDo();
        nameIDDo.strName ="English";
        vecLanguage.add(nameIDDo);

        NameIDDo nameIDDo1=new NameIDDo();
        nameIDDo1.strName ="Arabic";
        vecLanguage.add(nameIDDo1);

        /**********************************Default Language English *****************************************/
        preference.saveStringInPreference(Preference.LANGUAGE, AppConstants.ENGLISH);
        /****************************************************************************************************/
        isLanguageSelected = preference.getStringFromPreference(Preference.LANGUAGE, "");

        if(checkPermission()) {
            if (TextUtils.isEmpty(isLanguageSelected)) {
                initializeSplash();
            }else {
                initializeSplash();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLoggedIn = preference.getbooleanFromPreference(Preference.IS_LOGGED_IN,false);
    }
    public void showLanguagePopup() {
        if (vecLanguage != null && vecLanguage.size() > 0) {
            CustomBuilder customBuilder = new CustomBuilder(SplashActivity.this, ""+getString(R.string.select_language), false);
            customBuilder.setSingleChoiceItems(vecLanguage, "",
                    new CustomBuilder.OnClickListener() {
                        @Override
                        public void onClick(CustomBuilder builder,
                                            Object selectedObject) {
                            NameIDDo nameIDDo = (NameIDDo) selectedObject;
                            String selectedLanguage =  nameIDDo.strName;
                            isLanguageSelected = selectedLanguage;
                            preference.saveStringInPreference(Preference.LANGUAGE, selectedLanguage);
                            setLanguage();
                            initializeSplash();
                            builder.dismiss();
                        }
                    });
            customBuilder.show();
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
    @Override
    protected void setTypeFace() {

    }

    protected void initializeControls() {
        int width = preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH,0);
        int height = preference.getIntFromPreference(Preference.DEVICE_DISPLAY_HEIGHT,0);
        if(width == 0 || height == 0){
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            preference.saveIntInPreference(Preference.DEVICE_DISPLAY_WIDTH, displaymetrics.widthPixels);
            preference.saveIntInPreference(Preference.DEVICE_DISPLAY_HEIGHT, displaymetrics.heightPixels);
        }
        AppConstants.initializeTypeFace();
    }

    private void initializeSplash() {

        String token = preference.getStringFromPreference(Preference.GCM_ID,"");
        new Thread(new Runnable() {
            @Override
            public void run() {

                Object favouriteObj = FileUtils.readObjAsFile(AppConstants.FAVOURITE_TYPE);
                if(favouriteObj != null)
                    setCacheObject(AppConstants.FAVOURITE_TYPE,favouriteObj);

                Object filterObj = FileUtils.readObjAsFile(AppConstants.FILTER_TYPE);
                if(filterObj != null)
                    setCacheObject(AppConstants.FILTER_TYPE,filterObj);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getDeviceID();
                        if (isNetworkConnectionAvailable()) {
                            presentor.loadData();
                        } else
                            showCustomDialog(SplashActivity.this, getString(R.string.alert), getString(R.string.No_Network_connection), getString(R.string.OK), "", "gotoDashboard", false);

//                        moveToNextScreen();
                    }
                });
            }
        }).start();
    }

    private final int REQUEST_CODE_ASK_PERMISSIONS = 1000;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        LogUtils.debug(LogUtils.LOG_TAG, "onRequestPermissionsResult");
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0) {
                    for(int grantResult :grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(SplashActivity.this, "All permissions are required.", Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }
                    }
                    if (TextUtils.isEmpty(isLanguageSelected)) {
                        initializeSplash();
                    }else {
                        initializeSplash();
                    }
                } else {
                    Toast.makeText(SplashActivity.this, "All permissions are required.", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    public boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            final ArrayList<String> permissions = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.CAMERA);
            }
            if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.READ_PHONE_STATE);
            }

            if (permissions.size() > 0) {

                int permissionLength = permissions.size();
                final String[] permissionArray = new String[permissionLength];
                for (int i = 0; i < permissionLength; i++) {
                    permissionArray[i] = permissions.get(i);
                }
                ActivityCompat.requestPermissions(SplashActivity.this, permissionArray, REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private void moveToNextScreen(){
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                finish();
                if(preference.getbooleanFromPreference(preference.IS_LOGGED_IN,false)){
                    Intent intent		=	new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(intent);
                }
              /*  else if(TextUtils.isEmpty(isLanguageSelected))
                {
                    Intent intent		=	new Intent(SplashActivity.this, ChooseLangaugeScreen.class);
                    startActivity(intent);
                }
                else if(!preference.getbooleanFromPreference(preference.IS_AGREE_WITH_TERMS,false)){
                    Intent intent		=	new Intent(SplashActivity.this, TermsandCondition.class);
                    intent.putExtra("IAgreeBtnVisible",true);
                    startActivity(intent);
                }*/
                else if(!preference.getbooleanFromPreference(preference.IS_FISRT_TIME_LAUNCH,false)){
                    Intent intent		=	new Intent(SplashActivity.this, AppDetailPagerActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent		=	new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                if(preference.getStringFromPreference(preference.LANGUAGE,"").equalsIgnoreCase("english")){
                    overridePendingTransition(R.anim.slide_left,R.anim.slide_right);
                }
                else{
                    overridePendingTransition(R.anim.slide_right1,R.anim.slide_left1);
                }

            }
        },1500);
    }
    private void getDeviceID(){
        if(!preference.getbooleanFromPreference(preference.IS_AGREE_WITH_TERMS,false)) {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
            String deviceID = telephonyManager.getDeviceId();
            preference.saveStringInPreference(Preference.DEVICEID, deviceID);
        }
    }

    @Override
    public void showAlert(final String type)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                if(type.equalsIgnoreCase(AppConstants.Key_Succes))
                    moveToNextScreen();
            }
        });

    }

    @Override
    public void onLoadFailed() {

    }
}
