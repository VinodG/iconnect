package com.winit.iKonnect.dataobject;

/**
 * Created by Ojha.Sandeep on 11/1/2017.
 */

public class LeaveApplicationDO extends FormRequestDO {

    public String TypeofLeave = "";
    public String LeaveStartDate = "";
    public String LeaveEndDate = "";
    public String SalaryAdvance = "";
    public String ContactNo = "";
    public String DoyouWantLeaveSalary = "";
    public String ReasonEmergency = "";
    public String TotalLeaves = "";

    public String getTotalLeaves() {
        return TotalLeaves;
    }

    public void setTotalLeaves(String totalLeaves) {
        TotalLeaves = totalLeaves;
    }


    public String getReasonEmergency() {
        return ReasonEmergency;
    }

    public void setReasonEmergency(String reasonEmergency) {
        ReasonEmergency = reasonEmergency;
    }

    public String getTypeofLeave() {
        return TypeofLeave;
    }

    public void setTypeofLeave(String typeofLeave) {
        TypeofLeave = typeofLeave;
    }

    public String getLeaveStartDate() {
        return LeaveStartDate;
    }

    public void setLeaveStartDate(String leaveStartDate) {
        LeaveStartDate = leaveStartDate;
    }

    public String getLeaveEndDate() {
        return LeaveEndDate;
    }

    public void setLeaveEndDate(String leaveEndDate) {
        LeaveEndDate = leaveEndDate;
    }

    public String getSalaryAdvance() {
        return SalaryAdvance;
    }

    public void setSalaryAdvance(String salaryAdvance) {
        SalaryAdvance = salaryAdvance;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getDoyouWantLeaveSalary() {
        return DoyouWantLeaveSalary;
    }

    public void setDoyouWantLeaveSalary(String doyouWantLeaveSalary) {
        DoyouWantLeaveSalary = doyouWantLeaveSalary;
    }
}
