package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.BaseDO;

/**
 * Created by Sreekanth.P on 28-12-2017.
 */

public class EmpdetailsModelDO extends BaseDO{

    public String StaffNumber="";
    public String KnownAs="";
    public String PhotoUrl="";
    public String PersonalArea="";
    public String PersonalSubArea="";
    public String Mobile="";
    public String Email="";

    public String getStaffNumber() {
        return StaffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        StaffNumber = staffNumber;
    }

    public String getKnownAs() {
        return KnownAs;
    }

    public void setKnownAs(String knownAs) {
        KnownAs = knownAs;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public String getPersonalArea() {
        return PersonalArea;
    }

    public void setPersonalArea(String personalArea) {
        PersonalArea = personalArea;
    }

    public String getPersonalSubArea() {
        return PersonalSubArea;
    }

    public void setPersonalSubArea(String personalSubArea) {
        PersonalSubArea = personalSubArea;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
