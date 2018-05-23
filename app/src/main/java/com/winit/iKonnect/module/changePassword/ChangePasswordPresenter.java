package com.winit.iKonnect.module.changePassword;

import com.winit.common.util.StringUtils;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.changePassword.interacter.ChangePasswordInteracter;
import com.winit.iKonnect.module.changePassword.interacter.IChangePasswordInteracter;
import com.winit.iKonnect.ui.activities.ChangePasswordActivity;


import static com.winit.iKonnect.module.changePassword.IChangePasswordView.CONFIRM_PASSWORD;
import static com.winit.iKonnect.module.changePassword.IChangePasswordView.CURRENT_PASSWORD;
import static com.winit.iKonnect.module.changePassword.IChangePasswordView.NEW_PASSWORD;
import static com.winit.iKonnect.module.changePassword.IChangePasswordView.STAFF_ID;
import static com.winit.iKonnect.module.changePassword.IChangePasswordView.VALIDATE_PASSWORD;

/**
 * Created by Rahul.Yadav on 6/7/2017.
 */

public class ChangePasswordPresenter extends BasePresenter implements IChangePasswordPresenter,ChangePasswordInteracter.OnChnagePasswordListener{
    private IChangePasswordView view;
    private IChangePasswordInteracter interacter;

    public ChangePasswordPresenter(ChangePasswordActivity view) {
        super(view);
        this.view = view;
        this.interacter = new ChangePasswordInteracter(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void submit(String StaffNumber, String newPassword, String oldPassword,String confirmPassword) {
        if(StringUtils.isEmpty(StaffNumber)){
            view.showAlert(STAFF_ID);
        }
       else if(StringUtils.isEmpty(oldPassword)){
            view.showAlert(CURRENT_PASSWORD);
        }
       else if(StringUtils.isEmpty(newPassword)){
            view.showAlert(NEW_PASSWORD);
        }
       else if(StringUtils.isEmpty(confirmPassword)){
            view.showAlert(CONFIRM_PASSWORD);
        }
       else if(!StringUtils.isEmpty(newPassword)&&!StringUtils.isEmpty(confirmPassword)&& !confirmPassword.equalsIgnoreCase(newPassword)){
            view.showAlert(VALIDATE_PASSWORD);
        }
        else {
            interacter.postData(StaffNumber,newPassword,oldPassword);
        }
    }

    @Override
    public void onError(String Message) {
        view.showAlert(Message);
    }

    @Override
    public void onSuccess(int status, String msg) {
    view.showAlert(msg);
    }
}
