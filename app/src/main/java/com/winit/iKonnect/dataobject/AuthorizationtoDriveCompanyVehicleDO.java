package com.winit.iKonnect.dataobject;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class AuthorizationtoDriveCompanyVehicleDO extends FormRequestDO{
    private String TypeofRequest = "";
    private String ReasonforTrave ="";
    private String CostChargeTo= "";

    public String getTypeofRequest() {
        return TypeofRequest;
    }

    public void setTypeofRequest(String typeofRequest) {
        TypeofRequest = typeofRequest;
    }

    public String getReasonforTrave() {
        return ReasonforTrave;
    }

    public void setReasonforTrave(String reasonforTrave) {
        ReasonforTrave = reasonforTrave;
    }

    public String getCostChargeTo() {
        return CostChargeTo;
    }

    public void setCostChargeTo(String costChargeTo) {
        CostChargeTo = costChargeTo;
    }
}
