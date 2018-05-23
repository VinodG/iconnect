package com.winit.iKonnect.module.ContactUs;

import android.text.TextUtils;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.dataobject.ContactListDO;
import com.winit.iKonnect.dataobject.ContactUSDo;
import com.winit.iKonnect.dataobject.ContactsListDOArrayList;
import com.winit.iKonnect.dataobject.ServiceDO;
import com.winit.iKonnect.dataobject.response.FormResponse;
import com.winit.iKonnect.module.ContactUs.interactor.AsyncContactUsInteractor;
import com.winit.iKonnect.module.ContactUs.interactor.IAsyncContactusInteractor;
import com.winit.iKonnect.module.form.IFormView;

/**
 * Created by Ashoka.Reddy on 5/26/2017.
 */

public class ContactUsPresenter implements IContactusPresenter, AsyncContactUsInteractor.OnLoginFinishedListener {

    private ServiceDO serviceDO;
    private IContactUsView view;
    private IAsyncContactusInteractor interactor;
    private boolean isArabic;
    private String ID = "";


    public ContactUsPresenter(IContactUsView iContactUsView) {
        this.view = iContactUsView;
        this.interactor = new AsyncContactUsInteractor(this);
        isArabic = Preference.getInstance().getStringFromPreference(Preference.LANGUAGE, "").equalsIgnoreCase(AppConstants.ARABIC);
        ID = Preference.getInstance().getStringFromPreference(Preference.STAFF_NUMBER, "");
    }


   /* @Override
    public void submitForm(String subject, String Msg, String to, String from) {

        if (subject == null || TextUtils.isEmpty("" + subject)) {
            view.showAlert(IFormView.ENTER_SUBJECT);
        } else if (Msg == null || TextUtils.isEmpty(Msg)) {
            view.showAlert(IFormView.ENTER_MESSEGE);
        } else {
            ContactUSDo contactUSDo = new ContactUSDo();
            contactUSDo.setSubject(subject);
            contactUSDo.setContent(Msg);
            contactUSDo.setTo("");
            contactUSDo.setFrom(ID);
            interactor.postData(contactUSDo);
        }


    }*/

    @Override
    public void submitForm(String body, String topic, String subTopic, String staffNo, boolean isSubTopicAvilable) {
        if (TextUtils.isEmpty(topic)) {
            view.showAlert(IFormView.EMPTY_TOPIC);
        }
        else if (isSubTopicAvilable && TextUtils.isEmpty(subTopic)) {
            view.showAlert(IFormView.EMPTY_SUB_TOPIC);
        }
        /*if (body == null || TextUtils.isEmpty(body)) {
            view.showAlert(IFormView.ENTER_MESSEGE);
        } */
        else {
            ContactUSDo contactUSDo = new ContactUSDo();
            contactUSDo.setBody(body);
            contactUSDo.setTopic(topic);
            contactUSDo.setSubTopic(subTopic);
            contactUSDo.setStaffNumber(staffNo);
            interactor.postData(contactUSDo);
        }
    }

    @Override
    public void fetchAllContactList() {

        interactor.postDataToGetContactList();
    }

    @Override
    public void onError(String Message) {
        view.showAlert(Message);
    }

    @Override
    public void onSuccess(Object object) {

        if (object instanceof ContactsListDOArrayList) {
            view.getData((ContactsListDOArrayList) object);
        } else {
            FormResponse formResponse = (FormResponse) object;
            view.showAlert(isArabic ? formResponse.getStatusMessageAr() : formResponse.getStatusMessageEn());
        }


    }
}
