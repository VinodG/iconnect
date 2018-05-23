package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.BaseDO;

/**
 * Created by Ashoka.Reddy on 7/22/2017.
 */

public class ForgetotpDo extends BaseDO {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusEn() {
        return statusEn;
    }

    public void setStatusEn(String statusEn) {
        this.statusEn = statusEn;
    }

    public String getStatusMessageEn() {
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
        return statusMessageAr;
    }

    public void setStatusMessageAr(String statusMessageAr) {
        this.statusMessageAr = statusMessageAr;
    }

    private String mobile;
    private String statusCode;
    private String statusEn;
    private String statusMessageEn;
    private String statusAr;
    private String statusMessageAr;

    public String getWorkCountry() {
        return WorkCountry;
    }

    public void setWorkCountry(String workCountry) {
        WorkCountry = workCountry;
    }

    private String WorkCountry;
}
