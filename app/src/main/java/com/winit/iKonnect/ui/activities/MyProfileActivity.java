package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.FeedsDO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ashoka.Reddy on 7/12/2017.
 */

public class MyProfileActivity extends BaseActivity implements View.OnClickListener{
    private TextView tvmyprofile,tvmyfav,tvmypayslip,tvmytrackservice,tv_count;
    private LinearLayout llmyprofile, llmyfav, llmypaylips, llmytrackservice;

    @Nullable
    @Bind(R.id.llEmployeeList)
    LinearLayout llEmployeeList;
    @Nullable
    @Bind(R.id.llmydocuments)
    LinearLayout llmydocuments;
    @Nullable
    @Bind(R.id.tvEmployeeList)
    TextView tvEmployeeList;
    @Nullable
    @Bind(R.id.tvmydocuments)
    TextView tvmydocuments;
    @Override
    protected void initialize() {

        if(getIntent().hasExtra("from"))
        {
            Intent intent = new Intent(MyProfileActivity.this, TrackServiceActivity.class);
            startActivity(intent);
        }
        inflater.inflate(R.layout.myprofilescreen, flBody, true);
        ButterKnife.bind(this);
        setToolbarTitle(""+getString(R.string.my_profile));
        initialisecontrols();
        llmyprofile.setOnClickListener(this);
        llmyfav.setOnClickListener(this);
        llmypaylips.setOnClickListener(this);
        llEmployeeList.setOnClickListener(this);
        llmydocuments.setOnClickListener(this);

    }

    private void initialisecontrols() {
        tvmyprofile = (TextView) findViewById(R.id.tvmyprofile);
        tvmyfav = (TextView) findViewById(R.id.tvmyfav);
        tvmypayslip = (TextView) findViewById(R.id.tvmypayslip);
//        tvmytrackservice = (TextView) findViewById(R.id.tvmytrackservice);
//        tv_count = (TextView) findViewById(R.id.tv_count);

        llmyprofile = (LinearLayout) findViewById(R.id.llmyprofile);
        llmyfav = (LinearLayout) findViewById(R.id.llmyfav);
        llmypaylips = (LinearLayout) findViewById(R.id.llmypaylips);
//        llmytrackservice = (LinearLayout) findViewById(R.id.llmytrackservice);
    }

    @Override
    protected void setTypeFace() {
        tvmyprofile.setTypeface(AppConstants.MEDIUM);
        tvmyfav.setTypeface(AppConstants.MEDIUM);
//        tvmytrackservice.setTypeface(AppConstants.MEDIUM);
        tvmypayslip.setTypeface(AppConstants.MEDIUM);
        tvEmployeeList.setTypeface(AppConstants.MEDIUM);
        tvmydocuments.setTypeface(AppConstants.MEDIUM);
//        tv_count.setTypeface(AppConstants.MEDIUM);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
       /* if(preference.getIntFromPreference(Preference.ServiceCount,0)>0)
            tv_count.setText(""+preference.getIntFromPreference(Preference.ServiceCount,0));
        else
            tv_count.setText("");
*/
        IKonnectApplication.setImageUrl(ivProfile, ServiceUrls.PROFILE_PIC + preference.getStringFromPreference(preference.STAFF_PHOTO_URL, ""), R.drawable.profile_pic);

    }

    @Override
    public void onClick(View v) {

        Intent intent=null;
        if(v.getId()==R.id.llmyprofile)
        {
//            intent = new Intent(MyProfileActivity.this, StaffDetailActivity.class);
            intent = new Intent(MyProfileActivity.this, StaffDetailActivityNew.class);
            intent.putExtra("isFrom", "menu");
        }else if(v.getId()==R.id.llmyfav)
        {
            intent = new Intent(MyProfileActivity.this, FeedActivity.class);
            intent.putExtra("where","profile");
            intent.putExtra(ConstantExtraKey.FEED_TYPE, FeedsDO.FAVOURITE);

        }
        else if(v.getId()==R.id.llmypaylips)
        {
//            intent = new Intent(MyProfileActivity.this, PaySlipActivity.class);
        }
        else if(v.getId()==R.id.llEmployeeList)
        {
              intent = new Intent(MyProfileActivity.this, EmployeeListActivity.class);
        }
        else if(v.getId()==R.id.llmydocuments)
        {
            intent = new Intent(MyProfileActivity.this, MyDocumentsActivity.class);
        }


      /*  else if(v.getId()==R.id.llmytrackservice)
        {
            *//*intent = new Intent(MyProfileActivity.this, TrackServiceActivity.class);
            preference.removeFromPreference(Preference.ServiceCount);*//*
        }*/
        if (intent!=null)
            startActivity(intent);

    }
}
