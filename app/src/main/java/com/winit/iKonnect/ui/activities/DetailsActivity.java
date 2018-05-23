package com.winit.iKonnect.ui.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.winit.common.constant.ConstantExtraKey;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.ui.fragments.DetailsFragment;

/**
 * Created by Girish Velivela on 5/17/2017.
 */

public class DetailsActivity extends BaseActivity{

    @Override
    protected void initialize() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.flBody, new DetailsFragment()).commit();
        FeedsDO feedsDO = (FeedsDO) getIntent().getSerializableExtra(ConstantExtraKey.FEED_OBJECT);
        setToolbarTitle(feedsDO.getTitleEn());
    }

    @Override
    protected void setTypeFace() {

    }

}
