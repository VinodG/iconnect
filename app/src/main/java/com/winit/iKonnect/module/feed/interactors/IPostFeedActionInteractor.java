package com.winit.iKonnect.module.feed.interactors;

import com.winit.iKonnect.dataobject.CmspoststatModelDO;
import com.winit.iKonnect.dataobject.CmspostuserModelDO;
import com.winit.iKonnect.dataobject.PostFeedActionDO;
import com.winit.iKonnect.dataobject.response.FeedsResponse;
import com.winit.iKonnect.module.base.interactors.IBaseInteractor;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public interface IPostFeedActionInteractor extends IBaseInteractor{
    void postFeedAction(PostFeedActionDO postFeedActionDO);
    void getSingleFeed(String id);

    /**
     * Created by Girish Velivela on 5/11/15.
     */
    interface OnPostFeedActionListener extends BaseListener{
        void onSuccess(CmspostuserModelDO cmspostuserModel, CmspoststatModelDO cmspoststatModel);
        void onSingleFeedSuccess(FeedsResponse feedresponseDo);
    }
}
