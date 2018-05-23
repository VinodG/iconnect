package com.winit.iKonnect.dataobject;

import java.text.NumberFormat;

/**
 * Created by Girish Velivela on 5/16/2017.
 */

public class PostFeedActionDO extends BaseDO {
    private long cmspostId;
    private String staffNumber;
    private String ActionType; //"OPEN","LIKE","COMMENT","SHARE","FAVORITE"
    private String[] Comment;

    public transient static final String OPEN = "OPEN";
    public transient static final String LIKE = "LIKE";
    public transient static final String COMMENT = "COMMENT";
    public transient static final String SHARE = "SHARE";
    public transient static final String FAVORITE = "FAVORITE";
    public transient static final String VIEW = "VIEW";

    public static String format(String formatter,int count,String type){
        if(count<=1){
            String countStr = String.format(formatter, NumberFormat.getInstance().format(count));
            return countStr.substring(0,countStr.length()-1);
        }else if(count >0)
            return String.format(formatter,NumberFormat.getInstance().format(count));
        else
            return "";
    }

    public long getCmspostId() {
        return cmspostId;
    }

    public void setCmspostId(long cmspostId) {
        this.cmspostId = cmspostId;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getActionType() {
        return ActionType;
    }

    public void setActionType(String actionType) {
        ActionType = actionType;
    }

    public String[] getComment() {
        return Comment;
    }

    public void setComment(String[] comment) {
        Comment = comment;
    }
}
