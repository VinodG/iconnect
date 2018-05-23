package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;

import butterknife.ButterKnife;

/**
 * Created by Ashoka.Reddy on 5/26/2017.
 */

public class TermsandCondition extends BaseActivity {

    private Button btnIAgree;
    private TextView tvB1,tvB2,tvB3,tvB4,tvB5,tvB6,tvB7;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6;
    private WebView webView;
    private ProgressBar progressBar;
    @Override
    protected void initialize() {
        inflater.inflate(R.layout.termsandcondition, flBody, true);
        ButterKnife.bind(this);
        setToolbarTitle(getString(R.string.tandc));
        initializeControls();
        Intent intent = getIntent();
        if(intent.hasExtra("IAgreeBtnVisible") && intent
                .getBooleanExtra("IAgreeBtnVisible",false)){
            btnIAgree.setVisibility(View.VISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        else{
            btnIAgree.setVisibility(View.GONE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        webView.getSettings().setJavaScriptEnabled(true);
        if(preference.getStringFromPreference(Preference.LANGUAGE,"English").equalsIgnoreCase(AppConstants.ENGLISH))
            webView.loadUrl(""+ ServiceUrls.TermsAndCondition);
        else
            webView.loadUrl(""+ServiceUrls.TermsAndCondition_Ar);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initializeControls(){
        btnIAgree = (Button)findViewById(R.id.btnIAgree);
        tvB1 = (TextView) findViewById(R.id.tvB1);
        tvB2 = (TextView) findViewById(R.id.tvB2);
        tvB3 = (TextView) findViewById(R.id.tvB3);
        tvB4 = (TextView) findViewById(R.id.tvB4);
        tvB5 = (TextView) findViewById(R.id.tvB5);
        tvB6 = (TextView) findViewById(R.id.tvB6);
        tvB7 = (TextView) findViewById(R.id.tvB7);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
    }
    @Override
    protected void setTypeFace() {

        IKonnectApplication.setTypeFace(tvB1, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvB2, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvB3, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvB4, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvB5, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvB6, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvB7, AppConstants.BOLD);

        IKonnectApplication.setTypeFace(tv1, AppConstants.REGULAR);
        IKonnectApplication.setTypeFace(tv2, AppConstants.REGULAR);
        IKonnectApplication.setTypeFace(tv3, AppConstants.REGULAR);
        IKonnectApplication.setTypeFace(tv4, AppConstants.REGULAR);
        IKonnectApplication.setTypeFace(tv5, AppConstants.REGULAR);
        IKonnectApplication.setTypeFace(tv6, AppConstants.REGULAR);

    }

    public void goTONextScreen(View view){
        preference.saveBooleanInPreference(preference.IS_AGREE_WITH_TERMS,true);
        Intent intent = new Intent(TermsandCondition.this,AppDetailPagerActivity.class);
        finish();
        startActivity(intent);
    }
}

