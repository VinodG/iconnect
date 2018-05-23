package com.winit.iKonnect.module.feed;

import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.module.base.IBasePresenter;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public interface IFeedActionPresenter extends IBasePresenter{
    FeedsDO getFeedsDO();
    void setFeedsDO(FeedsDO feedsDO);
    void performActionFeed(String type, final FeedsDO feedsDO);
    void getSingleFeed(String id);
}
