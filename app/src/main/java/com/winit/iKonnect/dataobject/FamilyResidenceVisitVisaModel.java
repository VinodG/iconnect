package com.winit.iKonnect.dataobject;

import com.winit.iKonnect.dataobject.response.FamilyDetailDO;

import java.util.ArrayList;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class FamilyResidenceVisitVisaModel extends FormRequestDO{
    private String VisaType ="";
    private String ChamberofCommerceStatus  ="";
    private String EligibleasperEmploymentContract="";
    private String CostChargeTo = "";
    private String SaudiEmbassy = "";
    ArrayList<FamilyDetailDO> arrfamilyDetail;
    private ArrayList<FamilyResidenceVisitVisa_FamilyDetails> FamilyResidenceVisitVisa_FamilyDetails;

    public ArrayList<FamilyResidenceVisitVisa_FamilyDetails> getFamilyResidenceVisitVisa_FamilyDetails() {
        return FamilyResidenceVisitVisa_FamilyDetails;
    }

    public void setFamilyResidenceVisitVisa_FamilyDetails(ArrayList<FamilyResidenceVisitVisa_FamilyDetails> familyResidenceVisitVisa_FamilyDetails) {
        this.FamilyResidenceVisitVisa_FamilyDetails = familyResidenceVisitVisa_FamilyDetails;
    }
    //    private String List<FamilyResidenceVisitVisa_FamilyDetailsModel>
//    FamilyResidenceVisitVisa_FamilyDetails { get; set; }

    public String getVisaType() {
        return VisaType;
    }

    public void setVisaType(String visaType) {
        VisaType = visaType;
    }

    public String getChamberofCommerceStatus() {
        return ChamberofCommerceStatus;
    }

    public void setChamberofCommerceStatus(String chamberofCommerceStatus) {
        ChamberofCommerceStatus = chamberofCommerceStatus;
    }

    public String getEligibleasperEmploymentContract() {
        return EligibleasperEmploymentContract;
    }

    public void setEligibleasperEmploymentContract(String eligibleasperEmploymentContract) {
        EligibleasperEmploymentContract = eligibleasperEmploymentContract;
    }

    public String getCostChargeTo() {
        return CostChargeTo;
    }

    public void setCostChargeTo(String costChargeTo) {
        CostChargeTo = costChargeTo;
    }

    public String getSaudiEmbassy() {
        return SaudiEmbassy;
    }

    public void setSaudiEmbassy(String saudiEmbassy) {
        SaudiEmbassy = saudiEmbassy;
    }
}
