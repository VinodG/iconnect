package com.winit.iKonnect.module.base.interactors;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.HttpService;

/**
 * on 9/25/2016.
 */

public abstract class AsyncBaseHttpInteractor implements IBaseInteractor,HttpService.HttpListener {

    protected boolean isArabic;

    public AsyncBaseHttpInteractor(){
        isArabic = Preference.getInstance().getStringFromPreference(Preference.LANGUAGE,"").equalsIgnoreCase(AppConstants.ARABIC);
    }

}
