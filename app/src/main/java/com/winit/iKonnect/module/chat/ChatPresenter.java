package com.winit.iKonnect.module.chat;


import com.winit.common.Preference;
import com.winit.iKonnect.dataobject.ChatGroupDO;
import com.winit.iKonnect.dataobject.ChatMemberDO;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.chat.interactors.AsyncChatInteractor;
import com.winit.iKonnect.module.chat.interactors.IAsyncChatInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public class ChatPresenter extends BasePresenter implements IChatPresenter,IAsyncChatInteractor.OnChatGroupListener{

    private IAsyncChatInteractor interactor;
    private IChatView view;

    public ChatPresenter(IChatView view){
        super(view);
        this.view = view;
        this.interactor = new AsyncChatInteractor(this);
    }

    @Override
    public void onError(final String Message) {
        handler.postResult(new Runnable() {
            @Override
            public void run() {
                view.showAlert(Message);
            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void getChatGroups() {
        interactor.fetchChatGroups(Preference.getInstance().getStringFromPreference(Preference.STAFF_NUMBER,""));
    }

    @Override
    public void getChatMemberInfo(String staffNumber)
    {
        interactor.fetchStaffDetails(staffNumber);
    }

    @Override
    public void onSuccess(final List<ChatGroupDO> chatGroupDOs) {
        handler.postResult(new Runnable() {
            @Override
            public void run() {
                if(chatGroupDOs != null && chatGroupDOs.size() >0)
                    view.onGroupsFetched(chatGroupDOs);
                else
                    view.noGroups();
            }
        });
    }

    @Override
    public void onLoadChatMember(final ArrayList<ChatMemberDO> arrayList)
    {
        handler.postResult(new Runnable() {
            @Override
            public void run() {
                if(arrayList!=null && arrayList.size()>0)
                    view.getChatMemberInfo(arrayList);
            }
        });
    }
}
