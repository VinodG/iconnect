package com.winit.iKonnect.module.ContactUs;

/**
 * Created by Ashoka.Reddy on 5/26/2017.
 */

public interface IContactusPresenter {
    //  void submitForm(String Subject, String Msg, String to, String from);
    void submitForm(String body, String topic, String subTopic, String staffNo, boolean isSubTopicAvilable);

    void fetchAllContactList();
}
