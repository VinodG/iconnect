package com.winit.iKonnect.module.searchemployee;

import com.winit.iKonnect.ChatMemberResponce;
import com.winit.iKonnect.module.base.IBasePresenter;

/**
 * Created by Ashoka.Reddy on 7/12/2017.
 */

public interface ISearchPresenter extends IBasePresenter
{
    public void FetchedEmpDetail(ChatMemberResponce chatGroupMembers);
}
