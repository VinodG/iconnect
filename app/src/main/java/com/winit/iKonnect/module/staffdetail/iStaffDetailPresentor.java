package com.winit.iKonnect.module.staffdetail;

import com.winit.iKonnect.module.base.IBasePresenter;

/**
 * Created by Ashoka.Reddy on 6/16/2017.
 */

public interface iStaffDetailPresentor extends IBasePresenter {
    void postProfilePicture(String path, int id);
    void onProfilSuccess();
    void onError(String s);
}
