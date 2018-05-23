package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.FormRequestDO;
import com.winit.iKonnect.dataobject.InsuranceCardFamilyDetailsModel;

import java.util.ArrayList;

/**
 * Created by Ashoka.Reddy on 5/23/2017.
 */

public class InsuranceCardModel extends FormRequestDO {

    public ArrayList<InsuranceCardFamilyDetailsModel> getInsuranceCardFamilyDetails() {
        return InsuranceCardFamilyDetails;
    }

    public void setInsuranceCardFamilyDetails(ArrayList<InsuranceCardFamilyDetailsModel> insuranceCardFamilyDetails) {
        this.InsuranceCardFamilyDetails = insuranceCardFamilyDetails;
    }

    private ArrayList<InsuranceCardFamilyDetailsModel> InsuranceCardFamilyDetails = new ArrayList<>();


}
