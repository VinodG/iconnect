package com.winit.iKonnect.module.comment;


import com.winit.common.Preference;
import com.winit.common.util.CalendarUtil;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.dataobject.CmspoststatModelDO;
import com.winit.iKonnect.dataobject.CmspostuserModelDO;
import com.winit.iKonnect.dataobject.FeedDetail;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.dataobject.PostFeedActionDO;
import com.winit.iKonnect.dataobject.response.FeedsResponse;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.comment.interactors.AsyncDetailInteractor;
import com.winit.iKonnect.module.comment.interactors.IAsyncDetailInteractor;
import com.winit.iKonnect.module.feed.interactors.IPostFeedActionInteractor;
import com.winit.iKonnect.module.feed.interactors.PostFeedActionInteractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public class FeedDetailPresenter extends BasePresenter implements IFeedDetailPresenter,IAsyncDetailInteractor.OnFeedDetailsListener,IPostFeedActionInteractor.OnPostFeedActionListener {

    private AsyncDetailInteractor interactor;
    private IFeedDetailView view;
    private FeedsDO feedsDO;
    private String type;
    private PostFeedActionDO postFeedActionDO;
    private List<FeedDetail> feedDetails;
    private IPostFeedActionInteractor postFeedActionInteractor;
    private boolean isCommentEnable;

    public FeedDetailPresenter(FeedsDO feedsDO, IFeedDetailView view){
        super(view);
        this.view = view;
        this.feedsDO = feedsDO;
        this.interactor = new AsyncDetailInteractor(this);
        postFeedActionInteractor = new PostFeedActionInteractor(this);
    }

    @Override
    public void setFeedsDO(FeedsDO feedsDO) {
        this.feedsDO = feedsDO;
    }

    @Override
    public void setCommentEnable(boolean commentEnable) {
        isCommentEnable = commentEnable;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void getDetails(String type) {
        if(feedsDO!=null && interactor!=null)
        interactor.fetchDetails(feedsDO.getId(),type);
    }

    @Override
    public void postComment(String comment) {
        postFeedActionDO = new PostFeedActionDO();
        postFeedActionDO.setStaffNumber(preference.getStringFromPreference(Preference.STAFF_NUMBER,"86262"));
        postFeedActionDO.setCmspostId(feedsDO.getId());
        postFeedActionDO.setComment(new String[]{comment});
        postFeedActionDO.setActionType(PostFeedActionDO.COMMENT);
        postFeedActionInteractor.postFeedAction(postFeedActionDO);
    }

    @Override
    public void onError(final String Message) {
        ((MVPHandler)handler).postResult(new Runnable() {
            @Override
            public void run() {
                view.showAlert(Message);
            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onSuccess(final List<FeedDetail> feedDetails) {
        this.feedDetails = feedDetails;
        if(feedDetails != null && feedDetails != null){
            Collections.sort(feedDetails, new Comparator<FeedDetail>() {
                @Override
                public int compare(FeedDetail feedDetail, FeedDetail feedDetailTwo) {
                    if(type.equalsIgnoreCase(PostFeedActionDO.COMMENT)) {
                        if(isCommentEnable)
                            return (int) CalendarUtil.getDifferenceTimezone(feedDetailTwo.getCommentTime(), feedDetail.getCommentTime(), CalendarUtil.SEC_DATE_PATTERN, "UTC");
                        else
                            return (int) CalendarUtil.getDifferenceTimezone(feedDetail.getCommentTime(), feedDetailTwo.getCommentTime(), CalendarUtil.SEC_DATE_PATTERN, "UTC");
                    }else if(type.equalsIgnoreCase(PostFeedActionDO.LIKE))
                        return (int) CalendarUtil.getDifferenceTimezone(feedDetail.getLikeTime(),feedDetailTwo.getLikeTime(),CalendarUtil.SEC_DATE_PATTERN,"UTC");
                    else
                        return 0;
                }
            });
        }
        handler.postResult(new Runnable() {
            @Override
            public void run() {
                if(feedDetails != null && feedDetails.size() >0)
                    view.onDetails(feedDetails,null);
                else
                    view.noComments();
            }
        });
    }

    @Override
    public void onSuccess(CmspostuserModelDO cmspostuserModel, CmspoststatModelDO cmspoststatModel) {
        FeedDetail feedDetail = new FeedDetail();
        feedDetail.setStaffNumber(StringUtils.getLong(postFeedActionDO.getStaffNumber()));
        feedDetail.setStaffName(preference.getStringFromPreference(Preference.STAFF_NAME,""));
        feedDetail.setPhotoUrl(preference.getStringFromPreference(Preference.STAFF_PHOTO_URL,""));
//        feedDetail.setPhotoUrl();
        feedDetail.setComment(postFeedActionDO.getComment()[0]);
        feedDetail.setCommentTime(CalendarUtil.getDate(new Date(),CalendarUtil.SEC_DATE_PATTERN, Locale.ENGLISH,"UTC"));
        if(feedDetails == null)
            feedDetails = new ArrayList<>();
       /* if(feedDetails.size() >0)
            feedDetails.add(0,feedDetail);
        else*/
            feedDetails.add(feedDetail);
        feedsDO.setCmspostuserModel(cmspostuserModel);
        feedsDO.setCmspoststatModel(cmspoststatModel);
        ((MVPHandler)handler).postResult(new Runnable() {
            @Override
            public void run() {
                view.onDetails(feedDetails,feedsDO);
            }
        });
    }

    @Override
    public void onSingleFeedSuccess(FeedsResponse feedresponseDo) {

    }
}
