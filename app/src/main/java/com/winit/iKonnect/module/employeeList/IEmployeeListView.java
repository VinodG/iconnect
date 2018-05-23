package com.winit.iKonnect.module.employeeList;

import com.winit.iKonnect.dataobject.ChatMemberDO;
import com.winit.iKonnect.dataobject.response.EmpModelDO;
import com.winit.iKonnect.dataobject.response.EmpdetailsModelDO;
import com.winit.iKonnect.module.base.IBaseView;

import java.util.ArrayList;

/**
 * Created by Sreekanth.P on 28-12-2017.
 */

public interface IEmployeeListView extends IBaseView {

    public void FetchedEmployeeData(ArrayList<EmpdetailsModelDO> arrEmployee);
}
