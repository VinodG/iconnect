package com.winit.iKonnect.dataobject.response;

/**
 * Created by Rahul.Yadav on 5/27/2017.
 */

public class LoginResponse extends BaseResponse {
    EmpModelDO empModel;

    public EmpModelDO getEmpModel() {
        return empModel;
    }

    public void setEmpModel(EmpModelDO empModel) {
        this.empModel = empModel;
    }
}
