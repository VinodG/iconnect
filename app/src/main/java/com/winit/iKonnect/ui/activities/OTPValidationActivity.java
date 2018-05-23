package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.response.ForgetotpDo;
import com.winit.iKonnect.module.otpValidation.IOtpValidationPresenter;
import com.winit.iKonnect.module.otpValidation.IOtpValidationView;
import com.winit.iKonnect.module.otpValidation.OtpValidationPresenter;

import butterknife.ButterKnife;

/**
 * Created by Rahul.Yadav on 5/28/2017.
 */

public class OTPValidationActivity extends BaseActivity implements IOtpValidationView {
 private EditText et_OtpValue;
    private TextView tvEmailId, tvMobile, tvUserName, tvEmpoyCode;
    private IOtpValidationPresenter iOtpValidationPresenter;
    private ForgetotpDo forgetpass;
    private String from="";


    @Override
    protected void initialize() {
        inflater.inflate(R.layout.otp_code, flBody, true);
        ButterKnife.bind(this);
        if(getIntent().hasExtra("loginResponse"))
            forgetpass = (ForgetotpDo) getIntent().getExtras().getSerializable("loginResponse");
        setToolbarTitle(getString(R.string.otp_Validation_header));
        iOtpValidationPresenter = new OtpValidationPresenter(OTPValidationActivity.this);

        initializeControl();

        if(getIntent().hasExtra("from"))
            from = getIntent().getExtras().getString("from"); //signup--forgetpassword
        if(forgetpass!=null)
        {
            tvEmailId.setText(forgetpass.getEmail());
            tvMobile.setText(forgetpass.getMobile());
        }
        else
        {
            tvEmailId.setText(""+preference.getStringFromPreference(Preference.STAFF_EMAIL,"N/A"));
            tvMobile.setText(""+""+preference.getStringFromPreference(Preference.STAFF_MOBILE,"N/A"));
        }

    }

    private void initializeControl()
    {
        et_OtpValue = (EditText) findViewById(R.id.et_OtpValue);
        tvEmailId = (TextView) findViewById(R.id.tvEmailId);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvEmpoyCode = (TextView) findViewById(R.id.tvEmpoyCode);
        tvMobile = (TextView) findViewById(R.id.tvMobile);
//        tvEmailId.setText(preference.getStringFromPreference(Preference.STAFF_EMAIL,""));
//        tvMobile.setText(preference.getStringFromPreference(Preference.STAFF_MOBILE,""));
    }
    @Override
    protected void setTypeFace() {

    }

    @Override
    public void showToast(String message) {
        hideLoader();
        showCustomDialog(OTPValidationActivity.this,getString(R.string.alert),message,getString(R.string.OK),"","");
    }

    @Override
    public void showAlert(String type) {
        hideLoader();
        String message = "";
        switch (type){
            case INVALID_OTP:
                message = getString(R.string.Please_entere_valid_OTP);
                break;
            default:
                message = type;
                break;
        }
        if(message.equalsIgnoreCase(getString(R.string.OTP_validated_successfully)))
        {
            Intent in= new Intent(OTPValidationActivity.this, PasswordActivity.class);
            if(from!=null && !TextUtils.isEmpty(from))
            in.putExtra("from",from);
            overridePendingTransition(R.anim.slide_left1,R.anim.slide_right1);
            startActivity(in);
            finish();
        }
        else
            showCustomDialog(OTPValidationActivity.this,getString(R.string.alert),message,getString(R.string.OK),"","");
    }

    @Override
    public void onLoadFailed() {
        hideLoader();

    }

    public void varifyOTP(View view)
    {
        showLoader("");
       iOtpValidationPresenter.submit(preference.getStringFromPreference(Preference.STAFF_NUMBER,""),preference.getStringFromPreference(Preference.STAFF_MOBILE,""),et_OtpValue.getText().toString());
    }

    public void reSentOTP(View view)
    {
        showLoader("");
        iOtpValidationPresenter.reSendOtp(preference.getStringFromPreference(Preference.STAFF_NUMBER,""),preference.getStringFromPreference(Preference.STAFF_MOBILE,""),preference.getStringFromPreference(Preference.STAFF_WORK_COUNTRY,""));
    }

}
