package com.winit.iKonnect.module.Settings;

import com.winit.common.Preference;
import com.winit.iKonnect.module.Settings.interactors.ISettingInteractor;
import com.winit.iKonnect.module.Settings.interactors.SettingsInteractor;

/**
 * Created by Ashoka.Reddy on 5/25/2017.
 */

public class SettingsDetailPresenter implements ISettingsDetailPresenter
{
    private ISettingsDetailView view;
    private Preference preference;
    private ISettingInteractor iSettingInteractor;

    public SettingsDetailPresenter(ISettingsDetailView view)
    {
        this.view=view;
        preference=Preference.getInstance();
        iSettingInteractor = new SettingsInteractor(this);
    }

    @Override
    public void UpdateNotitification(String Modified_Language)
    {
        iSettingInteractor.SetLanguage(Modified_Language);
    }

    @Override
    public void OnresponceReceive(Object data)
    {
        view.OnResponceReceive(data);
    }
}
