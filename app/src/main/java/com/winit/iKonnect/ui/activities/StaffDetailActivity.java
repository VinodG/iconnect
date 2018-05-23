package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.StringUtils;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.AttachmentsDO;
import com.winit.iKonnect.module.staffdetail.StaffDetailPresentor;
import com.winit.iKonnect.module.staffdetail.iStaffDetailPresentor;
import com.winit.iKonnect.module.staffdetail.iStaffDetailView;

import java.io.File;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by namashivaya.gangishe on 5/29/2017.
 */

public class StaffDetailActivity extends BaseActivity implements iStaffDetailView
{
    private TextView  tvEmailId, tvMobile, tvReligion, tvCountry, tvStatus, tvNationality,tvGr,orgunit,tvps,
    tvUser_Id;
    private CircleImageView ivUserPic;
    private String isFrom;
    private Bundle bundle;
    private Button btn_proceed;
    private TextView tvUser_Name,tvUserDesignation,tvmobile,tvmail,
            tvworkcountry,
            tvGrade,
            tvna,
            Religion,
            tvOrgUnit,
            tvarea;
    private iStaffDetailPresentor iStaff;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.user_info, flBody, true);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        setToolbarTitle(""+getString(R.string.my_profile));
        if(bundle.getString("isFrom")!=null)
        isFrom = bundle.getString("isFrom");
        if(!StringUtils.isEmpty(isFrom)&&isFrom.equalsIgnoreCase("signUp")) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        initializeControls();
        setUpNavigationView();
        setStaffDetail();
        iStaff = new StaffDetailPresentor(StaffDetailActivity.this);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void changeImage(View v)
    {
        CaptureImageForProfile();
    }

    public void setAttachment(String path, AttachmentsDO fromDetails)
    {
        try{
            showLoader("Please wait!");
            File image = new File(path);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize=10;
            Bitmap bitmap=null;
            bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
            Matrix matrix = new Matrix();
//            matrix.postRotate(90);
            bitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            ivUserPic.setImageBitmap(bitmap);
            ivProfile.setImageBitmap(bitmap);
            iStaff.postProfilePicture(path, StringUtils.getInt(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setStaffDetail() {
        tvEmailId.setText(""+preference.getStringFromPreference(preference.STAFF_EMAIL,"N/A"));
        tvMobile.setText(""+preference.getStringFromPreference(preference.STAFF_MOBILE,"N/A"));
        tvReligion.setText(""+preference.getStringFromPreference(preference.STAFF_RELIGION,"N/A"));
        tvCountry.setText(""+preference.getStringFromPreference(preference.STAFF_WORK_COUNTRY,"N/A"));
        tvStatus.setText(""+preference.getStringFromPreference(preference.STAFF_STATUS,"N/A"));
        tvNationality.setText(""+preference.getStringFromPreference(preference.STAFF_NATIONALITY,"N/A"));
        tvUser_Name.setText(""+preference.getStringFromPreference(preference.STAFF_NAME,"N/A"));
        tvUserDesignation.setText(""+preference.getStringFromPreference(preference.STAFF_POSITION,"N/A"));
        tvUser_Id.setText("["+preference.getStringFromPreference(preference.STAFF_NUMBER,"N/A")+"]");
        tvGr.setText(""+preference.getStringFromPreference(preference.STAFF_GRADE,"N/A")+"");
        orgunit.setText(""+preference.getStringFromPreference(preference.STAFF_ORGUNIT,"N/A")+"");
        tvps.setText(preference.getStringFromPreference(preference.STAFF_PERSONAL_SUB_AREA,"N/A")+", "+preference.getStringFromPreference(preference.STAFF_PERSONAL_AREA,"N/A"));
        IKonnectApplication.setImageUrl(ivUserPic, ServiceUrls.PROFILE_PIC+preference.getStringFromPreference(preference.STAFF_PHOTO_URL,""),R.drawable.profile_pic);
    }

    private void initializeControls() {
            tvUser_Name= (TextView) findViewById(R.id.tvUser_Name);
            tvUserDesignation= (TextView) findViewById(R.id.tvUserDesignation);
            tvmobile= (TextView) findViewById(R.id.tvmobile);
            tvmail= (TextView) findViewById(R.id.tvmail);
            tvworkcountry= (TextView) findViewById(R.id.tvworkcountry);
            tvGrade= (TextView) findViewById(R.id.tvGrade);
            tvna= (TextView) findViewById(R.id.tvna);
            Religion= (TextView) findViewById(R.id.Religion);
            tvOrgUnit= (TextView) findViewById(R.id.tvOrgUnit);
            tvarea= (TextView) findViewById(R.id.tvarea);

        tvEmailId   = (TextView) findViewById(R.id.tvEmailId);
        tvMobile    = (TextView)findViewById(R.id.tvMobile);
        tvReligion  = (TextView)findViewById(R.id.tvReligion);
        tvCountry   = (TextView)findViewById(R.id.tvCountry);
        tvStatus    = (TextView)findViewById(R.id.tvStatus);
        tvNationality    = (TextView)findViewById(R.id.tvNationality);
        tvUser_Name    = (TextView)findViewById(R.id.tvUser_Name);
        tvUserDesignation    = (TextView)findViewById(R.id.tvUserDesignation);
        tvUser_Id    = (TextView)findViewById(R.id.tvUser_Id);
        tvps    = (TextView)findViewById(R.id.tvps);
        tvGr    = (TextView)findViewById(R.id.tvGr);
        orgunit    = (TextView)findViewById(R.id.orgunit);
        ivUserPic    = (CircleImageView) findViewById(R.id.ivUserPic);
        btn_proceed    = (Button) findViewById(R.id.btn_proceed);
        if(isFrom.equalsIgnoreCase("menu"))
            btn_proceed.setVisibility(View.GONE);
    }

    @Override
    protected void setTypeFace() {
        IKonnectApplication.setTypeFace(tvUser_Name, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvUserDesignation, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvmobile, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvmail, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvworkcountry, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvGrade, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvna, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(Religion, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvOrgUnit, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvarea, AppConstants.BOLD);

        IKonnectApplication.setTypeFace(tvEmailId, AppConstants.LIGHT);
        IKonnectApplication.setTypeFace(tvMobile, AppConstants.LIGHT);
        IKonnectApplication.setTypeFace(tvReligion, AppConstants.LIGHT);
        IKonnectApplication.setTypeFace(tvCountry, AppConstants.LIGHT);
        IKonnectApplication.setTypeFace(tvStatus, AppConstants.LIGHT);
        IKonnectApplication.setTypeFace(tvNationality, AppConstants.LIGHT);
        IKonnectApplication.setTypeFace(tvUser_Id, AppConstants.LIGHT);
        IKonnectApplication.setTypeFace(tvps, AppConstants.LIGHT);
        IKonnectApplication.setTypeFace(orgunit, AppConstants.LIGHT);
        IKonnectApplication.setTypeFace(tvGr, AppConstants.LIGHT);
        IKonnectApplication.setTypeFace(tvReligion, AppConstants.LIGHT);
    }

    public void MoveToDashboard(View view)
    {
        if(!StringUtils.isEmpty(isFrom)&&isFrom.equalsIgnoreCase("signUp")) {
            finish();
            Intent intent = new Intent(StaffDetailActivity.this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("from","staffdetail");
            startActivity(intent);
        }
    }

    @Override
    public void showAlert(String type) {
        if(type.equalsIgnoreCase(AppConstants.Logout))
        {
            showCustomDialog(StaffDetailActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        }
    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void onProfilePicSuccess() {
        IKonnectApplication.setImageUrl(ivProfile, ServiceUrls.PROFILE_PIC+preference.getStringFromPreference(preference.STAFF_PHOTO_URL,""),R.drawable.profile_pic,true);
        showCustomDialog(StaffDetailActivity.this, getString(R.string.success), getString(R.string.profile_pic), getString(R.string.OK), "", "", false);
        hideLoader();
    }
}
