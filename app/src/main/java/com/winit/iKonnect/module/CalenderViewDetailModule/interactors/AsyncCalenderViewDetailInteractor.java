package com.winit.iKonnect.module.CalenderViewDetailModule.interactors;


import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.response.CalenderListResponse;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 * on 9/25/2016.
 */

public class AsyncCalenderViewDetailInteractor extends AsyncBaseHttpInteractor implements IAsyncCalenderViewDetailInteractor {

    private OnLoginFinishedListener onLoginFinishedListener;
    public AsyncCalenderViewDetailInteractor(OnLoginFinishedListener onLoginFinishedListener){
    this.onLoginFinishedListener=onLoginFinishedListener;
    }


    @Override
    public void postData(String StaffNumber, String fromDateTimeInUTC, String toDateTimeInUTC) {

        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.Load_CALENDER_DATA, BuildJsonRequest.getCalenderDetail(StaffNumber,fromDateTimeInUTC,toDateTimeInUTC), this);
    }


    @Override
    public void onResponseReceived(Response response) {

        if(response != null){
            if(response.data != null){
                CalenderListResponse calenderDetailResponse = (CalenderListResponse) response.data;
                if(calenderDetailResponse.getStatusCode() == 200){
                    this.onLoginFinishedListener.onSuccess(calenderDetailResponse);
                }else
                    this.onLoginFinishedListener.onError(isArabic? calenderDetailResponse.getStatusMessageAr() :calenderDetailResponse.getStatusMessageEn());

                return;
            }
        }
        this.onLoginFinishedListener.onError("Unable to connect.");
    }
    /**
     * Created by Girish Velivela on 5/11/15.
     */
    public static interface OnLoginFinishedListener {
        void onError(String Message);
        void onSuccess(Object object);
    }
}
