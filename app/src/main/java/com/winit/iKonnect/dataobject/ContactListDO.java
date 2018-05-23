package com.winit.iKonnect.dataobject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ojha.Sandeep on 12/6/2017.
 */

public class ContactListDO extends BaseDO {
    public ArrayList<SubTopicModelDO> SubTopicModel;
    public String TopicName;

    public ArrayList<SubTopicModelDO> getSubTopicModel() {
        return SubTopicModel;
    }

    public void setSubTopicModel(ArrayList<SubTopicModelDO> subTopicModel) {
        SubTopicModel = subTopicModel;
    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String topicName) {
        TopicName = topicName;
    }



}
