package com.winit.iKonnect.module.inbox.interactors;

import com.winit.iKonnect.dataobject.NotificationDO;
import com.winit.iKonnect.module.base.interactors.IBaseInteractor;

import java.util.List;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public interface IInboxInteractor extends IBaseInteractor{
    void fetchInboxs(String UserCode, String dateTime);
    void fetchInboxs(List<NotificationDO> notificationDOs, String UserCode, String dateTime);

    /**
     * Created by Girish Velivela on 5/11/15.
     */
    interface OnInboxDataListener extends BaseListener{
        void onSuccess(List<NotificationDO> notificationDOs);
    }
}
