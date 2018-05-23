package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.module.Settings.ISettingsDetailPresenter;
import com.winit.iKonnect.module.Settings.ISettingsDetailView;
import com.winit.iKonnect.module.Settings.SettingsDetailPresenter;

import butterknife.ButterKnife;

import static com.winit.iKonnect.R.id.img_lang;

/**
 * Created by Ashoka.Reddy on 5/25/2017.
 */

public class SettingActivity extends BaseActivity implements ISettingsDetailView
{
    private boolean isEnglisSelected=true;
    private String Modified_Language = "";
    private Button btn_submit;
    private ISettingsDetailPresenter iSettingsDetailPresenter;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.settings, flBody, true);
        ButterKnife.bind(this);

        iSettingsDetailPresenter = new SettingsDetailPresenter(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        setToolbarTitle(""+getString(R.string.settings));

    }
    public void submit(View v)
    {
        if(Modified_Language.equalsIgnoreCase(AppConstants.ARABIC))
             iSettingsDetailPresenter.UpdateNotitification("ar");
        else
            iSettingsDetailPresenter.UpdateNotitification("en");

    }

    public void moveToChangePassword(View view)
    {
        Intent intent = new Intent(SettingActivity.this,ChangePasswordActivity.class);
        startActivity(intent);
    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    public void OnResponceReceive(Object data)
    {
        if(!TextUtils.isEmpty(Modified_Language)) {
            preference.saveStringInPreference(Preference.LANGUAGE, Modified_Language);
            setLanguage();
            Intent intent = new Intent(SettingActivity.this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else
        {
            finish();
        }
    }
}
