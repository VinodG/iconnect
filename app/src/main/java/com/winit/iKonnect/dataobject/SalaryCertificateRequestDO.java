package com.winit.iKonnect.dataobject;

/**
 * Created by Sreekanth.P on 30-10-2017.
 */

public class SalaryCertificateRequestDO extends FormRequestDO {

    public String Addressedto ="";
    public String Purpose ="";
    public String OtherInstructions ="";


    public String getAddressedto() {
        return Addressedto;
    }

    public void setAddressedto(String addressedto) {
        Addressedto = addressedto;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
    }

    public String getOtherInstructions() {
        return OtherInstructions;
    }

    public void setOtherInstructions(String otherInstructions) {
        OtherInstructions = otherInstructions;
    }

}
