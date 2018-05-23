package com.winit.iKonnect.dataobject;

/**
 * Created by Girish Velivela on 5/17/2017.
 */

public class FeedDetail extends BaseDO {

    /**
     * staffNumber : 86262
     * staffName : FARIS FOUAD KHAYYAT
     * photoUrl : 86262.jpg
     * comment : rest
     * commentTime : 2017-05-13T21:52:20.193
     */

    private long staffNumber;
    private String staffName;
    private String photoUrl;
    private String comment;
    private String likeTime;
    private String commentTime;

    public long getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(long staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(String likeTime) {
        this.likeTime = likeTime;
    }
}
