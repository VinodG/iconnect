package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.CalendarUtil;
import com.winit.common.util.StringUtils;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.AttachmentsDO;
import com.winit.iKonnect.module.staffdetail.StaffDetailPresentor;
import com.winit.iKonnect.module.staffdetail.iStaffDetailPresentor;
import com.winit.iKonnect.module.staffdetail.iStaffDetailView;

import java.io.File;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Sreekanth.P on 23-11-2017.
 */

public class StaffDetailActivityNew extends BaseActivity implements iStaffDetailView {

    @Nullable
    @Bind(R.id.tvDob)
    TextView tvDob;
    @Nullable
    @Bind(R.id.tvDateofJoin)
    TextView tvDateofJoin;
    @Nullable
    @Bind(R.id.tvBankName)
    TextView tvBankName;
    @Nullable
    @Bind(R.id.tvBankAccount)
    TextView tvBankAccount;
    @Nullable
    @Bind(R.id.tvPassportNo)
    TextView tvPassportNo;
    @Nullable
    @Bind(R.id.tvBand)
    TextView tvBand;
    @Nullable
    @Bind(R.id.tvIBAN_Num)
    TextView tvIBAN_Num;
    @Nullable
    @Bind(R.id.tvExpiry_Date)
    TextView tvExpiry_Date;
    @Nullable
    @Bind(R.id.tvVisa_Expiry_Date)
    TextView tvVisa_Expiry_Date;
    @Nullable
    @Bind(R.id.tvSponsor)
    TextView tvSponsor;
    @Nullable
    @Bind(R.id.tvDivision)
    TextView tvDivision;
    @Nullable
    @Bind(R.id.btn_proceed)
    Button btn_proceed;

    private Bundle bundle;
    private String isFrom="";

    private iStaffDetailPresentor iStaff;

    @Override
    protected void initialize() {

        inflater.inflate(R.layout.myprofilescreen_new, flBody, true);
        ButterKnife.bind(this);
        setToolbarTitle("" + getString(R.string.my_profile));

        bundle = getIntent().getExtras();
        if(bundle.getString("isFrom")!=null)
            isFrom = bundle.getString("isFrom");
        if(!StringUtils.isEmpty(isFrom)&&isFrom.equalsIgnoreCase("signUp")) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

//        setProfiledata();
        setUpNavigationView();
        setProfiledata();
        iStaff = new StaffDetailPresentor(StaffDetailActivityNew.this);
    }
    public void MoveToDashboard(View view)
    {
        if(!StringUtils.isEmpty(isFrom)&&isFrom.equalsIgnoreCase("signUp")) {
            finish();
            Intent intent = new Intent(StaffDetailActivityNew.this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("from","staffdetail");
            startActivity(intent);
        }
    }

    private void setProfiledata() {

        tvUser_Name.setText(preference.getStringFromPreference(Preference.STAFF_NAME, ""));
        tvUserDesignation.setText(preference.getStringFromPreference(Preference.STAFF_POSITION, ""));
        tvUser_Id.setText(preference.getStringFromPreference(Preference.STAFF_ID, ""));

        tvLocation.setText(preference.getStringFromPreference(Preference.STAFF_PERSONAL_SUB_AREA, "N/A"));
        tvFunctionalArea.setText(preference.getStringFromPreference(Preference.FUNCTIONAL_AREA, "N/A"));
        tvNationality.setText(preference.getStringFromPreference(Preference.STAFF_NATIONALITY, "N/A"));

        IKonnectApplication.setImageUrl(ivEmployeeUserPic, ServiceUrls.PROFILE_PIC + preference.getStringFromPreference(preference.STAFF_PHOTO_URL, ""), R.drawable.profile_pic);


        tvDob.setText(CalendarUtil.getDate(preference.getStringFromPreference(Preference.DOB, "")
                , CalendarUtil.DATE_PATTERN, CalendarUtil.DD_MM_YYYY_PATTERN, Locale.ENGLISH, ""));

        tvDateofJoin.setText(preference.getStringFromPreference(Preference.HIRE_DATE, "N/A"));
        tvBankName.setText(preference.getStringFromPreference(Preference.BANK_NAME,"N/A"));
        tvBankAccount.setText(preference.getStringFromPreference(Preference.ACCOUNT_NUMBER,"N/A"));
        tvPassportNo.setText(preference.getStringFromPreference(Preference.ID_NUMBER_PASSPORT_SLEF, "N/A"));
        tvBand.setText(preference.getStringFromPreference(Preference.BAND, "N/A"));
        tvIBAN_Num.setText(preference.getStringFromPreference(Preference.IBAN,"N/A"));
        tvExpiry_Date.setText(CalendarUtil.getDate(preference.getStringFromPreference(Preference.ID_NUMBER_PASSPORT_SLEF_EXPIREDATE, "")
                , CalendarUtil.DATE_PATTERN, CalendarUtil.DD_MM_YYYY_PATTERN, Locale.ENGLISH, ""));
        tvVisa_Expiry_Date.setText(CalendarUtil.getDate(preference.getStringFromPreference(Preference.ID_NUMBER_RESIDENCE_VISA_EXPIREDATE, "")
                , CalendarUtil.DATE_PATTERN, CalendarUtil.DD_MM_YYYY_PATTERN, Locale.ENGLISH, ""));

        tvSponsor.setText(preference.getStringFromPreference(Preference.SPONSOR,"N/A"));

        tvDivision.setText(preference.getStringFromPreference(Preference.STAFF_PERSONAL_AREA,"N/A"));

        if(isFrom.equalsIgnoreCase("menu"))
            btn_proceed.setVisibility(View.GONE);
    }

    @Override
    protected void setTypeFace() {
        IKonnectApplication.setTypeFace(tvUser_Name, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvUserDesignation, AppConstants.REGULAR);
        IKonnectApplication.setTypeFace(tvUser_Id, AppConstants.REGULAR);
        IKonnectApplication.setTypeFace(tvFunctionalArea, AppConstants.REGULAR);
        IKonnectApplication.setTypeFace(tvLocation, AppConstants.REGULAR);
        IKonnectApplication.setTypeFace(tvNationality, AppConstants.REGULAR);

       /* IKonnectApplication.setTypeFace(tvDob, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvDateofJoin, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvBankAccount, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvPassportNo, AppConstants.BOLD);*/
    }

    public void changeImage(View v) {
        CaptureImageForProfile();
    }

    public void setAttachment(String path, AttachmentsDO fromDetails) {
        try {
            showLoader("Please wait!");
            File image = new File(path);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 10;
            Bitmap bitmap = null;
            bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
            Matrix matrix = new Matrix();
//            matrix.postRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            ivEmployeeUserPic.setImageBitmap(bitmap);
            ivEmployeeUserPic.setImageBitmap(bitmap);
            iStaff.postProfilePicture(path, StringUtils.getInt(preference.getStringFromPreference(Preference.STAFF_NUMBER, "")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showAlert(String type) {
        if (type.equalsIgnoreCase(AppConstants.Logout)) {
            showCustomDialog(StaffDetailActivityNew.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        }
    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void onProfilePicSuccess() {
        IKonnectApplication.setImageUrl(ivEmployeeUserPic, ServiceUrls.PROFILE_PIC + preference.getStringFromPreference(preference.STAFF_PHOTO_URL, ""), R.drawable.profile_pic, true);
        showCustomDialog(StaffDetailActivityNew.this, getString(R.string.success), getString(R.string.profile_pic), getString(R.string.OK), "", "", false);
        hideLoader();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //listener for home
        if (item.getItemId() == android.R.id.home) {
            if (isBackAllowed)
            {
                if(!StringUtils.isEmpty(isFrom)&&isFrom.equalsIgnoreCase("signUp"))
                {
                    Intent intent = new Intent(StaffDetailActivityNew.this,DashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("from","staffdetail");
                    startActivity(intent);
                    finish();
                }else
                    finish();
            }
            else
                drawerLayout.openDrawer(navigationView);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!StringUtils.isEmpty(isFrom)&&isFrom.equalsIgnoreCase("signUp"))
        {
            Intent intent = new Intent(StaffDetailActivityNew.this,DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("from","staffdetail");
            startActivity(intent);
            finish();
        }else
            super.onBackPressed();
    }
}
