package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.NetworkUtility;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.module.login.ILoginPresenter;
import com.winit.iKonnect.module.login.ILoginView;
import com.winit.iKonnect.module.login.LoginPresenter;
import com.winit.iKonnect.module.usernavigationactivity.INavigationPresenter;
import com.winit.iKonnect.module.usernavigationactivity.INavigationView;
import com.winit.iKonnect.module.usernavigationactivity.NavigationPresentor;

import butterknife.ButterKnife;

/**
 * Created by Girish Velivela on 5/8/2017.
 */

public class LoginActivity extends BaseActivity implements ILoginView, INavigationView {

    private EditText etLoginID, et_password;
    private ILoginPresenter iLoginPresenter;
    private TextView tv_forget, tvversion;
   // private ScrollView sv;
    private Button tv_push;
    private View v_dummy1;

    //userNavigation
    private String From = "";
    private INavigationPresenter presenter;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.login, flBody, true);
        ButterKnife.bind(this);

        /*     UserNavigationActivity code combining*/
        presenter = new NavigationPresentor(LoginActivity.this);
        if (getIntent().hasExtra("From"))
            From = getIntent().getExtras().getString("From");
        if (From != null && From.equalsIgnoreCase("menu")) {
            presenter.pushServiceForLogout(preference.getStringFromPreference(Preference.STAFF_NUMBER, ""), preference.getStringFromPreference(Preference.GCM_ID, ""));
        }

        /* usernvigation Activtiy end code*/


        try {
            initializeControls();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        iLoginPresenter = new LoginPresenter(this);

       // etLoginID.setFocusable(true);
        /*etLoginID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
//                sv.fullScroll(ScrollView.FOCUS_DOWN);
                    sv.post(new Runnable() {
                        public void run() {
                           sv.smoothScrollTo(0, tv_push.getBottom());
                        }
                    });
            }
        });
        etLoginID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv.post(new Runnable() {
                    public void run() {
                       sv.smoothScrollTo(0, tv_push.getBottom());
                    }
                });
            }
        });
        et_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv.post(new Runnable() {
                    public void run() {
                       sv.smoothScrollTo(0, tv_push.getBottom());
                    }
                });
            }
        });
        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    sv.post(new Runnable() {
                        public void run() {
                            sv.smoothScrollTo(0, sv.getBottom());
                        }
                    });
                }
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initializeControls() throws PackageManager.NameNotFoundException {

        etLoginID = (EditText) findViewById(R.id.etLoginID);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_forget = (TextView) findViewById(R.id.tv_forget);
        tvversion = (TextView) findViewById(R.id.tvversion);
      //  sv = (ScrollView) findViewById(R.id.sv);
        v_dummy1 = (View) findViewById(R.id.v_dummy1);
        tv_push = (Button) findViewById(R.id.tv_push);
        PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        String version = pInfo.versionName;
        int Code = pInfo.versionCode;
        tvversion.setText("V " + version + "(" + Code + ")");
    }

    public void postLoginRequest(View view) {
        if (NetworkUtility.isNetworkConnectionAvailable(LoginActivity.this)) {
            showLoader("");
            String deviceId = preference.getStringFromPreference(Preference.GCM_ID, "");
            if (StringUtils.isEmpty(deviceId))
                deviceId = preference.getStringFromPreference(Preference.DEVICEID, "");
            iLoginPresenter.submit(etLoginID.getText().toString(), AppConstants.DEVICE_TYPE, deviceId, et_password.getText().toString(), deviceId);
        } else
            showAlert(getString(R.string.No_Network_connection));
    }

    public void movetoForgetPassword(View view) {
        showLoader("");
        Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
        startActivity(intent);
    }

    @Override
    protected void setTypeFace() {
        tv_forget.setTypeface(AppConstants.BOLD);
        tvversion.setTypeface(AppConstants.BOLD);
    }

    @Override
    public void showAlert(String type) {
        String message = "";
        hideLoader();
        switch (type) {
            case STAFFID:
                message = getString(R.string.StaffIDMsg);
                break;
            case PASSWORD:
                message = getString(R.string.PleaseEnterPassword);
                break;
            case AppConstants.Logout:
                showCustomDialog(LoginActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
                break;
            default:
                message = type;
                break;
        }
        if (AppConstants.LoginCount==0) {
            if (message.equalsIgnoreCase(getString(R.string.LoginResponceMessage))) {
                AppConstants.LoginCount = 1;
                iLoginPresenter.postHovering();
                preference.saveBooleanInPreference(preference.IS_LOGGED_IN, true);
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_left1, R.anim.slide_right1);
                startActivity(intent);
            } else
                showCustomDialog(LoginActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
        }
    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    protected void onResume() {
        hideLoader();
        super.onResume();
    }

    public void moveToSignUp(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        intent.putExtra("isFrom", "");
        startActivity(intent);
    }
}
