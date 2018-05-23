package com.winit.iKonnect.module.CalenderViewDetailModule;


import com.winit.iKonnect.dataobject.response.CalenderListResponse;
import com.winit.iKonnect.module.base.IBaseView;

/**
 *  on 9/25/2016.
 */

public interface ICalenderViewDetailView extends IBaseView {
    void LoadData(CalenderListResponse cal);

}
