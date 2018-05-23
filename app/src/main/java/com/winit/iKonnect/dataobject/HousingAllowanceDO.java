package com.winit.iKonnect.dataobject;

/**
 * Created by Ojha.Sandeep on 11/1/2017.
 */

public class HousingAllowanceDO extends FormRequestDO {
    public String Name = "";
    public String Location = "";
    public String Designation = "";
    public String Department = "";
    public String AdvanceRequest = "";


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getAdvanceRequest() {
        return AdvanceRequest;
    }

    public void setAdvanceRequest(String advanceRequest) {
        AdvanceRequest = advanceRequest;
    }
}
