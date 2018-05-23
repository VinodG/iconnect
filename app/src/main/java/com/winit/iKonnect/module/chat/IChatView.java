package com.winit.iKonnect.module.chat;

import com.winit.iKonnect.dataobject.ChatGroupDO;
import com.winit.iKonnect.dataobject.ChatMemberDO;
import com.winit.iKonnect.module.base.IBaseView;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public interface IChatView extends IBaseView{
    void onGroupsFetched(List<ChatGroupDO> chatGroupDOs);
    void noGroups();
    public void getChatMemberInfo(ArrayList<ChatMemberDO> arrEmployee);
}
