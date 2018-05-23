package com.winit.iKonnect.module.inbox;

import com.winit.iKonnect.dataobject.NotificationDO;
import com.winit.iKonnect.module.base.IBasePresenter;

import java.util.List;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public interface IInboxPresenter extends IBasePresenter{
    void getInboxData();
    void getInboxData(List<NotificationDO> inboxDOs);
    List<NotificationDO> getNotificationDOs();
}
