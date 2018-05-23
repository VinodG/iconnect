package com.winit.iKonnect.ui.activities;

import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.winit.iKonnect.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sreekanth.P on 11-12-2017.
 */

public class PaySlipActivityNew extends BaseActivity{

    @Nullable
    @Bind(R.id.webView)
    WebView webView;
    @Override
    protected void initialize() {
        inflater.inflate(R.layout.pay_slip_activity_new, flBody, true);
        ButterKnife.bind(this);
        setToolbarTitle("Pay Slip");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https:\\www.google.com");
        webView.setHorizontalScrollBarEnabled(false);
    }

    @Override
    protected void setTypeFace() {

    }
}
