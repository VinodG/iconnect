package com.winit.iKonnect.dataobject;

import com.winit.iKonnect.dataobject.response.BaseResponse;
import com.winit.iKonnect.dataobject.response.EmpdetailsModelDO;

import java.util.ArrayList;

/**
 * Created by Sreekanth.P on 28-12-2017.
 */

public class EmpdetailsModels extends BaseResponse{

    ArrayList<EmpdetailsModelDO>EmpdetailsModels;

    public ArrayList<EmpdetailsModelDO> getEmpdetailsModelDOs() {
        return EmpdetailsModels;
    }

    public void setEmpdetailsModelDOs(ArrayList<EmpdetailsModelDO> empdetailsModelDOs) {
        this.EmpdetailsModels = empdetailsModelDOs;
    }
}
