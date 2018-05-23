package com.winit.iKonnect.module.CalenderViewDetailModule;


import com.winit.iKonnect.dataobject.response.CalenderListResponse;
import com.winit.iKonnect.module.CalenderViewDetailModule.interactors.AsyncCalenderViewDetailInteractor;
import com.winit.iKonnect.module.CalenderViewDetailModule.interactors.IAsyncCalenderViewDetailInteractor;
import com.winit.iKonnect.module.base.BasePresenter;

/**
 *  on 9/25/2016.
 */

public class CalenderViewDetailPresenter extends BasePresenter implements ICalenderViewDetailPresenter,AsyncCalenderViewDetailInteractor.OnLoginFinishedListener {

    private IAsyncCalenderViewDetailInteractor interactor;
    private ICalenderViewDetailView view;


    public CalenderViewDetailPresenter(ICalenderViewDetailView view){
        super(view);
        interactor = new AsyncCalenderViewDetailInteractor(this);
        this.view = view;
    }

    @Override
    public void onError(String Message) {
        view.showAlert(Message);
    }

    @Override
    public void onSuccess(final Object object) {
        handler.postResult(new Runnable() {
            @Override
            public void run() {
                CalenderListResponse loginResponse = (CalenderListResponse) object;
                view.LoadData(loginResponse);
            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void LoadData(String StaffNumber, String fromDateTimeInUTC, String toDateTimeInUTC) {

        interactor.postData(StaffNumber,fromDateTimeInUTC,toDateTimeInUTC);

    }
}
