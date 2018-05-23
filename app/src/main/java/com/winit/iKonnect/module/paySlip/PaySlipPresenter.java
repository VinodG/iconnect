package com.winit.iKonnect.module.paySlip;

import com.winit.common.Preference;
import com.winit.iKonnect.dataobject.response.PaySlipResponseDo;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.paySlip.interacter.IPaySlipInteractor;
import com.winit.iKonnect.module.paySlip.interacter.PaySlipInteracter;

/**
 * Created by Rahul.Yadav on 6/20/2017.
 */

public class PaySlipPresenter extends BasePresenter implements IPaySlipPresenter,IPaySlipInteractor.OnPaySlipListener {

    private IPaySlipView view;
    private IPaySlipInteractor iPaySlipInteractor;

    public PaySlipPresenter(IPaySlipView iPaySlipView)
    {
        super(iPaySlipView);
        this.view = iPaySlipView;
        this.iPaySlipInteractor = new PaySlipInteracter(this);

    }

    @Override
    public void loadData() {

    }

    @Override
    public void fetchPaySlip() {
        iPaySlipInteractor.fetchPaySlipData(preference.getStringFromPreference(Preference.STAFF_NUMBER,""));

    }

    @Override
    public void fetchPaySlipData(PaySlipResponseDo paySlipResponseDo) {

    }

    @Override
    public void onError(String Message) {
       /* handler.postResult(new Runnable() {
            @Override
            public void run() {
                view.onDataFetch(new PaySlipResponseDo());
            }
        });*/
        handler.postResult(new Runnable() {
            @Override
            public void run() {
                view.Error();
            }
        });


    }

    @Override
    public void onSuccess(final PaySlipResponseDo paySlipResponseDo) {
        handler.postResult(new Runnable() {
            @Override
            public void run() {
                view.onDataFetch(paySlipResponseDo);
            }
        });
    }
}
