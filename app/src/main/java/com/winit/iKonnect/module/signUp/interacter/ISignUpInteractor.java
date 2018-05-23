package com.winit.iKonnect.module.signUp.interacter;

import com.winit.iKonnect.dataobject.request.ServiceRequest;
import com.winit.iKonnect.module.base.interactors.IBaseInteractor;

/**
 * Created by Rahul.Yadav on 5/28/2017.
 */

public interface ISignUpInteractor extends IBaseInteractor{
    void postData(String StaffNumber, String mobile, String workCountry);
    void postAttachments(ServiceRequest serviceRequest);

}
