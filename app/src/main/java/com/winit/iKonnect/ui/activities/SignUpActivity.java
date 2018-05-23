package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.iKonnect.R;
import com.winit.iKonnect.module.signUp.ISignUpPresenter;
import com.winit.iKonnect.module.signUp.ISignUpView;
import com.winit.iKonnect.module.signUp.SignUpPresenter;

import butterknife.ButterKnife;

/**
 * Created by Rahul.Yadav on 5/28/2017.
 */

public class SignUpActivity extends BaseActivity implements ISignUpView  {
    EditText et_staffId;
    private String isFrom="";
    private Bundle bundle;
    private Button btn_signUp;
    private TextView tv_signUpHeader;
    private ISignUpPresenter iSignUpPresenter;
    @Override
    protected void initialize() {
        inflater.inflate(R.layout.sign_up, flBody, true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        if(bundle!=null&&bundle.getString("isFrom")!=null)
            isFrom = bundle.getString("isFrom");
        iSignUpPresenter = new SignUpPresenter(SignUpActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initializeControl();
        bindControls();
        et_staffId.setFocusable(true);
    }

    private void bindControls()
    {
        if(isFrom.equalsIgnoreCase("ForgetPassword"))
        {
            tv_signUpHeader.setText(getResources().getString(R.string.Forgot_Password));
            btn_signUp.setText(getResources().getString(R.string.Send_Otp));
        }
        else
        {
            tv_signUpHeader.setText(getResources().getString(R.string.SIGNUP));
            btn_signUp.setText(getResources().getString(R.string.SIGNUP));
        }

    }

    private void initializeControl()
    {
        et_staffId = (EditText) findViewById(R.id.etStaffID);
        tv_signUpHeader = (TextView) findViewById(R.id.tv_signUpHeader);
        btn_signUp = (Button) findViewById(R.id.btn_signUp);
    }

    @Override
    protected void setTypeFace() {
    }

    public void MoveToOtpScreen(View view)
    {
    showLoader("");

//        if(isFrom.equalsIgnoreCase("ForgetPassword"))
//            iSignUpPresenter.submitForgetPassword(et_staffId.getText().toString());
//        else
            iSignUpPresenter.submit(et_staffId.getText().toString());
    }

    @Override
    public void showAlert(String type) {
        String message = "";
        switch (type){
            case STAFFID:
                message = getString(R.string.StaffIDMsg);
                break;
            default:
                message = type;
                break;
        }
//        hideLoader();
        if(message.equalsIgnoreCase(getString(R.string.OTP_sent_successfully)))
        {
            hideLoader();

            Intent in= new Intent(SignUpActivity.this, OTPValidationActivity.class);
            in.putExtra("from","signup");
            overridePendingTransition(R.anim.slide_left1,R.anim.slide_right1);
            startActivity(in);
            finish();
        }
       else if(message.equalsIgnoreCase("Success"))
        {
//            iSignUpPresenter.submit(preference.getStringFromPreference(Preference.STAFF_NUMBER,""),preference.getStringFromPreference(Preference.STAFF_MOBILE,""),preference.getStringFromPreference(Preference.STAFF_WORK_COUNTRY,""));
        }
        else
            showCustomDialog(SignUpActivity.this,getString(R.string.alert),message,getString(R.string.OK),"","");

    }

    @Override
    public void onLoadFailed() {

    }


    @Override
    public void onButtonYesClick(String from) {
        super.onButtonYesClick(from);
    }
}
