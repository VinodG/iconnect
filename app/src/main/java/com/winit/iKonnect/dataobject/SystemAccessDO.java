package com.winit.iKonnect.dataobject;

/**
 * Created by Sreekanth.P on 31-10-2017.
 */

public class SystemAccessDO extends FormRequestDO {
    public String StaffNo ="";
    public String Name ="";
    public String Department ="";
    public String Designation ="";
    public String Location ="";
    public String SAP ="";
    public String SystemNetwork ="";
    public String Email ="";
    public String Internet ="";
    public String TelephoneExtension ="";
    public String Remarks ="";


    public String getStaffNo() {
        return StaffNo;
    }

    public void setStaffNo(String staffNo) {
        StaffNo = staffNo;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getSAP() {

        return SAP;
    }

    public void setSAP(String SAP) {
        this.SAP = SAP;
    }

    public String getSystemNetwork() {
        return SystemNetwork;
    }

    public void setSystemNetwork(String systemNetwork) {
        SystemNetwork = systemNetwork;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getInternet() {
        return Internet;
    }

    public void setInternet(String internet) {
        Internet = internet;
    }

    public String getTelephoneExtension() {
        return TelephoneExtension;
    }

    public void setTelephoneExtension(String telephoneExtension) {
        TelephoneExtension = telephoneExtension;
    }
}
