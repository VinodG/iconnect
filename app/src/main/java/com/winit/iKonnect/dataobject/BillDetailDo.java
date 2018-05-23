package com.winit.iKonnect.dataobject;

/**
 * Created by Rohitmanohar on 23-05-2017.
 */

public class BillDetailDo extends BaseDO {
    public String PaymentDetails ="";
    public String FCurr ="";

    public String getPaymentDetails() {
        return PaymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        PaymentDetails = paymentDetails;
    }

    public String getFCurr() {
        return FCurr;
    }

    public void setFCurr(String FCurr) {
        this.FCurr = FCurr;
    }

    public String getSAREqui() {
        return SAREqui;
    }

    public void setSAREqui(String SAREqui) {
        this.SAREqui = SAREqui;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String SAREqui ="";
    public String Remarks ="";
}
