package com.winit.iKonnect.module.comment.interactors;

import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.PostFeedActionDO;
import com.winit.iKonnect.dataobject.response.CommentsResponse;
import com.winit.iKonnect.dataobject.response.LikesResponse;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public class AsyncDetailInteractor extends AsyncBaseHttpInteractor implements IAsyncDetailInteractor {

    private OnFeedDetailsListener listener;

    public AsyncDetailInteractor(OnFeedDetailsListener listener){
        this.listener = listener;
    }

    @Override
    public void fetchDetails(int postId,String type) {
        HttpService httpService = new HttpService();
        ServiceUrls.ServiceAction serviceAction = null;
        if(type.equalsIgnoreCase(PostFeedActionDO.COMMENT))
            serviceAction = ServiceUrls.ServiceAction.GET_COMMENTS;
        else if(type.equalsIgnoreCase(PostFeedActionDO.LIKE))
            serviceAction = ServiceUrls.ServiceAction.GET_LIKES;
        else
            serviceAction = ServiceUrls.ServiceAction.GET_COMMENTS;
        httpService.executeAsyncTask(serviceAction, BuildJsonRequest.getPostParams(postId), this);
    }

    @Override
    public void onResponseReceived(Response response) {
        if(response != null){
            if(response.method == ServiceUrls.ServiceAction.GET_COMMENTS) {
                if (response.data != null) {
                    CommentsResponse feedsResponse = (CommentsResponse) response.data;
                    if (feedsResponse.getStatusCode() == 200) {
                        listener.onSuccess(feedsResponse.getPostcommentModels());
                    }else if (feedsResponse.getStatusCode() == AppConstants.LOGOUT_ERROR_CODE) {
                        listener.onError(AppConstants.Logout);
                    }
                    else
                        listener.onError(feedsResponse.getStatusMessageEn());
                    return;
                }
            }else if(response.method == ServiceUrls.ServiceAction.GET_LIKES){
                if(response.data != null){
                    LikesResponse feedsResponse = (LikesResponse) response.data;
                    if(feedsResponse.getStatusCode() == 200){
                        listener.onSuccess(feedsResponse.getPostlikeModels());
                    }
                    else if (feedsResponse.getStatusCode() == AppConstants.LOGOUT_ERROR_CODE) {
                        listener.onError(AppConstants.Logout);
                    }else
                        listener.onError(feedsResponse.getStatusMessageEn());
                    return;
                }
            }
        }
        listener.onError("Unable to connect.");
    }
}
