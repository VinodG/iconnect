package com.winit.iKonnect.module.paySlip;

import com.winit.iKonnect.dataobject.response.PaySlipResponseDo;
import com.winit.iKonnect.module.base.IBasePresenter;

/**
 * Created by Rahul.Yadav on 6/20/2017.
 */

public interface IPaySlipPresenter extends IBasePresenter{

    void fetchPaySlip();
    void fetchPaySlipData(PaySlipResponseDo paySlipResponseDo);
}
