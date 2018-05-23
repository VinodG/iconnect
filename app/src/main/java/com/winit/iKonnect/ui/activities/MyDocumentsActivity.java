package com.winit.iKonnect.ui.activities;

import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.MyDocumentsAdapter;
import com.winit.iKonnect.adapter.ServicesAdapter;
import com.winit.iKonnect.dataobject.MyDocumentsDO;
import com.winit.iKonnect.module.myDocuments.IMyDocumentsPresenter;
import com.winit.iKonnect.module.myDocuments.IMyDocumentsView;
import com.winit.iKonnect.module.myDocuments.MyDocumentsPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sreekanth.P on 01-12-2017.
 */

public class MyDocumentsActivity extends BaseActivity implements IMyDocumentsView {

    @Nullable
    @Bind(R.id.rvMyDocuments)
    RecyclerView rvMyDocuments;
    @Nullable
    @Bind(R.id.tvdataNotFound)
    TextView tvdataNotFound;

    private IMyDocumentsPresenter iMyDocumentsPresenter;
    private MyDocumentsAdapter myDocumentsAdapter;
    private ArrayList<MyDocumentsDO> uploadDOArrayList;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.mydocuments_activity, flBody, true);
        ButterKnife.bind(this);
        setToolbarTitle("My Documents");

        iMyDocumentsPresenter = new MyDocumentsPresenter(this);
        getMyDocuments();
        uploadDOArrayList = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MyDocumentsActivity.this, 2);
        rvMyDocuments.setLayoutManager(mLayoutManager);
        rvMyDocuments.setItemAnimator(new DefaultItemAnimator());
        rvMyDocuments.setAdapter(myDocumentsAdapter = new MyDocumentsAdapter(MyDocumentsActivity.this, uploadDOArrayList));

    }

    private void getMyDocuments() {
        showLoader("Loading...");
        iMyDocumentsPresenter.getUserDocuments(preference.getStringFromPreference(Preference.STAFF_ID, ""));
    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    public void getDocumentsUpload(final ArrayList<MyDocumentsDO> uploadDOArrayList) {

        hideLoader();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (uploadDOArrayList != null) {
                    tvdataNotFound.setVisibility(View.GONE);
                    rvMyDocuments.setVisibility(View.VISIBLE);
                    myDocumentsAdapter.refresh(uploadDOArrayList);

                } else {

                    tvdataNotFound.setVisibility(View.VISIBLE);
                    rvMyDocuments.setVisibility(View.GONE);


                }
            }
        });


    }

    @Override
    public void showAlert(String message) {
        hideLoader();

        if (message.equalsIgnoreCase("No data found")) {

            tvdataNotFound.post(new Runnable() {
                @Override
                public void run() {
                    tvdataNotFound.setVisibility(View.VISIBLE);
                }
            });
            rvMyDocuments.post(new Runnable() {
                @Override
                public void run() {
                    rvMyDocuments.setVisibility(View.GONE);
                }
            });
        }
        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(MyDocumentsActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(MyDocumentsActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void onLoadFailed() {

    }
}
