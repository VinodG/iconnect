package com.winit.iKonnect.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.util.StringUtils;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.DetailsAdapter;
import com.winit.iKonnect.dataobject.FeedDetail;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.dataobject.PostFeedActionDO;
import com.winit.iKonnect.module.comment.FeedDetailPresenter;
import com.winit.iKonnect.module.comment.IFeedDetailPresenter;
import com.winit.iKonnect.module.comment.IFeedDetailView;
import com.winit.iKonnect.ui.activities.BaseActivity;
import com.winit.iKonnect.ui.customview.ExpandableHeightRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Girish Velivela on 5/17/2017.
 */

public class DetailsFragment extends Fragment implements IFeedDetailView {

    private Context context;
    private IFeedDetailPresenter iFeedDetailPresenter;
    private DetailsAdapter commentsAdapter;

    @Nullable
    @Bind(R.id.rvComments)
    ExpandableHeightRecyclerView rvComments;

    @Nullable
    @Bind(R.id.tvDetails)
    TextView tvDetails;

    @Nullable
    @Bind(R.id.etComment)
    EditText etComment;

    @Nullable
    @Bind(R.id.ivProfile)
    ImageView ivProfile;

    @Nullable
    @Bind(R.id.llComment)
    LinearLayout llComment;

    private String type;
    String label = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        type = getActivity().getIntent().getStringExtra(ConstantExtraKey.DETAIL_TYPE);
        if(type == null)
            type = "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        ButterKnife.bind(this, view);
        tvDetails.setTypeface(AppConstants.REGULAR);
        FeedsDO feedsDO = (FeedsDO) getActivity().getIntent().getSerializableExtra(ConstantExtraKey.FEED_OBJECT);
        iFeedDetailPresenter = new FeedDetailPresenter(feedsDO, this);
        if(StringUtils.isEmpty(type))
            iFeedDetailPresenter.setType(PostFeedActionDO.COMMENT);
        else
            iFeedDetailPresenter.setType(type);
        if (getActivity().getIntent().getBooleanExtra(ConstantExtraKey.IS_COMMENT_ENABLE, true) && type.equalsIgnoreCase(PostFeedActionDO.COMMENT)){
            view.setBackgroundColor(getResources().getColor(R.color.comment_background));
            ivProfile.setTag(R.string.isRound,true);
            IKonnectApplication.setImageUrl(ivProfile, ServiceUrls.PROFILE_PIC+ Preference.getInstance().getStringFromPreference(Preference.STAFF_PHOTO_URL,""),R.drawable.profile_pic);
            rvComments.setExpandHeight(false);
            iFeedDetailPresenter.setCommentEnable(true);
            llComment.setVisibility(View.VISIBLE);
        }else {
            if(type.equalsIgnoreCase(PostFeedActionDO.LIKE)) {
                rvComments.setExpandHeight(false);
                view.setBackgroundColor(getResources().getColor(R.color.comment_background));
            }
            llComment.setVisibility(View.GONE);
        }
        refresh(feedsDO);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(((BaseActivity)getActivity()).checkNetworkConnection()) {
            if (type.equalsIgnoreCase(PostFeedActionDO.LIKE)) {
                ((BaseActivity) context).showLoader(String.format(getString(R.string.loading_likes)));
            }else if (type.equalsIgnoreCase(PostFeedActionDO.COMMENT)) {
                ((BaseActivity) context).showLoader(String.format(getString(R.string.loading_comments)));
            }
            iFeedDetailPresenter.getDetails(type);
        }
    }

    public void refresh(FeedsDO feedsDO){
        if(feedsDO != null && feedsDO.getCmspoststatModel() != null) {
            if (type.equalsIgnoreCase(PostFeedActionDO.COMMENT)) {
                if(feedsDO.getCmspoststatModel().getNoOfComments() == 0)
                    tvDetails.setVisibility(View.GONE);
                else
                    tvDetails.setText(PostFeedActionDO.format(getString(R.string.comments), feedsDO.getCmspoststatModel().getNoOfComments(), PostFeedActionDO.COMMENT));
            }else if (type.equalsIgnoreCase(PostFeedActionDO.LIKE)) {
                if(feedsDO.getCmspoststatModel().getNoOfLikes() == 0)
                    tvDetails.setVisibility(View.GONE);
                else
                    tvDetails.setText(PostFeedActionDO.format(getString(R.string.likes), feedsDO.getCmspoststatModel().getNoOfLikes(), PostFeedActionDO.LIKE));
            }else
                tvDetails.setVisibility(View.GONE);
        }else
            tvDetails.setVisibility(View.GONE);
    }

    @Override
    public void onDetails(List<FeedDetail> commentDOs,FeedsDO feedsDO) {
        if(feedsDO != null){
            Intent intent = new Intent();
            intent.setAction(AppConstants.REFRESH_FEEDS);
            intent.putExtra(ConstantExtraKey.FEED_OBJECT,feedsDO);
            context.sendBroadcast(intent);
            refresh(feedsDO);
        }
        rvComments.setVisibility(View.VISIBLE);
        etComment.setText("");
        ((BaseActivity)context).hideLoader();
        if (commentsAdapter == null) {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            rvComments.setLayoutManager(mLayoutManager);
            rvComments.setItemAnimator(new DefaultItemAnimator());
            rvComments.setAdapter(commentsAdapter = new DetailsAdapter(context, commentDOs,StringUtils.isEmpty(type)?PostFeedActionDO.COMMENT : type));
        } else {
            commentsAdapter.refresh(commentDOs);
            rvComments.smoothScrollToPosition(commentDOs.size()-1);
        }
    }

    @Override
    public void noComments() {
        ((BaseActivity)getActivity()).hideLoader();
        tvDetails.setText(label);
        rvComments.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showAlert(String type) {
        ((BaseActivity)getActivity()).hideLoader();
        String message = "";
        switch (type) {
            case AppConstants.Logout:
                ((BaseActivity)context).showCustomDialog(context, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
                break;
            default:
                message = type;
                break;
        }
        ((BaseActivity)context).showCustomDialog(context, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void onLoadFailed() {

    }

    @Nullable
    @OnClick(R.id.ivPostComment)
    public void postComment() {
        if(((BaseActivity)getActivity()).checkNetworkConnection()) {
            String comment = etComment.getText().toString();
            if (StringUtils.isEmpty(comment))
                showAlert(getString(R.string.enter_your_comment));
            else {
                ((BaseActivity) context).showLoader(getString(R.string.Posting_your_comment));
                ((BaseActivity) context).hideKeyboard(etComment);
                iFeedDetailPresenter.postComment(comment);
            }
        }
    }
}