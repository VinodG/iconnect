package com.winit.iKonnect.module.feed;

import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.module.base.IBaseView;

import java.util.List;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public interface IFeedView extends IBaseView{
    String NO_FEEDS = "NO_FEEDS";
    String NO_COMMENTS = "NO_DETAILS";
    void onFeeds(List<FeedsDO> feeds);
    void noOfFeeds();
}
