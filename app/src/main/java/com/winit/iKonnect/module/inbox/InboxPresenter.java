package com.winit.iKonnect.module.inbox;


import com.winit.common.Preference;
import com.winit.common.util.CalendarUtil;
import com.winit.iKonnect.dataobject.NotificationDO;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.inbox.interactors.IInboxInteractor;
import com.winit.iKonnect.module.inbox.interactors.InboxInteractor;

import java.util.Date;
import java.util.List;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public class InboxPresenter extends BasePresenter implements IInboxPresenter,IInboxInteractor.OnInboxDataListener {

    private IInboxView view;
    private InboxInteractor inboxInteractor;
    private List<NotificationDO> notificationDOs;

    public InboxPresenter(IInboxView view){
        super(view);
        this.view = view;
        inboxInteractor = new InboxInteractor(this);
    }

    @Override
    public List<NotificationDO> getNotificationDOs() {
        return notificationDOs;
    }

    @Override
    public void onError(final String message) {
        view.showAlert(message);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void getInboxData() {
        inboxInteractor.fetchInboxs(preference.getStringFromPreference(Preference.STAFF_NUMBER,"86262"),CalendarUtil.getDate(new Date(),CalendarUtil.YYYY_MM_DD_FULL_PATTERN));
    }

    @Override
    public void getInboxData(List<NotificationDO> inboxDOs) {
        if(inboxDOs != null && inboxDOs.size()>0)
            inboxInteractor.fetchInboxs(inboxDOs,preference.getStringFromPreference(Preference.STAFF_NUMBER,"86262"),
                    inboxDOs.get(inboxDOs.size()-1).getCreatedOn());
    }

    @Override
    public void onSuccess(final List<NotificationDO> notificationDOs) {
        this.notificationDOs = notificationDOs;
        handler.postResult(new Runnable() {
            @Override
            public void run() {
                if(notificationDOs != null && notificationDOs.size() >0)
                    view.onInboxMessages(notificationDOs);
                else
                    view.onLoadFailed();
            }
        });
    }

}
