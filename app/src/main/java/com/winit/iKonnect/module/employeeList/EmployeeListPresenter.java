package com.winit.iKonnect.module.employeeList;

import com.winit.iKonnect.dataobject.EmpdetailsModels;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.employeeList.interactors.EmployeeListInteractor;
import com.winit.iKonnect.ui.activities.EmployeeListActivity;

/**
 * Created by Sreekanth.P on 28-12-2017.
 */

public class EmployeeListPresenter extends BasePresenter implements IEmployeeListPresenter
{

    IEmployeeListView iEmployeeListView;
    EmployeeListInteractor employeeListInteractor;

    public EmployeeListPresenter(IEmployeeListView iEmployeeListView)
    {
        super(iEmployeeListView);
        this.iEmployeeListView=iEmployeeListView;
        employeeListInteractor=new EmployeeListInteractor(this);
    }

    @Override
    public void loadData() {

    }

    public void getEmployeeList(int startIndex, int endIndex, String searchtext) {

        employeeListInteractor.getAllEmployees(startIndex,endIndex,searchtext);
    }


    @Override
    public void FetchedEmpDetail(EmpdetailsModels empdetailsModels) {

        if (empdetailsModels!=null)
            iEmployeeListView.FetchedEmployeeData(empdetailsModels.getEmpdetailsModelDOs());
    }
}
