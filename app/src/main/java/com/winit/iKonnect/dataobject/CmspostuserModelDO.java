package com.winit.iKonnect.dataobject;

/**
 * Created by Girish Velivela on 5/16/2017.
 */

public class CmspostuserModelDO extends BaseDO {

    /**
     * id : 16
     * cmspostId : 26
     * staffNumber : 86262
     * availableTime : 0001-01-01T00:00:00
     * readTime : 2017-05-13T21:44:17.203
     * hasLiked : false
     * hasCommented : true
     * comment :
     * commentTime : 2017-05-14T12:28:14.263
     * hasShared : true
     * shareTime : 2017-05-14T22:31:29.723
     * staffName : FARIS FOUAD KHAYYAT
     * likeTime : 2017-05-14T19:01:11.033
     * hasFavorite : true
     * favoriteTime : 2017-05-14T22:56:42.577
     */

    private int id;
    private int cmspostId;
    private String staffNumber;
    private String availableTime;
    private String readTime;
    private boolean hasLiked;
    private boolean hasCommented;
    private String comment;
    private String commentTime;
    private boolean hasShared;
    private String shareTime;
    private String staffName;
    private String likeTime;
    private boolean hasFavorite;
    private String favoriteTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCmspostId() {
        return cmspostId;
    }

    public void setCmspostId(int cmspostId) {
        this.cmspostId = cmspostId;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public boolean isHasLiked() {
        return hasLiked;
    }

    public void setHasLiked(boolean hasLiked) {
        this.hasLiked = hasLiked;
    }

    public boolean isHasCommented() {
        return hasCommented;
    }

    public void setHasCommented(boolean hasCommented) {
        this.hasCommented = hasCommented;
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

    public boolean isHasShared() {
        return hasShared;
    }

    public void setHasShared(boolean hasShared) {
        this.hasShared = hasShared;
    }

    public String getShareTime() {
        return shareTime;
    }

    public void setShareTime(String shareTime) {
        this.shareTime = shareTime;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(String likeTime) {
        this.likeTime = likeTime;
    }

    public boolean isHasFavorite() {
        return hasFavorite;
    }

    public void setHasFavorite(boolean hasFavorite) {
        this.hasFavorite = hasFavorite;
    }

    public String getFavoriteTime() {
        return favoriteTime;
    }

    public void setFavoriteTime(String favoriteTime) {
        this.favoriteTime = favoriteTime;
    }

}
