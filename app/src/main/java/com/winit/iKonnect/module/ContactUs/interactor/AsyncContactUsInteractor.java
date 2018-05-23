package com.winit.iKonnect.module.ContactUs.interactor;


import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.ContactUSDo;
import com.winit.iKonnect.dataobject.ContactsListDOArrayList;
import com.winit.iKonnect.dataobject.response.FormResponse;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 * on 9/25/2016.
 */

public class AsyncContactUsInteractor extends AsyncBaseHttpInteractor implements IAsyncContactusInteractor {

    private OnLoginFinishedListener onLoginFinishedListener;

    public AsyncContactUsInteractor(OnLoginFinishedListener onLoginFinishedListener) {
        this.onLoginFinishedListener = onLoginFinishedListener;
    }

    @Override
    public void onResponseReceived(Response response) {

        if (response != null) {
            if (response.data != null) {
                this.onLoginFinishedListener = onLoginFinishedListener;

                if (response.data instanceof ContactsListDOArrayList) {

                    ContactsListDOArrayList contactsListDOArrayList = (ContactsListDOArrayList) response.data;

                    if (contactsListDOArrayList.getStatusCode() == 200) {
                        onLoginFinishedListener.onSuccess(contactsListDOArrayList);
                    } else {
                        onLoginFinishedListener.onError(contactsListDOArrayList.getStatusMessageEn());
                    }


                } else {
                    FormResponse formResponse = (FormResponse) response.data;
                    if (formResponse.getStatusCode() == 200) {
//                    onLoginFinishedListener.onSuccess(formResponse.getStatusMessageEn(), formResponse.getServicerequestModel());
                        onLoginFinishedListener.onSuccess(formResponse);
                    }
                    if (formResponse.getStatusCode() == AppConstants.LOGOUT_ERROR_CODE) {
                        onLoginFinishedListener.onError(AppConstants.Logout);
                    } else
                        onLoginFinishedListener.onError(formResponse.getStatusMessageEn());
                }


                return;
            }
        }
        onLoginFinishedListener.onError("Unable to connect.");

    }

    @Override
    public void postData(ContactUSDo contactUSDo) {

        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.POST_CONTACT_US, BuildJsonRequest.getPostFormRequest(contactUSDo), this);
    }

    @Override
    public void postDataToGetContactList() {
        HttpService httpService = new HttpService();
        httpService.executeTask(ServiceUrls.ServiceAction.GET_CONTACT_US_LIST, BuildJsonRequest.getFormStatus(), this);
    }


    /**
     * Created by Girish Velivela on 5/11/15.
     */
    public static interface OnLoginFinishedListener {
        void onError(String Message);

        void onSuccess(Object object);
    }
}
