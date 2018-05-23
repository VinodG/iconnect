package com.winit.iKonnect.module.feed;

import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.dataobject.response.FeedsResponse;
import com.winit.iKonnect.module.base.IBaseView;

/**
 * Created by Gufran.Khan on 6/1/2017.
 */

public interface IFeedActionView extends IBaseView{
    void onActionSuccess(String type, FeedsDO feedsDO);
    void setSingleFeed(FeedsResponse feedresponseDo);
}
