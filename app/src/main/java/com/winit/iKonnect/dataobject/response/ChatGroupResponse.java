package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.ChatGroupDO;

import java.util.List;

/**
 * Created by Gufran.Khan on 6/29/2017.
 */

public class ChatGroupResponse extends BaseResponse {

    private List<ChatGroupDO> chatgroupModels;

    public List<ChatGroupDO> getChatgroupModels() {
        return chatgroupModels;
    }

    public void setChatgroupModels(List<ChatGroupDO> chatgroupModels) {
        this.chatgroupModels = chatgroupModels;
    }

}
