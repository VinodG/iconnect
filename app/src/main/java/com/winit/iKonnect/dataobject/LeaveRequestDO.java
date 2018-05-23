package com.winit.iKonnect.dataobject;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class LeaveRequestDO extends FormRequestDO{
    public String fromDate="";

    private String TypeofRequest;
   /* private String RequireDrivingLicence;
    private String CostChargeTo;*/

    public String getTypeofRequest() {
        return TypeofRequest;
    }

    public void setTypeofRequest(String typeofRequest) {
        TypeofRequest = typeofRequest;
    }

  /*  public String getRequireDrivingLicence() {
        return RequireDrivingLicence;
    }

    public void setRequireDrivingLicence(String requireDrivingLicence) {
        RequireDrivingLicence = requireDrivingLicence;
    }

    public String getCostChargeTo() {
        return CostChargeTo;
    }

    public void setCostChargeTo(String costChargeTo) {
        CostChargeTo = costChargeTo;
    }*/

}
