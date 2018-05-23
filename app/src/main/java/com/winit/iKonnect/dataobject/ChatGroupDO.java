package com.winit.iKonnect.dataobject;

/**
 * Created by Gufran.Khan on 6/29/2017.
 */

public class ChatGroupDO extends BaseDO {

    private int id;
    private String name;
    private String description;
    private int unreadMessages=0;
    public String groupKey="";

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public int getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(int unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
