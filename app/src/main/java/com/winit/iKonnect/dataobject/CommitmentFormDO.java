package com.winit.iKonnect.dataobject;

/**
 * Created by Sreekanth.P on 31-10-2017.
 */

public class CommitmentFormDO extends FormRequestDO {
    public String ContributionAmount ="";
    public String Remarks ="";

    public String getContributionAmount() {
        return ContributionAmount;
    }

    public void setContributionAmount(String contributionAmount) {
        ContributionAmount = contributionAmount;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }
}
