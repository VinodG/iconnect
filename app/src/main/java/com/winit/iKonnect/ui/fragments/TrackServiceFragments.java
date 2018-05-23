package com.winit.iKonnect.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.util.CalendarUtil;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.TrackServicesAdapter;
import com.winit.iKonnect.dataobject.ServiceRequestDO;
import com.winit.iKonnect.module.trackService.ITrackServicePresenter;
import com.winit.iKonnect.ui.activities.BaseActivity;
import com.winit.iKonnect.ui.activities.DashboardActivity;
import com.winit.iKonnect.ui.activities.TrackServiceActivity;
import com.winit.iKonnect.ui.activities.TrackServiceDetails;

import java.lang.reflect.Constructor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rahul.Yadav on 5/25/2017.
 */

public class TrackServiceFragments extends Fragment {

    private ITrackServicePresenter iTrackServicePresenter;
    private Context context;

    @Nullable
    @Bind(R.id.rv_trackServices)
    public RecyclerView rvTrackservices;

    @Nullable
    @Bind(R.id.tvNoData)
    TextView tvNoData;

    @Nullable
    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private TrackServicesAdapter trackServicesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        iTrackServicePresenter = ((DashboardActivity) getActivity()).iTrackServicePresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.active_track_services, container, false);
        ButterKnife.bind(this, view);
        int position = getArguments().getInt(ConstantExtraKey.TRACKING_SERVICE_POSITION);
        if (position == 0)
            showActiveServices();
        else
            showClosedServices();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (((BaseActivity) context).checkNetworkConnection()) {
                    iTrackServicePresenter.fetchTrackServices();
                }
            }
        });

        return view;
    }

    private void showActiveServices() {
        ArrayList<ServiceRequestDO> trackServiceDO = null;
        if (iTrackServicePresenter!=null) {
            trackServiceDO = iTrackServicePresenter.getTrackServicesDOs(ServiceRequestDO.ACTIVE);
        }

        if (trackServiceDO != null && trackServiceDO.size() > 0) {

            Collections.sort(trackServiceDO,new SortDateDescending());

          /* if(trackServicesAdapter==null)
           {*/
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
            rvTrackservices.setLayoutManager(mLayoutManager);
            rvTrackservices.setItemAnimator(new DefaultItemAnimator());
            rvTrackservices.setAdapter(trackServicesAdapter = new TrackServicesAdapter(getActivity(), trackServiceDO));
          /* }
           else
               trackServicesAdapter.refresh(trackServiceDO);*/
        } else {
            mSwipeRefreshLayout.setVisibility(View.GONE);
            tvNoData.setText("" + getString(R.string.no_data_found));
            tvNoData.setVisibility(View.VISIBLE);
        }
    }


    private void showClosedServices() {
        ArrayList<ServiceRequestDO> trackServiceDO=null;
        if (iTrackServicePresenter!=null) {
            trackServiceDO = iTrackServicePresenter.getTrackServicesDOs(ServiceRequestDO.CLOSED);
        }

        if (trackServiceDO != null && trackServiceDO.size() > 0) {
           /* if(trackServicesAdapter==null)
            {*/
            Collections.sort(trackServiceDO,new SortDateDescending());

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
            rvTrackservices.setLayoutManager(mLayoutManager);
            rvTrackservices.setItemAnimator(new DefaultItemAnimator());
            rvTrackservices.setAdapter(trackServicesAdapter = new TrackServicesAdapter(getActivity(), trackServiceDO));
            /*}
            else
                trackServicesAdapter.refresh(trackServiceDO);*/
        } else {
            mSwipeRefreshLayout.setVisibility(View.GONE);
            tvNoData.setText("" + getString(R.string.no_data_found));
            tvNoData.setVisibility(View.VISIBLE);
        }
    }

    public class SortDateDescending implements Comparator<ServiceRequestDO>
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CalendarUtil.MM_DD_YYYYPATTERN);
        public int compare(ServiceRequestDO lhs, ServiceRequestDO rhs)
        {
            int compareResult = 0;
            try {

                Date arg0Date = dateFormat.parse(lhs.getRequestTime());
                Date arg1Date = dateFormat.parse(rhs.getRequestTime());
                if ( arg0Date.compareTo(arg1Date)>=1)
                    return -1;
                else if ( arg0Date.compareTo(arg1Date)<=-1)
                    return 1;
                else
                    return 0;

            } catch (ParseException e) {
                e.printStackTrace();
                compareResult = lhs.getRequestTime().compareTo(rhs.getRequestTime());
            }
            return compareResult;
        }
    }
}
