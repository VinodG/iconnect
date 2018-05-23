package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.NotificationDO;

import java.util.List;

/**
 * Created by Gufran.Khan on 6/29/2017.
 */

public class InboxResponse extends BaseResponse {

    private List<NotificationDO> enoticeModels;

    public List<NotificationDO> getEnoticeModels() {
        return enoticeModels;
    }

    public void setEnoticeModels(List<NotificationDO> enoticeModels) {
        this.enoticeModels = enoticeModels;
    }

}
