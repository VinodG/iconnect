package com.winit.iKonnect.module.comment.interactors;

import com.winit.iKonnect.dataobject.FeedDetail;
import com.winit.iKonnect.module.base.interactors.IBaseInteractor;

import java.util.List;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public interface IAsyncDetailInteractor extends IBaseInteractor{
    void fetchDetails(int postId,String type);

    /**
     * Created by Girish Velivela on 5/11/15.
     */
    interface OnFeedDetailsListener extends IBaseInteractor.BaseListener{
        void onSuccess(List<FeedDetail> commentDOs);
    }
}
