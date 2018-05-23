package com.winit.iKonnect.dataobject;

/**
 * Created by Gufran.Khan on 6/27/2017.
 */

public class ChatGroupModel extends BaseDO {

    private String groupKey;
    private int groupId;
    private String groupName;
    private int UnreadMessages=0;

    public int getUnreadMessages() {
        return UnreadMessages;
    }

    public void setUnreadMessages(int unreadMessages) {
        UnreadMessages = unreadMessages;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
