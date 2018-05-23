package com.winit.iKonnect.dataobject;

import java.util.ArrayList;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class MedicalExpenseClaimDO extends FormRequestDO{

    private String ClaimMember ="";
    private String AmountReimbursable="";
    private String ClinicInNetwork  ="";
    private String MedicalCardUsed  ="";
    private ArrayList<BillDetailDo> MedicalExpenseClaim_BillDetails;

    public ArrayList<BillDetailDo> getMedicalExpenseClaim_BillDetails() {
        return MedicalExpenseClaim_BillDetails;
    }

    public void setMedicalExpenseClaim_BillDetails(ArrayList<BillDetailDo> medicalExpenseClaim_BillDetails) {
        this.MedicalExpenseClaim_BillDetails = medicalExpenseClaim_BillDetails;
    }
    //    public List<MedicalExpenseClaim_BillDetailsModel> MedicalExpenseClaim_BillDetails { get; set; }

    public String getClaimMember() {
        return ClaimMember;
    }

    public void setClaimMember(String claimMember) {
        ClaimMember = claimMember;
    }

    public String getAmountReimbursable() {
        return AmountReimbursable;
    }

    public void setAmountReimbursable(String amountReimbursable) {
        AmountReimbursable = amountReimbursable;
    }

    public String getClinicInNetwork() {
        return ClinicInNetwork;
    }

    public void setClinicInNetwork(String clinicInNetwork) {
        ClinicInNetwork = clinicInNetwork;
    }

    public String getMedicalCardUsed() {
        return MedicalCardUsed;
    }

    public void setMedicalCardUsed(String medicalCardUsed) {
        MedicalCardUsed = medicalCardUsed;
    }

    public String getNameOfClinic() {
        return NameOfClinic;
    }

    public void setNameOfClinic(String nameOfClinic) {
        NameOfClinic = nameOfClinic;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        Total = total;
    }

    private String NameOfClinic ="";
    public float Total ;
}
