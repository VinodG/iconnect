package com.winit.iKonnect.dataobject.response;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.StringUtils;

/**
 * Created by Girish Velivela on 5/8/2017.
 */

public class BaseResponse  {

    private int statusCode;
    private String statusEn;
    private String statusMessageEn;
    private String statusAr;
    private String statusMessageAr;
    protected boolean isArabic;

    {
        isArabic = Preference.getInstance().getStringFromPreference(Preference.LANGUAGE,"").equalsIgnoreCase(AppConstants.ARABIC);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusEn() {
        return statusEn;
    }

    public void setStatusEn(String statusEn) {
        this.statusEn = statusEn;
    }

    public String getStatusMessageEn() {
        if(isArabic)
            return getStatusMessageAr();
        return statusMessageEn;
    }

    public void setStatusMessageEn(String statusMessageEn) {
        this.statusMessageEn = statusMessageEn;
    }

    public String getStatusAr() {
        return statusAr;
    }

    public void setStatusAr(String statusAr) {
        this.statusAr = statusAr;
    }

    public String getStatusMessageAr() {
        if(StringUtils.isEmpty(statusMessageAr))
            return statusMessageEn;
        return statusMessageAr;
    }

    public void setStatusMessageAr(String statusMessageAr) {
        this.statusMessageAr = statusMessageAr;
    }

}
