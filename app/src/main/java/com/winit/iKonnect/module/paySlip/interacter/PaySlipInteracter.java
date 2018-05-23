package com.winit.iKonnect.module.paySlip.interacter;

import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.response.PaySlipResponseDo;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 * Created by Rahul.Yadav on 6/20/2017.
 */

public class PaySlipInteracter extends AsyncBaseHttpInteractor implements IPaySlipInteractor {
    private OnPaySlipListener listener;

    public PaySlipInteracter(OnPaySlipListener onPaySlipListener) {
    this.listener = onPaySlipListener;
    }

    @Override
    public void fetchPaySlipData(String staff_Id) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_PAYSLIP_DETAIL, BuildJsonRequest.getPaySlipParams(staff_Id),this);
    }

    @Override
    public void onResponseReceived(Response response) {
        if(response!=null)
        {
            if(response.data!=null)
            {
                PaySlipResponseDo paySlipResponseDo = (PaySlipResponseDo) response.data;
                if(paySlipResponseDo.getStatusCode()==200)
                    listener.onSuccess(paySlipResponseDo);
                else
                    listener.onError(paySlipResponseDo.getStatusMessageEn());
            }
        }
        listener.onError("Unable to connect.");


    }
}
