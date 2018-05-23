package com.winit.iKonnect.ui.activities;

import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.NetworkUtility;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.module.changePassword.ChangePasswordPresenter;
import com.winit.iKonnect.module.changePassword.IChangePasswordPresenter;
import com.winit.iKonnect.module.changePassword.IChangePasswordView;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rahul.Yadav on 6/7/2017.
 */

public class ChangePasswordActivity extends BaseActivity implements IChangePasswordView{
    EditText etCurrentPassword,etNewPassword,etConfirmNewPassword;
    private IChangePasswordPresenter iChangePasswordPresenter;
    private CircleImageView ivUserPic;
    private TextView tvStaffName, tvStaffDesignation, tvStaffCode, tvchhosenewpass, tv_confirmpass, tv_enter_pass;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.change_password_layout, flBody, true);
//        inflater.inflate(R.layout.change_password_layout_new, flBody, true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);
        setToolbarTitle(""+getString(R.string.change_pass));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            appBarLayout.setOutlineProvider(null);
        }
        iChangePasswordPresenter = new ChangePasswordPresenter(this);
        initializeControls();
        setStaffDetail();
    }

    private void initializeControls()
    {
        etCurrentPassword = (EditText) findViewById(R.id.etCurrentPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etConfirmNewPassword = (EditText) findViewById(R.id.etConfirmNewPassword);
        ivUserPic = (CircleImageView) findViewById(R.id.ivUserPic);
        tvStaffName = (TextView) findViewById(R.id.tvStaffName);
        tvStaffDesignation = (TextView) findViewById(R.id.tvStaffDesignation);
        tvStaffCode = (TextView) findViewById(R.id.tvStaffCode);
        tvchhosenewpass = (TextView) findViewById(R.id.tvchhosenewpass);
        tv_confirmpass = (TextView) findViewById(R.id.tv_confirmpass);
        tv_enter_pass = (TextView) findViewById(R.id.tv_enter_pass);
    }

    @Override
    protected void setTypeFace() {

        IKonnectApplication.setTypeFace(tvStaffName, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvStaffDesignation, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvchhosenewpass, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tv_confirmpass, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tv_enter_pass, AppConstants.BOLD);


        IKonnectApplication.setTypeFace(tvStaffCode, AppConstants.LIGHT);
        IKonnectApplication.setTypeFace(etCurrentPassword, AppConstants.LIGHT);
        IKonnectApplication.setTypeFace(etNewPassword, AppConstants.LIGHT);
        IKonnectApplication.setTypeFace(etConfirmNewPassword, AppConstants.LIGHT);

    }

    public void submitPassword(View view)
    { if(NetworkUtility.isNetworkConnectionAvailable(ChangePasswordActivity.this))
    {
        showLoader("");
        iChangePasswordPresenter.submit(preference.getStringFromPreference(Preference.STAFF_NUMBER,""),etNewPassword.getText().toString(),etCurrentPassword.getText().toString(),etConfirmNewPassword.getText().toString());
    }
    else
        showAlert(getString(R.string.No_Network_connection));
    }

    @Override
    public void showAlert(String type) {
        {
            String message = "";
            switch (type){
                case STAFF_ID:
                    message = getString(R.string.StaffIDMsg);
                    break;
                case NEW_PASSWORD:
                    message = getString(R.string.Please_enter_new_password);
                    break;
                case CURRENT_PASSWORD:
                    message = getString(R.string.Please_enter_current_password);
                    break;
                case CONFIRM_PASSWORD:
                    message = getString(R.string.Please_re_enter_password);
                    break;
                case VALIDATE_PASSWORD:
                    message = getString(R.string.New_password_and_confirm_passwordMsg);
                    break;
                default:
                    message = type;
                    break;
            }
            hideLoader();
            if(message.equalsIgnoreCase( getString(R.string.Password_changed_successfully)))
            {
                showCustomDialog(ChangePasswordActivity.this,getString(R.string.alert),message,getString(R.string.OK),"","finish");

            }
            else
                showCustomDialog(ChangePasswordActivity.this,getString(R.string.alert),message,getString(R.string.OK),"","");

        }

    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void onButtonYesClick(String from) {
        super.onButtonYesClick(from);
        if(from.equalsIgnoreCase("finish"))
            finish();
    }
    private void setStaffDetail() {
        tvStaffName.setText(""+preference.getStringFromPreference(preference.STAFF_NAME,"N/A"));
        tvStaffDesignation.setText(""+preference.getStringFromPreference(preference.STAFF_POSITION,"N/A"));
        tvStaffCode.setText("["+preference.getStringFromPreference(preference.STAFF_NUMBER,"N/A")+"]");
        IKonnectApplication.setImageUrl(ivUserPic, ServiceUrls.PROFILE_PIC+preference.getStringFromPreference(preference.STAFF_PHOTO_URL,""),R.drawable.place_holder_image);
                tvStaffCode.setText("["+preference.getStringFromPreference(preference.STAFF_NUMBER,"N/A")+"]");

    }
}
