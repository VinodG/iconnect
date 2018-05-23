package com.winit.iKonnect.dataobject;

/**
 * Created by Girish Velivela on 5/16/2017.
 */

public class CmspoststatModelDO extends BaseDO {

    /**
     * id : 9
     * cmspostId : 26
     * noOfSharing : 6
     * noOfComments : 11
     * noOfLikes : 1
     * noOfViews : 40
     */

    private int id;
    private int cmspostId;
    private int noOfSharing;
    private int noOfComments;
    private int noOfLikes;
    private int noOfViews;

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

    public int getNoOfSharing() {
        return noOfSharing;
    }

    public void setNoOfSharing(int noOfSharing) {
        this.noOfSharing = noOfSharing;
    }

    public int getNoOfComments() {
        return noOfComments;
    }

    public void setNoOfComments(int noOfComments) {
        this.noOfComments = noOfComments;
    }

    public int getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(int noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public int getNoOfViews() {
        return noOfViews;
    }

    public void setNoOfViews(int noOfViews) {
        this.noOfViews = noOfViews;
    }

}
