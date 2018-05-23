package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.FeedDetail;

import java.util.List;

/**
 * Created by Girish Velivela on 5/8/2017.
 */

public class LikesResponse extends BaseResponse {


    private List<FeedDetail> postlikeModels;

    public List<FeedDetail> getPostlikeModels() {
        return postlikeModels;
    }

    public void setPostlikeModels(List<FeedDetail> postlikeModels) {
        this.postlikeModels = postlikeModels;
    }
}
