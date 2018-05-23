package com.winit.iKonnect.module.chat.interactors;

import com.winit.iKonnect.dataobject.ChatGroupDO;
import com.winit.iKonnect.dataobject.ChatMemberDO;
import com.winit.iKonnect.module.base.interactors.IBaseInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public interface IAsyncChatInteractor extends IBaseInteractor{
    void fetchChatGroups(String staffNumber);
    void fetchStaffDetails(String staffNumebr);

    /**
     * Created by Girish Velivela on 5/11/15.
     */
    interface OnChatGroupListener extends BaseListener{
        void onSuccess(List<ChatGroupDO> chatGroupDOs);
        void onLoadChatMember(ArrayList<ChatMemberDO> arrayList);
    }
}
