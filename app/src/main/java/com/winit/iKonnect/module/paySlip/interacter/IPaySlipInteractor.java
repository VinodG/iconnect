package com.winit.iKonnect.module.paySlip.interacter;

import com.winit.iKonnect.dataobject.response.PaySlipResponseDo;
import com.winit.iKonnect.module.base.interactors.IBaseInteractor;

/**
 * Created by Rahul.Yadav on 6/20/2017.
 */

public interface IPaySlipInteractor extends IBaseInteractor{


    void fetchPaySlipData(String staff_Id);

    interface  OnPaySlipListener extends BaseListener
    {
        void onSuccess(PaySlipResponseDo paySlipResponseDo);
    }
}
