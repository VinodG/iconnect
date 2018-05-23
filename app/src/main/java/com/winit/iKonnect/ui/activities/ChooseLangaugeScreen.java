package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;

/**
 * Created by Ashoka.Reddy on 7/14/2017.
 */

public class ChooseLangaugeScreen extends BaseActivity {
    private TextView tv_sel_lang;
    private LinearLayout ll_proceed;
    private ImageView img_change_lang;
    private String isLanguageSelected = "";

    @Override
    protected void initialize() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        inflater.inflate(R.layout.choosepasswordscreen, flBody, true);
        initializecontrols();
        isLanguageSelected = preference.getStringFromPreference(Preference.LANGUAGE, "");
        if(TextUtils.isEmpty(isLanguageSelected))
            isLanguageSelected=AppConstants.ENGLISH;

        if (isLanguageSelected.equalsIgnoreCase(AppConstants.ARABIC)) {
//            img_change_lang.setImageResource(R.drawable.arabic_btn);
        } else {
//            img_change_lang.setImageResource(R.drawable.english);
        }
        img_change_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLanguageSelected.equalsIgnoreCase(AppConstants.ARABIC)) {
                    isLanguageSelected = AppConstants.ENGLISH;
//                    img_change_lang.setImageResource(R.drawable.english);

                } else {
                    isLanguageSelected = AppConstants.ARABIC;
//                    img_change_lang.setImageResource(R.drawable.arabic_btn);
                }
            }
        });
        ll_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLangaugeScreen.this, TermsandCondition.class);
                intent.putExtra("IAgreeBtnVisible", true);
                startActivity(intent);
                preference.saveStringInPreference(Preference.LANGUAGE, isLanguageSelected);
                setLanguage();
                finish();
            }
        });
    }

    private void initializecontrols() {
        tv_sel_lang = (TextView) findViewById(R.id.tv_sel_lang);
        ll_proceed = (LinearLayout) findViewById(R.id.ll_proceed);
        img_change_lang = (ImageView) findViewById(R.id.img_change_lang);
    }

    @Override
    protected void setTypeFace() {
        tv_sel_lang.setTypeface(AppConstants.BOLD);
    }
}
