package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.CmspoststatModelDO;
import com.winit.iKonnect.dataobject.CmspostuserModelDO;

/**
 * Created by Girish Velivela on 5/8/2017.
 */

public class PostFeedActionResponse extends BaseResponse {

    /**
     * cmspostuserModel : {"id":16,"cmspostId":26,"staffNumber":"86262","availableTime":"0001-01-01T00:00:00","readTime":"2017-05-13T21:44:17.203","hasLiked":false,"hasCommented":true,"comment":"","commentTime":"2017-05-14T12:28:14.263","hasShared":true,"shareTime":"2017-05-14T22:31:29.723","staffName":"FARIS FOUAD KHAYYAT","likeTime":"2017-05-16T14:13:18.113","hasFavorite":true,"favoriteTime":"2017-05-14T22:56:42.577"}
     * cmspoststatModel : {"id":9,"cmspostId":26,"noOfSharing":6,"noOfComments":11,"noOfLikes":1,"noOfViews":40}
     */
    private CmspostuserModelDO cmspostuserModel;

    private CmspoststatModelDO cmspoststatModel;

    public CmspoststatModelDO getCmspoststatModel() {
        return cmspoststatModel;
    }

    public void setCmspoststatModel(CmspoststatModelDO cmspoststatModel) {
        this.cmspoststatModel = cmspoststatModel;
    }

    public CmspostuserModelDO getCmspostuserModel() {
        return cmspostuserModel;
    }

    public void setCmspostuserModel(CmspostuserModelDO cmspostuserModel) {
        this.cmspostuserModel = cmspostuserModel;
    }

}
