package com.winit.iKonnect.module.Settings.interactors;

import com.winit.common.Preference;
import com.winit.common.webAccessLayer.BuildJsonRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.UpdateSettingNotificationDO;
import com.winit.iKonnect.module.Settings.SettingsDetailPresenter;
import com.winit.iKonnect.module.base.interactors.AsyncBaseHttpInteractor;

/**
 * Created by Ashoka.Reddy on 23-Sep-17.
 */

public class SettingsInteractor extends AsyncBaseHttpInteractor implements ISettingInteractor,HttpService.HttpListener
{
    public SettingsDetailPresenter settingsDetailPresenter;
    private Preference preference;
    public SettingsInteractor(SettingsDetailPresenter settingsDetailPresenter)
    {
        this.settingsDetailPresenter=settingsDetailPresenter;
        preference = Preference.getInstance();
    }
    @Override
    public void SetLanguage(String Modified_Language)
    {
        HttpService httpService = new HttpService();
        UpdateSettingNotificationDO updateSettingNotificationDO = new UpdateSettingNotificationDO();
        updateSettingNotificationDO.setDeviceId(preference.getStringFromPreference(Preference.GCM_ID,""));
        updateSettingNotificationDO.setDeviceType("Android");
        updateSettingNotificationDO.setFCMDeviceToken(preference.getStringFromPreference(Preference.GCM_ID,""));
        updateSettingNotificationDO.setIsNotificationEnabled(""+true);
        updateSettingNotificationDO.setPreferedLanguage(Modified_Language);
        updateSettingNotificationDO.setStaffNumber(preference.getStringFromPreference(Preference.STAFF_NUMBER,""));
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.POST_UPDATE_NOTIFICATION_SETTINGS, BuildJsonRequest.getNotificationUpdateService(updateSettingNotificationDO),this);
    }

    @Override
    public void onResponseReceived(Response response)
    {
        settingsDetailPresenter.OnresponceReceive(response.data);
    }
}
