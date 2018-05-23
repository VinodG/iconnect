package com.winit.iKonnect.module.chat.interactors;

import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.ChatMemberResponce;
import com.winit.iKonnect.dataobject.response.ChatGroupResponse;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public class AsyncChatInteractor extends AsyncBaseHttpInteractor implements IAsyncChatInteractor {

    private OnChatGroupListener listener;

    public AsyncChatInteractor(OnChatGroupListener listener){
        this.listener = listener;
    }

    @Override
    public void fetchChatGroups(String staffNumber) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_CHAT_GROUP, BuildJsonRequest.getChatGroupParams(staffNumber), this);
    }

    @Override
    public void fetchStaffDetails(String staffNumebr)
    {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_EMPLOYEE_NAMES, BuildJsonRequest.getStaffDetail(staffNumebr), this);
    }

    @Override
    public void onResponseReceived(Response response) {
        if(response != null)
        {
            if (response.data != null)
            {
                if(response.data instanceof ChatGroupResponse)
                {
                    ChatGroupResponse chatGroupResponse = (ChatGroupResponse) response.data;
                    if (chatGroupResponse.getStatusCode() == 200)
                    {
                        listener.onSuccess(chatGroupResponse.getChatgroupModels());
                    }else if(chatGroupResponse.getStatusCode() == AppConstants.LOGOUT_ERROR_CODE)
                    {
                        listener.onError(AppConstants.Logout);
                    }
                    else
                        listener.onError(chatGroupResponse.getStatusMessageEn());
                    return;
                }
                else if(response.data instanceof ChatMemberResponce)
                {
                    ChatMemberResponce chatMemberResponce = (ChatMemberResponce) response.data;
                    if(chatMemberResponce.getStatusCode()==200)
                    {
                        listener.onLoadChatMember(chatMemberResponce.getChatMemberModels());
                    }
                    else if(chatMemberResponce.getStatusCode() == AppConstants.LOGOUT_ERROR_CODE)
                    {
                        listener.onError(AppConstants.Logout);
                    }
                    else
                        listener.onError(chatMemberResponce.getStatusMessageEn());
                    return;
                }
            }
        }
        listener.onError("Unable to connect.");
    }
}
