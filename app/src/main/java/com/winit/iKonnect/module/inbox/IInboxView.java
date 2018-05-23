package com.winit.iKonnect.module.inbox;

import com.winit.iKonnect.dataobject.NotificationDO;
import com.winit.iKonnect.module.base.IBaseView;

import java.util.List;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public interface IInboxView extends IBaseView{
    String NO_NOTIFICATIONS = "NO_NOTIFICATIONS";
    void onInboxMessages(List<NotificationDO> inboxDOs);
}
