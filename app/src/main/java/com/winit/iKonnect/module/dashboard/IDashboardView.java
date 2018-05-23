package com.winit.iKonnect.module.dashboard;

import com.winit.iKonnect.dataobject.ServiceResponseData;
import com.winit.iKonnect.module.base.IBaseView;

import java.util.HashMap;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public interface IDashboardView extends IBaseView{
    void StoreThoughtoftheDay(String s,String s_arabic);
    void onServices();
    void gotFormData(HashMap<String, ServiceResponseData> hmFormDataDetail);
}
