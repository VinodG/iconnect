package com.winit.iKonnect.module.searchemployee.Interactor;

import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.ChatMemberResponce;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;
import com.winit.iKonnect.module.searchemployee.ISearchPresenter;

/**
 * Created by Ashoka.Reddy on 7/12/2017.
 */

public class SearchInteractor extends AsyncBaseHttpInteractor implements ISearchInteractor,HttpService.HttpListener
{
    public ISearchPresenter presenterView;
    public SearchInteractor(ISearchPresenter presenterView)
    {
        this.presenterView=presenterView;
    }
    @Override
    public void FetchSearchedItems(String str, int position)
    {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_EMPLOYEE_NAMES, BuildJsonRequest.getFilterEmployee(str, position),this);
    }

    @Override
    public void onResponseReceived(Response response)
    {
        presenterView.FetchedEmpDetail((ChatMemberResponce) response.data);
    }
}
