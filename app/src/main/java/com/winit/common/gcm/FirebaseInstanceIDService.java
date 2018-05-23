package com.winit.common.gcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.winit.common.Preference;

/**
 * Created by home on 10/7/2017.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService
{
    private Preference preferences;
    private String gcmToken;

    @Override
    public void onTokenRefresh()
    {
        preferences = Preference.getInstance();
        gcmToken= FirebaseInstanceId.getInstance().getToken();
        preferences.saveStringInPreference(Preference.GCM_ID,gcmToken);
    }
}
