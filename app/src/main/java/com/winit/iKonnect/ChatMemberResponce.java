package com.winit.iKonnect;

import com.winit.iKonnect.dataobject.ChatMemberDO;
import com.winit.iKonnect.dataobject.response.BaseResponse;

import java.util.ArrayList;

/**
 * Created by Rohitmanohar on 12-07-2017.
 */

public class ChatMemberResponce extends BaseResponse
{
    public ArrayList<ChatMemberDO> chatMemberModels= new ArrayList<ChatMemberDO>();

    public ArrayList<ChatMemberDO> getChatMemberModels() {
        return chatMemberModels;
    }

    public void setChatMemberModels(ArrayList<ChatMemberDO> chatMemberModels) {
        this.chatMemberModels = chatMemberModels;
    }
}
