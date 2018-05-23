package com.winit.iKonnect.module.dashboard.interactors;

import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.CategoryFilterDO;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.dataobject.PostFeedDO;
import com.winit.iKonnect.dataobject.ServiceResponseData;
import com.winit.iKonnect.dataobject.response.FeedsResponse;
import com.winit.iKonnect.dataobject.response.FormResponseDetail;
import com.winit.iKonnect.dataobject.response.ThoughtOfTheDayResponse;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

import java.util.HashMap;
import java.util.List;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public class FeedsInteractor extends AsyncBaseHttpInteractor implements IFeedsInteractor {

    private OnFeedsListener listener;
    private HashMap<String, ServiceResponseData> hmFormDataDetail;

    public FeedsInteractor(OnFeedsListener listener) {
        this.listener = listener;
    }

    @Override
    public void fetchFeeds(String UserCode, CategoryFilterDO categoryFilterDO, int offset, int favourite) {
        HttpService httpService = new HttpService();
        PostFeedDO postFeedDO = new PostFeedDO();
        postFeedDO.setStaffNumber(UserCode);
        postFeedDO.setFilters(categoryFilterDO);
        postFeedDO.setOffset(offset);
        postFeedDO.setFavourite(favourite);
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.FEEDS, BuildJsonRequest.getPostFeedRequest(postFeedDO), this);
//        httpService.executeAsyncTask(ServiceUrls.ServiceAction.SINGLE_FEEDS, BuildJsonRequest.getSinglePost(""), this);
//        httpService.executeAsyncTask(ServiceUrls.ServiceAction.FEEDS, BuildJsonRequest.getFeedsParams(UserCode,"",dateTime,favourite), this);
    }

    @Override
    public void fetchFeeds(final List<FeedsDO> feedsDOs, final String UserCode, final CategoryFilterDO categoryFilterDO, final int offset, final int favourite) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpService httpService = new HttpService();
                PostFeedDO postFeedDO = new PostFeedDO();
                postFeedDO.setStaffNumber(UserCode);
                postFeedDO.setFilters(categoryFilterDO);
                postFeedDO.setOffset(offset);
                postFeedDO.setFavourite(favourite);
                Response response = httpService.executeTask(ServiceUrls.ServiceAction.FEEDS, BuildJsonRequest.getPostFeedRequest(postFeedDO));
//                Response response = httpService.executeTask(ServiceUrls.ServiceAction.FEEDS, BuildJsonRequest.getFeedsParams(UserCode,"",dateTime,favourite));
                if (response != null) {
                    if (response.data != null) {
                        FeedsResponse feedsResponse = (FeedsResponse) response.data;
                        if (feedsResponse.getStatusCode() == 200) {
                            if (feedsResponse.getCmspostModels() != null) {
                                feedsDOs.addAll(feedsResponse.getCmspostModels());
                                listener.onSuccess(feedsDOs);
                            }
                        }
                        return;
                    }
                }
            }
        }).start();
    }

    @Override
    public void getThoughtOfTheDay(String Staffno) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_THOUGHT_OF_THE_DAY, BuildJsonRequest.getStaffno(Staffno), this);
    }

    @Override
    public void getFormStatus() {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_FORM_STATUS, BuildJsonRequest.getFormStatus(), this);
    }


    @Override
    public void onResponseReceived(Response response) {
        if (response != null) {
            if (response.data != null) {
                if (response.data instanceof FeedsResponse) {
                    FeedsResponse feedsResponse = (FeedsResponse) response.data;
                    if (feedsResponse.getStatusCode() == 200) {
                        listener.onSuccess(feedsResponse.getCmspostModels());
                    }else if(feedsResponse.getStatusCode() == AppConstants.LOGOUT_ERROR_CODE)
                    {
                        listener.onError(AppConstants.Logout);
                    }
                    else
                        listener.onError(feedsResponse.getStatusMessageEn());
                    return;
                } else if (response.data instanceof ThoughtOfTheDayResponse) {
                    ThoughtOfTheDayResponse feedsResponse = (ThoughtOfTheDayResponse) response.data;
                    if (feedsResponse.getStatusCode() == 200) {
                        if (feedsResponse != null && feedsResponse.getQuoteModel() != null)
                            listener.gotMessege(feedsResponse.getQuoteModel().getQuote(), feedsResponse.getQuoteModel().getQuoteAr());
                    }else if(feedsResponse.getStatusCode() == AppConstants.LOGOUT_ERROR_CODE)
                    {
                        listener.onError(AppConstants.Logout);
                    } else
                        listener.onError(feedsResponse.getStatusEn());
                    return;
                } else if (response.data instanceof FormResponseDetail) {
                    FormResponseDetail feedsResponse = (FormResponseDetail) response.data;
                    if (feedsResponse.getStatusCode() == 200) {
                        if (feedsResponse != null && feedsResponse.getServicelistModels() != null && feedsResponse.getServicelistModels().size() > 0)
                            hmFormDataDetail= new HashMap<>();
                            for (ServiceResponseData servicedo : feedsResponse.getServicelistModels()) {
                                hmFormDataDetail.put(servicedo.getFormName(), servicedo);
                            }
                        listener.gotFormData(hmFormDataDetail);
                    }else if(feedsResponse.getStatusCode() == AppConstants.LOGOUT_ERROR_CODE)
                    {
                        listener.onError(AppConstants.Logout);
                    } else
                        listener.onError("1234");
                    return;
                }
            }
            listener.onError("Unable to connect.");
        }
    }
}

