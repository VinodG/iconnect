package com.winit.iKonnect.dataobject;

/**
 * Created by Girish Velivela on 5/16/2017.
 */

public class CmspostsettingModelDO extends BaseDO {

    /**
     * id : 24
     * cmspostId : 26
     * hasSharing : true
     * hasComments : true
     * hasLike : true
     * isPinned : true
     * pinUpto : 2017-05-20T00:00:00
     */

    private int id;
    private int cmspostId;
    private boolean hasSharing;
    private boolean hasComments;
    private boolean hasLike;
    private boolean isPinned;
    private String pinUpto;

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

    public boolean isHasSharing() {
        return hasSharing;
    }

    public void setHasSharing(boolean hasSharing) {
        this.hasSharing = hasSharing;
    }

    public boolean isHasComments() {
        return hasComments;
    }

    public void setHasComments(boolean hasComments) {
        this.hasComments = hasComments;
    }

    public boolean isHasLike() {
        return hasLike;
    }

    public void setHasLike(boolean hasLike) {
        this.hasLike = hasLike;
    }

    public boolean isIsPinned() {
        return isPinned;
    }

    public void setIsPinned(boolean isPinned) {
        this.isPinned = isPinned;
    }

    public String getPinUpto() {
        return pinUpto;
    }

    public void setPinUpto(String pinUpto) {
        this.pinUpto = pinUpto;
    }

}
