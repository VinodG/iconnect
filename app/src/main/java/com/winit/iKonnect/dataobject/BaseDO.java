package com.winit.iKonnect.dataobject;

import android.databinding.BaseObservable;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;

import java.io.Serializable;

/**
 */
public class BaseDO extends BaseObservable implements Serializable {

    protected boolean isArabic;
    {
        isArabic = Preference.getInstance().getStringFromPreference(Preference.LANGUAGE,"").equalsIgnoreCase(AppConstants.ARABIC);
    }

    public void setArabic(boolean arabic) {
        isArabic = arabic;
    }
}
