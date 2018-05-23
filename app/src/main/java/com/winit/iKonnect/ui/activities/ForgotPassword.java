package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.winit.common.util.NetworkUtility;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.response.ForgetotpDo;
import com.winit.iKonnect.module.forgotPassword.ForgotPasswordPresenter;
import com.winit.iKonnect.module.forgotPassword.IForgotPasswordPresenter;
import com.winit.iKonnect.module.forgotPassword.IForgotPasswordView;

import butterknife.ButterKnife;

/**
 * Created by Rahul.Yadav on 6/6/2017.
 */

public class ForgotPassword extends BaseActivity implements IForgotPasswordView{
    private EditText et_staffId;
    private Button btn_signUp;
    private TextView tv_signUpHeader;
    private IForgotPasswordPresenter iForgotPasswordPresenter;
    @Override
    protected void initialize() {
        inflater.inflate(R.layout.sign_up, flBody, true);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        iForgotPasswordPresenter = new ForgotPasswordPresenter(this);
        initializeControl();
        bindControls();


    }

    private void bindControls()
    {
            et_staffId.setFocusable(true);
            tv_signUpHeader.setText(getResources().getString(R.string.Forgot_Password));
            btn_signUp.setText(getResources().getString(R.string.Send_Otp));
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

    @Override
    public void showAlert(String type, ForgetotpDo loginResponse) {
        String message = "";
        switch (type){
            case STAFF_ID:
                message = getString(R.string.StaffIDMsg);
                break;
            default:
                message = type;
                break;
        }
        hideLoader();
        if(message.equalsIgnoreCase(getString(R.string.OTP_sent_successfully)))
        {
            Intent in= new Intent(ForgotPassword.this, OTPValidationActivity.class);
            in.putExtra("loginResponse",loginResponse);
            in.putExtra("from","forgetpassword");
            overridePendingTransition(R.anim.slide_left1,R.anim.slide_right1);
            startActivity(in);
            finish();
        }
        else
            showCustomDialog(ForgotPassword.this,getString(R.string.alert),message,getString(R.string.OK),"","");
    }
    public void MoveToOtpScreen(View view)
    {
        if(NetworkUtility.isNetworkConnectionAvailable(ForgotPassword.this)) {
            showLoader("");
            iForgotPasswordPresenter.submitForgetPassword(et_staffId.getText().toString());
        }
        else
            showAlert(getString(R.string.No_Network_connection));

    }


    @Override
    public void showAlert(String type) {
        hideLoader();
        String message = "";
        if(type.equalsIgnoreCase(STAFF_ID))
        {
            message = getString(R.string.StaffIDMsg);
        }else {
            message=type;
        }
        showCustomDialog(ForgotPassword.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void onLoadFailed() {

    }
}
