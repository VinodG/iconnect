package com.winit.iKonnect.module.otpValidation;

import com.winit.common.Preference;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.dataobject.response.LoginResponse;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.otpValidation.interacter.IOTPInteractor;
import com.winit.iKonnect.module.otpValidation.interacter.OTPInteracter;
import com.winit.iKonnect.module.signUp.interacter.ISignUpInteractor;
import com.winit.iKonnect.module.signUp.interacter.SignUpInteracter;
import com.winit.iKonnect.ui.activities.OTPValidationActivity;


import static com.winit.iKonnect.module.login.ILoginView.STAFFID;

/**
 * Created by Rahul.Yadav on 5/29/2017.
 */

public class OtpValidationPresenter extends BasePresenter implements IOtpValidationPresenter,OTPInteracter.OnOtpListener,SignUpInteracter.OnSignUpListener {
   private IOtpValidationView view;
   private  IOTPInteractor interactor;
    private ISignUpInteractor iSignUpInteractor;

    public OtpValidationPresenter(OTPValidationActivity view) {
        super(view);
        this.view = view;
        this.interactor = new OTPInteracter(this);
        this.iSignUpInteractor = new SignUpInteracter(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onError(String Message) {
        view.showAlert(Message);

    }

    @Override
    public void onSuccess(int status, String msg) {
        if(status==200)
        {
            view.showToast(msg);
        }
    }

    @Override
    public void submit(String StaffNumber, String mobile, String otp) {
        if(StringUtils.isEmpty(StaffNumber)){
            view.showAlert(STAFFID);
        }
        else {
            interactor.postData(StaffNumber,mobile,otp);
        }
    }

    @Override
    public void reSendOtp(String StaffNumber, String mobile, String workCountry) {
        iSignUpInteractor.postData(preference.getStringFromPreference(Preference.STAFF_NUMBER,""),preference.getStringFromPreference(Preference.STAFF_MOBILE,""),preference.getStringFromPreference(Preference.STAFF_WORK_COUNTRY,""));
    }

    @Override
    public void onSuccess(Object object) {
        LoginResponse loginDo = (LoginResponse) object;
        if(object!=null)
        {
            view.showAlert(((LoginResponse) object).getStatusMessageEn());
        }

    }
}
