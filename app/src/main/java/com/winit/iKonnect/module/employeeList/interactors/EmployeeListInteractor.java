package com.winit.iKonnect.module.employeeList.interactors;

import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.ChatMemberResponce;
import com.winit.iKonnect.dataobject.EmpdetailsModels;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;
import com.winit.iKonnect.module.employeeList.EmployeeListPresenter;
import com.winit.iKonnect.module.employeeList.IEmployeeListPresenter;

import static com.winit.iKonnect.R.string.position;

/**
 * Created by Sreekanth.P on 28-12-2017.
 */

public class EmployeeListInteractor extends AsyncBaseHttpInteractor implements IEmployeeListInteractor,HttpService.HttpListener {

    IEmployeeListPresenter iEmployeeListPresenter;

    public EmployeeListInteractor(IEmployeeListPresenter iEmployeeListPresenter) {

        this.iEmployeeListPresenter=iEmployeeListPresenter;
    }


    public void getAllEmployees(int startIndex, int endIndex, String searchtext) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_EMP_DETAILS, BuildJsonRequest.getEmployeeList(startIndex, endIndex, searchtext),this);
    }

    @Override
    public void onResponseReceived(Response response) {
        iEmployeeListPresenter.FetchedEmpDetail((EmpdetailsModels) response.data);
    }

}
