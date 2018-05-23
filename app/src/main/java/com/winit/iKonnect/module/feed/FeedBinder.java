package com.winit.iKonnect.module.feed;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.widget.Toast;

import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.util.CalendarUtil;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.CmspostcontentModelDO;
import com.winit.iKonnect.dataobject.CmspostcontentfileModels;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.dataobject.PostFeedActionDO;
import com.winit.iKonnect.module.base.IBasePresenter;
import com.winit.iKonnect.module.category.ICategoryPresenter;
import com.winit.iKonnect.ui.activities.BaseActivity;
import com.winit.iKonnect.ui.activities.DetailsActivity;
import com.winit.iKonnect.ui.activities.FeedActivity;
import com.winit.iKonnect.ui.dialog.FullImageDialog;
import com.winit.iKonnect.ui.fragments.AttachmentBottomSheetFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Girish Velivela on 5/17/2017.
 */

public class FeedBinder {
    private Context context;
    private IBasePresenter iBasePresenter;
    private ICategoryPresenter iCategoryPresenter;
    private boolean canOpen = true;
    public FeedsDO feedsDO;

    private void setFeedsDO(FeedsDO feedsDO) {
        if (iBasePresenter != null) {
            if (iBasePresenter instanceof IFeedActionPresenter)
                ((IFeedActionPresenter) iBasePresenter).setFeedsDO(feedsDO);
            else
                this.feedsDO = feedsDO;
        }
    }

    public FeedBinder(Context context){
        this.context = context;
    }

    public void setiBasePresenter(IBasePresenter iBasePresenter) {
        this.iBasePresenter = iBasePresenter;
    }

    public void setiCategoryPresenter(ICategoryPresenter iCategoryPresenter) {
        this.iCategoryPresenter = iCategoryPresenter;
    }

    public void setCanOpen(boolean canOpen) {
        this.canOpen = canOpen;
    }

    public void openFeed(FeedsDO feedsDO){
        if(canOpen) {
            setFeedsDO(feedsDO);
            Intent intent = new Intent(context, FeedActivity.class);
            intent.putExtra(ConstantExtraKey.FEED_OBJECT, feedsDO);
            intent.putExtra(ConstantExtraKey.IS_COMMENT_ENABLE, false);
            intent.putExtra(ConstantExtraKey.CATEGORY_NAME, iCategoryPresenter.getCategoryName(feedsDO.getCategoryId()));
            context.startActivity(intent);
        }
    }

    public void postAction(String action, FeedsDO feedsDO){
        if(((BaseActivity)context).checkNetworkConnection()) {
            if (iBasePresenter != null) {
                 if (iBasePresenter instanceof IFeedActionPresenter)
                    ((IFeedActionPresenter) iBasePresenter).performActionFeed(action, feedsDO);
            }
        }
    }

    public void share(FeedsDO feedsDO){
        if(((BaseActivity)context).checkNetworkConnection()) {
            setFeedsDO(feedsDO);
            if (iBasePresenter instanceof IFeedActionPresenter)
                ((IFeedActionPresenter) iBasePresenter).performActionFeed(PostFeedActionDO.SHARE,feedsDO);
            Intent shareIntent = new Intent();
            shareIntent.setType(/*StringUtils.getMimeType(ServiceUrls.FEEDS_DATA + feedsDO.getCoverPictureEnUrl())*/"text/plain");
            shareIntent.setAction(Intent.ACTION_SEND);
            String message = "";
            message += feedsDO.getTitleEn();
            message += "\n" + CalendarUtil.getDate(feedsDO.getCreatedOn(),CalendarUtil.SEC_DATE_PATTERN,CalendarUtil.dd_MM_yyyy_EEEE_PATTERN, Locale.getDefault(),"");
            message += "\n" +feedsDO.getSubtitleEn();
            if(feedsDO.getCmspostcontentModel() != null) {
                if(feedsDO.getCmspostcontentModel().getType().equalsIgnoreCase(CmspostcontentModelDO.URL)) {
                    message += "\n" + feedsDO.getCmspostcontentModel().getUrl();
                    message += "\n" + ServiceUrls.FEEDS_DATA + feedsDO.getCoverPictureEnUrl();
                }else if(feedsDO.getCmspostcontentModel().getType().equalsIgnoreCase(CmspostcontentModelDO.CONTENT)) {
                    message += "\n" + ServiceUrls.FEEDS_DATA + feedsDO.getCoverPictureEnUrl();
                    message += "\n" + feedsDO.getCmspostcontentModel().getContentEn();
                }else{
                    message += "\n" + ServiceUrls.FEEDS_DATA + feedsDO.getCoverPictureEnUrl();
                    if(feedsDO.getCmspostcontentModel().getCmspostcontentfileModels() != null){
                        List<CmspostcontentfileModels> cmspostcontentfileModelses = feedsDO.getCmspostcontentModel().getCmspostcontentfileModels();
                        for(CmspostcontentfileModels cmspostcontentfileModels : cmspostcontentfileModelses){
                            message += "\n" + ServiceUrls.FEEDS_DATA + cmspostcontentfileModels.getFileUrl();
                        }
                    }
                }
            }


            shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
//                shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,Uri.(ServiceUrls.FEEDS_DATA + feedsDO.getCoverPictureEnUrl()));
                context.startActivity(Intent.createChooser(shareIntent, "Select app to share"));
            } catch (android.content.ActivityNotFoundException ex) {
                if (iBasePresenter != null) {
                    if (iBasePresenter instanceof IFeedActionPresenter)
                        ((IFeedActionPresenter) iBasePresenter).setFeedsDO(null);
                    else
                        this.feedsDO = null;
                }
                Toast.makeText(context,
                        "No Apps found to share.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void viewFeedImage(FeedsDO feedsDO){
        if(canOpen) {
            openFeed(feedsDO);
        }else{
            FullImageDialog fullImageDialog = new FullImageDialog(context, ServiceUrls.FEEDS_DATA+feedsDO.getCoverPictureEnUrl());
            fullImageDialog.show();
        }
    }

    public void viewFiles(FeedsDO feedsDO){
        if(feedsDO.getCmspostcontentModel().getCmspostcontentfileModels() != null && feedsDO.getCmspostcontentModel().getCmspostcontentfileModels().size() >0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ConstantExtraKey.CONTENT_FILE, (ArrayList<CmspostcontentfileModels>) feedsDO.getCmspostcontentModel().getCmspostcontentfileModels());
            BottomSheetDialogFragment bottomSheetDialogFragment = new AttachmentBottomSheetFragment();
            bottomSheetDialogFragment.setArguments(bundle);
            bottomSheetDialogFragment.show(((BaseActivity) context).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
        }else{
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(feedsDO.getCmspostcontentModel().getUrl()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(context, "No Apps found to view.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void details(String type, FeedsDO feedsDO){
        setFeedsDO(feedsDO);
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(ConstantExtraKey.FEED_OBJECT,feedsDO);
        intent.putExtra(ConstantExtraKey.DETAIL_TYPE,type);
        context.startActivity(intent);
    }


    public static int getFeedIcon(int categoryId){
        switch (categoryId) {
            case 1:
                return R.drawable.others;
            case 2:
                return R.drawable.almarai_locations;
            case 3:
                return R.drawable.health_safety_security;
            case 4:
                return R.drawable.learning_performance;
            case 5:
                return R.drawable.memos;
            case 6:
                return R.drawable.my_hr;
            case 7:
                return R.drawable.news_and_media;
            case 8:
                return R.drawable.guidelines;
            case 9:
                return R.drawable.offers;
            default:
                return R.drawable.others;
        }
    }

}
