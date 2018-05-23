package com.winit.iKonnect.module.comment;

import com.winit.iKonnect.dataobject.FeedsDO;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public interface IFeedDetailPresenter {
    void setCommentEnable(boolean commentEnable);
    void setType(String type);
    void setFeedsDO(FeedsDO feedsDO);
    void getDetails(String type);
    void postComment(String comment);
}
