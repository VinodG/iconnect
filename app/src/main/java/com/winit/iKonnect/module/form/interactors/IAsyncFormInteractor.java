package com.winit.iKonnect.module.form.interactors;

import com.winit.iKonnect.dataobject.ServiceRequestDO;
import com.winit.iKonnect.dataobject.ServiceResponseData;
import com.winit.iKonnect.dataobject.request.ServiceRequest;
import com.winit.iKonnect.module.base.interactors.IBaseInteractor;

import java.util.HashMap;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public interface IAsyncFormInteractor extends IBaseInteractor{
    void postForm(ServiceRequest serviceRequest);
    void postAttachments(ServiceRequest serviceRequest);
    void postSignature(ServiceRequest serviceRequest);
    void getFormStatus();

    /**
     * Created by Girish Velivela on 5/11/15.
     */
    interface OnFormPostListener extends BaseListener{
        void onSuccess(String message, ServiceRequestDO serviceRequestDO);
        void onSuccess(String message);

        void gotFormData(HashMap<String, ServiceResponseData> hmFormDataDetail);
    }
}
