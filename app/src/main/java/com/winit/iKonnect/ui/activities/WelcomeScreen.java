package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.WelcomeMessageDO;
import com.winit.iKonnect.module.WelcomScreen.IWelcomScreenView;
import com.winit.iKonnect.module.WelcomScreen.WelcomScreenPresentor;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ashoka.Reddy on 6/1/2017.
 */

public class WelcomeScreen extends BaseActivity implements IWelcomScreenView
{
    @Nullable
    @Bind(R.id.tvWelcomeMsg)
    public TextView tvWelcomeMsg;
    private TextView tvname;
    private WelcomScreenPresentor presentor;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.welcome, flBody, true);
        ButterKnife.bind(this);
        initializeControls();
        presentor = new WelcomScreenPresentor(this);
        presentor.getWelcomeMessage();
    }

    private void initializeControls() {

        tvname = (TextView) findViewById(R.id.tvname);
        tvname.setText(preference.getStringFromPreference(Preference.STAFF_NAME,"N/A"));
    }

    @Override
    protected void setTypeFace() {
        tvWelcomeMsg.setTypeface(AppConstants.BOLD);
    }

    public void gotodashboard(View v)
    {
        preference.saveBooleanInPreference(preference.IS_LOGGED_IN,true);
        Intent intent= new Intent(WelcomeScreen.this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        overridePendingTransition(R.anim.slide_left1,R.anim.slide_right1);
        startActivity(intent);
    }

    @Override
    public void showAlert(String type) {

    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void LoadMessage(final WelcomeMessageDO message)
    {
        boolean isEnglisSelected= preference.getStringFromPreference(Preference.LANGUAGE,"").equalsIgnoreCase(AppConstants.ENGLISH);
        if(isEnglisSelected)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(message!=null)
                    tvWelcomeMsg.setText(""+message.msg);
                    else
                        showCustomDialog(WelcomeScreen.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
                }
            });
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(message!=null)
                    tvWelcomeMsg.setText(""+message.msgAr);
                    else
                        showCustomDialog(WelcomeScreen.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
                }
            });
        }


    }
}
