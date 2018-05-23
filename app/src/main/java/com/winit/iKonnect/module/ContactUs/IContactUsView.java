package com.winit.iKonnect.module.ContactUs;

import com.winit.iKonnect.dataobject.ContactListDO;
import com.winit.iKonnect.dataobject.ContactsListDOArrayList;
import com.winit.iKonnect.module.form.IFormView;

/**
 * Created by Ashoka.Reddy on 5/26/2017.
 */

public interface IContactUsView extends IFormView {

    public void getData(ContactsListDOArrayList contactListDO);


}
