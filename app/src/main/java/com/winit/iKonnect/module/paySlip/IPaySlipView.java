package com.winit.iKonnect.module.paySlip;

import com.winit.iKonnect.dataobject.response.PaySlipResponseDo;
import com.winit.iKonnect.module.base.IBaseView;

/**
 * Created by Rahul.Yadav on 6/20/2017.
 */

public interface IPaySlipView  extends IBaseView{
    void onDataFetch(PaySlipResponseDo paySlipResponseDo);
    void Error();

}
