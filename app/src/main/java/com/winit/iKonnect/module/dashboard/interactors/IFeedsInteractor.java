package com.winit.iKonnect.module.dashboard.interactors;

import com.winit.iKonnect.dataobject.CategoryFilterDO;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.dataobject.ServiceResponseData;
import com.winit.iKonnect.module.base.interactors.IBaseInteractor;

import java.util.HashMap;
import java.util.List;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public interface IFeedsInteractor extends IBaseInteractor{
    void fetchFeeds(String UserCode, CategoryFilterDO categoryFilterDO, int offset, int favourite);
    void fetchFeeds(List<FeedsDO> feedsDOs,String UserCode, CategoryFilterDO categoryFilterDO, int offset, int favourite);
    void getThoughtOfTheDay(String Staffno);
    void getFormStatus();

    /**
     * Created by Girish Velivela on 5/11/15.
     */
    interface OnFeedsListener extends BaseListener{
        void onSuccess(List<FeedsDO> feeds);
        void gotMessege(String s,String s_arabic);
        void gotFormData(HashMap<String, ServiceResponseData> hmFormDataDetail);

    }
}
