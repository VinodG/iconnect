package com.winit.iKonnect.dataobject;

/**
 * Created by namashivaya.gangishe on 5/18/2017.
 */

public class DrivingLicenceDO extends FormRequestDO{

    private String TypeofRequest;
    private String RequireDrivingLicence;
    private String CostChargeTo;

    public String getTypeofRequest() {
        return TypeofRequest;
    }

    public void setTypeofRequest(String typeofRequest) {
        TypeofRequest = typeofRequest;
    }

    public String getRequireDrivingLicence() {
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
    }

}
