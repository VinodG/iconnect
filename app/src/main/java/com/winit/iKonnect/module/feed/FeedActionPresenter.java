package com.winit.iKonnect.module.feed;


import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.FileUtils;
import com.winit.iKonnect.dataobject.CmspoststatModelDO;
import com.winit.iKonnect.dataobject.CmspostuserModelDO;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.dataobject.PostFeedActionDO;
import com.winit.iKonnect.dataobject.response.FeedsResponse;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.feed.interactors.IPostFeedActionInteractor;
import com.winit.iKonnect.module.feed.interactors.PostFeedActionInteractor;

import java.util.HashMap;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public class FeedActionPresenter extends BasePresenter implements IFeedActionPresenter,IPostFeedActionInteractor.OnPostFeedActionListener {

    private IFeedActionView iFeedActionView;
    private FeedsDO feedsDO;
    private String actionType;//SCIds
    private IPostFeedActionInteractor postFeedActionInteractor;

    public FeedActionPresenter(IFeedActionView iFeedActionView){
        super(iFeedActionView);
        this.iFeedActionView = iFeedActionView;
        postFeedActionInteractor = new PostFeedActionInteractor(this);
    }

    @Override
    public FeedsDO getFeedsDO() {
        return feedsDO;
    }

    @Override
    public void setFeedsDO(FeedsDO feedsDO) {
        this.feedsDO = feedsDO;
    }

    @Override
    public void onError(final String message) {
        iFeedActionView.showAlert(message);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onSuccess(CmspostuserModelDO cmspostuserModel, CmspoststatModelDO cmspoststatModel) {
        feedsDO.setCmspostuserModel(cmspostuserModel);
        feedsDO.setCmspoststatModel(cmspoststatModel);
        handler.postResult(new Runnable() {
            @Override
            public void run() {
                iFeedActionView.onActionSuccess(actionType,feedsDO);
            }
        });
        if(actionType == PostFeedActionDO.FAVORITE){
            HashMap<Integer, FeedsDO> hmFavFeedDO = (HashMap<Integer, FeedsDO>) IKonnectApplication.getCacheObject(AppConstants.FAVOURITE_TYPE);
            if(hmFavFeedDO == null) {
                hmFavFeedDO = new HashMap<>();
                IKonnectApplication.setCacheObject(AppConstants.FAVOURITE_TYPE,hmFavFeedDO);
            }
            if(feedsDO.getCmspostuserModel() != null && feedsDO.getCmspostuserModel().isHasFavorite())
                hmFavFeedDO.put(feedsDO.getId(),feedsDO);
            else
                hmFavFeedDO.remove(feedsDO.getId());
            FileUtils.saveObjAsFile(AppConstants.FAVOURITE_TYPE,hmFavFeedDO);
        }
    }

    @Override
    public void onSingleFeedSuccess(FeedsResponse feedresponseDo) {
        iFeedActionView.setSingleFeed(feedresponseDo);
    }

    @Override
    public void performActionFeed(String type, final FeedsDO feedsDO) {
        if(feedsDO != null) {
            this.feedsDO = feedsDO;
            this.actionType = type;
            if(type.equalsIgnoreCase(PostFeedActionDO.FAVORITE)){
                CmspostuserModelDO cmspostuserModel = feedsDO.getCmspostuserModel();
                if(cmspostuserModel == null) {
                    cmspostuserModel = new CmspostuserModelDO();
                    feedsDO.setCmspostuserModel(cmspostuserModel);
                }
                cmspostuserModel.setHasFavorite(!cmspostuserModel.isHasFavorite());
                handler.postResult(new Runnable() {
                    @Override
                    public void run() {
                        iFeedActionView.onActionSuccess(actionType,feedsDO);
                    }
                });
            }
            PostFeedActionDO postFeedActionDO = new PostFeedActionDO();
            postFeedActionDO.setStaffNumber(preference.getStringFromPreference(Preference.STAFF_NUMBER,"86262"));
            postFeedActionDO.setCmspostId(feedsDO.getId());
            postFeedActionDO.setActionType(type);
            postFeedActionInteractor.postFeedAction(postFeedActionDO);
        }
    }

    @Override
    public void getSingleFeed(String id) {
        postFeedActionInteractor.getSingleFeed(id);
    }

}
