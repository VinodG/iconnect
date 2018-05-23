package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.FeedDetail;

import java.util.List;

/**
 * Created by Girish Velivela on 5/8/2017.
 */

public class CommentsResponse extends BaseResponse {

    private List<FeedDetail> postcommentModels;

    public List<FeedDetail> getPostcommentModels() {
        return postcommentModels;
    }

    public void setPostcommentModels(List<FeedDetail> postcommentModels) {
        this.postcommentModels = postcommentModels;
    }

}
