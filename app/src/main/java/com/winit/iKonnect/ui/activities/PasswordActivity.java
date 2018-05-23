package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.module.passwordCreation.IPasswordPresenter;
import com.winit.iKonnect.module.passwordCreation.IPasswordView;
import com.winit.iKonnect.module.passwordCreation.PasswordPresenter;

import butterknife.ButterKnife;

import static com.winit.common.Preference.IS_FROM_FORGOT_PASSWORD;

/**
 * Created by Rahul.Yadav on 5/29/2017.
 */

public class PasswordActivity extends BaseActivity implements IPasswordView{
    private IPasswordPresenter iPasswordPresenter;
    private EditText et_confirmPassword,et_password;
    private String from="";
    @Override
    protected void initialize() {
        inflater.inflate(R.layout.password_create, flBody, true);
        ButterKnife.bind(this);
        initializeControls();
        iPasswordPresenter =new PasswordPresenter(this);
        if(getIntent().hasExtra("from"))
            from = getIntent().getExtras().getString("from"); //signup--forgetpassword
    }
    private void initializeControls(){

        et_password = (EditText) findViewById(R.id.et_password);
        et_confirmPassword = (EditText) findViewById(R.id.et_confirmPassword);

    }
    public void postPasswordRequest(View view)
    {
        String deviceId = preference.getStringFromPreference(Preference.GCM_ID,"");
        if(StringUtils.isEmpty(deviceId))
            deviceId = preference.getStringFromPreference(Preference.DEVICEID,"");
        if(from!=null && !TextUtils.isEmpty("from") && from.equalsIgnoreCase("forgetpassword"))
        iPasswordPresenter.submit(preference.getStringFromPreference(Preference.STAFF_NUMBER,""), AppConstants.DEVICE_TYPE,deviceId,et_password.getText().toString(),et_confirmPassword.getText().toString());
        else
        iPasswordPresenter.register(preference.getStringFromPreference(Preference.STAFF_NUMBER,""), AppConstants.DEVICE_TYPE,deviceId,et_password.getText().toString(),et_confirmPassword.getText().toString());
    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    public void showAlert(String type) {
        String message = "";
        switch (type){
            case PASSWORD:
                message = getString(R.string.PleaseEnterReason);
                break;
            case CONFIRM_PASSWORD:
                message = getString(R.string.Please_enter_Confirm_Password);
                break;
            case INVALID_PASSWORD:
                message = getString(R.string.New_password_and_confirm_passwordMsg);
                break;
            default:
                message = type;
                break;
        }
        if(message.equalsIgnoreCase(getString(R.string.Registered_successfully)))
        {
            if(preference.getbooleanFromPreference(IS_FROM_FORGOT_PASSWORD,false))
            {
                Intent in= new Intent(PasswordActivity.this, LoginActivity.class);
                overridePendingTransition(R.anim.slide_left1,R.anim.slide_right1);
                startActivity(in);
                finish();
            }
            else
            {
                if(!preference.getbooleanFromPreference(preference.IS_LOGGED_IN,false)) {
                    preference.saveBooleanInPreference(preference.IS_LOGGED_IN, true);
                    Intent in = new Intent(PasswordActivity.this, StaffDetailActivityNew.class);
                    in.putExtra("isFrom", "signUp");
                    overridePendingTransition(R.anim.slide_left1, R.anim.slide_right1);
                    startActivity(in);
                    finish();
                }
            }
        }
        else
            showCustomDialog(PasswordActivity.this,getString(R.string.alert),message,getString(R.string.OK),"","LOGOUT");
    }
    public void onButtonYesClick(String from) {
        if (from.equalsIgnoreCase("LOGOUT")) {
            Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else
            super.onButtonYesClick(from);
    }

    @Override
    public void onLoadFailed() {

    }
}
