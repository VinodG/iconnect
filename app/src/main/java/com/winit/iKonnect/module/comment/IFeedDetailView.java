package com.winit.iKonnect.module.comment;

import com.winit.iKonnect.dataobject.FeedDetail;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.module.base.IBaseView;

import java.util.List;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public interface IFeedDetailView extends IBaseView{
    void onDetails(List<FeedDetail> commentDOs, FeedsDO feedsDO);
    void noComments();
}
