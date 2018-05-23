package com.winit.iKonnect.module.feed.interactors;

import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.PostFeedActionDO;
import com.winit.iKonnect.dataobject.response.FeedsResponse;
import com.winit.iKonnect.dataobject.response.PostFeedActionResponse;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public class PostFeedActionInteractor extends AsyncBaseHttpInteractor implements IPostFeedActionInteractor {

    private OnPostFeedActionListener listener;

    public PostFeedActionInteractor(OnPostFeedActionListener listener){
        this.listener = listener;
    }

    @Override
    public void onResponseReceived(Response response) {
        if(response != null){
            if(response.data != null && response.data instanceof PostFeedActionResponse){
                PostFeedActionResponse postFeedActionResponse = (PostFeedActionResponse)response.data;
                if(postFeedActionResponse.getStatusCode() == 200){
                    listener.onSuccess(postFeedActionResponse.getCmspostuserModel(),postFeedActionResponse.getCmspoststatModel());
                    return;
                }else if(postFeedActionResponse.getStatusCode() == AppConstants.LOGOUT_ERROR_CODE)
                    listener.onError(AppConstants.Logout);
                else
                    listener.onError(postFeedActionResponse.getStatusMessageEn());
                return;
            }else if(response.data != null && response.data instanceof FeedsResponse)
            {
                FeedsResponse feedsResponse = (FeedsResponse)response.data;
                if(feedsResponse.getStatusCode() == 200){
                    listener.onSingleFeedSuccess(feedsResponse);
                    return;
                }
                else if(feedsResponse.getStatusCode() == AppConstants.LOGOUT_ERROR_CODE)
                    listener.onError(AppConstants.Logout);
                else
                listener.onError(feedsResponse.getStatusMessageEn());
                return;
            }
        }
        listener.onError("Unable to connect.");
    }

    @Override
    public void postFeedAction(PostFeedActionDO postFeedActionDO) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.POST_FEED_ACTION, BuildJsonRequest.getPostActionRequest(postFeedActionDO), this);
    }

    @Override
    public void getSingleFeed(String id) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.SINGLE_FEEDS, BuildJsonRequest.getSinglePost(id), this);
    }

}
