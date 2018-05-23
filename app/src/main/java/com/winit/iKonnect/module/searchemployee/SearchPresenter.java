package com.winit.iKonnect.module.searchemployee;

import com.winit.iKonnect.ChatMemberResponce;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.searchemployee.Interactor.SearchInteractor;

/**
 * Created by Ashoka.Reddy on 7/12/2017.
 */

public class SearchPresenter extends BasePresenter implements ISearchPresenter
{
    private iSearchView iBaseView;
    private SearchInteractor interactor;

    public SearchPresenter(iSearchView iBaseView)
    {
        super(iBaseView);
        this.iBaseView=iBaseView;
        interactor = new SearchInteractor(this);
    }
    @Override
    public void loadData()
    {

    }

    public void loadEmployees(String str)
    {
        interactor.FetchSearchedItems(str, 0);
    }
    public void loadNextEmployees(String str, int position)
    {
        interactor.FetchSearchedItems(str, position);
    }

    @Override
    public void FetchedEmpDetail(ChatMemberResponce chatGroupMembers)
    {
        if(chatGroupMembers!=null)
        iBaseView.FetchedEmployeeData(chatGroupMembers.getChatMemberModels());
    }
}
