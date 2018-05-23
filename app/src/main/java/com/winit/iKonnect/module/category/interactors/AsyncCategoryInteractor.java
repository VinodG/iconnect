package com.winit.iKonnect.module.category.interactors;

import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.response.CategoryListResponse;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public class AsyncCategoryInteractor extends AsyncBaseHttpInteractor implements IAsyncCategoryInteractor {

    private OnCategoriesListener listener;

    public AsyncCategoryInteractor(OnCategoriesListener listener){
        this.listener = listener;
    }

    @Override
    public void fetchCategories() {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_CATEGORIES, "", this);

    }

    @Override
    public void onResponseReceived(Response response) {
        if(response != null){
            if(response.data != null){
                CategoryListResponse categoryListResponse = (CategoryListResponse) response.data;
                if(categoryListResponse.getStatusCode() == 200){
                    if(categoryListResponse.getCategoriesModels() != null){
                        listener.onSuccess(categoryListResponse.getCategoriesModels());
                    }else
                        listener.onError("No Feeds Found.");
                }else if(categoryListResponse.getStatusCode() == AppConstants.LOGOUT_ERROR_CODE)
                {
                    listener.onError(AppConstants.Logout);
                }
                else
                    listener.onError(categoryListResponse.getStatusMessageEn());
                return;
            }
        }
        listener.onError("Unable to connect.");
    }
}
