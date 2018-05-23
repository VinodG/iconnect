package com.winit.iKonnect.module.CalenderViewDetailModule.interactors;

import com.winit.iKonnect.module.base.interactors.IBaseInteractor;

/**
 *  on 9/25/2016.
 */

public interface IAsyncCalenderViewDetailInteractor extends IBaseInteractor {
    void postData(String StaffNumber, String fromDateTimeInUTC, String toDateTimeInUTC);

}
