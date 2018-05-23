package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.view.View;

import com.winit.common.Preference;
import com.winit.iKonnect.R;
import com.winit.iKonnect.module.usernavigationactivity.INavigationPresenter;
import com.winit.iKonnect.module.usernavigationactivity.INavigationView;
import com.winit.iKonnect.module.usernavigationactivity.NavigationPresentor;

import butterknife.ButterKnife;

/**
 * Created by namashivaya.gangishe on 5/26/2017.
 */

public class UserNavigationActivity extends BaseActivity implements INavigationView{

    private String From="";
    private INavigationPresenter presenter;
    @Override
    protected void initialize() {
        inflater.inflate(R.layout.user_navigation, flBody, true);
        ButterKnife.bind(this);
        presenter = new NavigationPresentor(UserNavigationActivity.this);
        if(getIntent().hasExtra("From"))
            From= getIntent().getExtras().getString("From");
        if(From!=null && From.equalsIgnoreCase("menu"))
        {
            presenter.pushServiceForLogout(preference.getStringFromPreference(Preference.STAFF_NUMBER,""),preference.getStringFromPreference(Preference.GCM_ID,""));
        }
    }

    @Override
    protected void setTypeFace() {

    }

    public void goToLogin(View view){
        Intent intent = new Intent(UserNavigationActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void moveToSignUp(View view)
    {
        Intent intent = new Intent(UserNavigationActivity.this,SignUpActivity.class);
        intent.putExtra("isFrom","");
        startActivity(intent);
    }
    /*public void  freeuser(View view){
        Intent intent = new Intent(UserNavigationActivity.this,FreeUserActivityNew.class);
        startActivity(intent);
    }*/


    @Override
    public void showAlert(String type) {

    }

    @Override
    public void onLoadFailed() {

    }
}
