package com.winit.iKonnect.module.ContactUs.interactor;

import com.winit.iKonnect.dataobject.ContactUSDo;
import com.winit.iKonnect.module.base.interactors.IBaseInteractor;

/**
 *  on 9/25/2016.
 */

public interface IAsyncContactusInteractor extends IBaseInteractor {
    void postData(ContactUSDo contactUSDo);
    void postDataToGetContactList();
}
