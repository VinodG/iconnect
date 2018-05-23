package com.winit.iKonnect.dataobject;

import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.response.BaseResponse;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ojha.Sandeep on 12/6/2017.
 */

public class ContactsListDOArrayList extends BaseResponse {
   public ArrayList<ContactListDO> TopicModels;

    public ArrayList<ContactListDO> getTopicModels() {
        return TopicModels;
    }

    public void setTopicModels(ArrayList<ContactListDO> topicModels) {
        TopicModels = topicModels;
    }
}
