package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.FeedsDO;

import java.util.List;

/**
 * Created by Girish Velivela on 5/8/2017.
 */

public class FeedsResponse extends BaseResponse {

    private List<FeedsDO> cmspostModels;

    public List<FeedsDO> getCmspostModels() {
        return cmspostModels;
    }

    public void setCmspostModels(List<FeedsDO> cmspostModels) {
        this.cmspostModels = cmspostModels;
    }

}
