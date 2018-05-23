package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.NotificationDetailsDO;

/**
 * Created by Gufran.Khan on 6/1/2017.
 */

public class NotificationDetailResponse extends BaseResponse {

    private NotificationDetailsDO NotificationDetailModel;

    public NotificationDetailsDO getNotificationDetailModel() {
        return NotificationDetailModel;
    }

    public void setNotificationDetailModel(NotificationDetailsDO notificationDetailModel) {
        NotificationDetailModel = notificationDetailModel;
    }
}
