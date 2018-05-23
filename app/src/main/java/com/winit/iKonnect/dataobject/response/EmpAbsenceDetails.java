package com.winit.iKonnect.dataobject.response;

/**
 * Created by Rahul.Yadav on 6/20/2017.
 */

public class EmpAbsenceDetails {
  private String StartDate = "";
  private String EndDate = "";
  private String AbsentType = "";
  private String AbsentDays = "";
  private String StartDate1 = "";
  private String EndDate1 = "";

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getAbsentType() {
        return AbsentType;
    }

    public void setAbsentType(String absentType) {
        AbsentType = absentType;
    }

    public String getAbsentDays() {
        return AbsentDays;
    }

    public void setAbsentDays(String absentDays) {
        AbsentDays = absentDays;
    }

    public String getStartDate1() {
        return StartDate1;
    }

    public void setStartDate1(String startDate1) {
        StartDate1 = startDate1;
    }

    public String getEndDate1() {
        return EndDate1;
    }

    public void setEndDate1(String endDate1) {
        EndDate1 = endDate1;
    }

    public String getAEDTM() {
        return AEDTM;
    }

    public void setAEDTM(String AEDTM) {
        this.AEDTM = AEDTM;
    }

    private String AEDTM          = "";
}
