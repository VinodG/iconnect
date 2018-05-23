package com.winit.iKonnect.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winit.common.constant.ConstantExtraKey;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.FeedContentAdapter;
import com.winit.iKonnect.dataobject.CmspostcontentfileModels;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AttachmentBottomSheetFragment extends BottomSheetDialogFragment{

    @Bind(R.id.rvContentFile)
    public RecyclerView rvContentFile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_content_popup, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        List<CmspostcontentfileModels> cmspostcontentfileModelsList = (List<CmspostcontentfileModels>) bundle.getSerializable(ConstantExtraKey.CONTENT_FILE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvContentFile.setLayoutManager(linearLayoutManager);
        rvContentFile.setItemAnimator(new DefaultItemAnimator());
        rvContentFile.setAdapter(new FeedContentAdapter(getActivity(), cmspostcontentfileModelsList));
    }


    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

}
