package com.winit.iKonnect.module.trackService;

import com.winit.common.Preference;
import com.winit.common.util.CalendarUtil;
import com.winit.iKonnect.dataobject.ServiceRequestDO;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.trackService.interacter.ITrackInteracter;
import com.winit.iKonnect.module.trackService.interacter.TrackIntracter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rahul.Yadav on 5/25/2017.
 */

public class TrackServicePresenter extends BasePresenter implements ITrackServicePresenter, ITrackInteracter.OnTrackServiceListener {

    private ITrackServiceView view;
    private ITrackInteracter iTrackInteracter;
    private HashMap<String, ArrayList<ServiceRequestDO>> hmTrackRequestDOs;

    public TrackServicePresenter(ITrackServiceView iTrackServiceView) {
        super(iTrackServiceView);
        this.view = iTrackServiceView;
        this.iTrackInteracter = new TrackIntracter(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public ArrayList<ServiceRequestDO> getTrackServicesDOs(String type) {
        return hmTrackRequestDOs != null ? hmTrackRequestDOs.get(type) : null;
    }

    @Override
    public void fetchTrackServices() {
        iTrackInteracter.fetchServices(preference.getStringFromPreference(Preference.STAFF_NUMBER, ""), "");
    }

    @Override
    public void fetchTrackServices(List<ServiceRequestDO> trackServiceDOs) {

    }

    @Override
    public void onError(String Message) {
        handler.postResult(new Runnable() {
            @Override
            public void run() {
                view.onTrackServices(new ArrayList<ServiceRequestDO>());
            }
        });
    }

    @Override
    public void onSuccess(final List<ServiceRequestDO> trackServices) {
        if (hmTrackRequestDOs == null)
            hmTrackRequestDOs = new HashMap<>();
        else {
            hmTrackRequestDOs.clear();
        }
        if (trackServices != null) {
            Collections.sort(trackServices, new Comparator<ServiceRequestDO>() {
                @Override
                public int compare(ServiceRequestDO serviceRequestDO, ServiceRequestDO t1) {
                    return (int) CalendarUtil.getDifferenceTimezone1(t1.getRequestTime(), serviceRequestDO.getRequestTime(), CalendarUtil.MM_DD_YYYYPATTERN, "");
                }
            });
        }
        if (trackServices != null) {
            for (ServiceRequestDO serviceRequestDO : trackServices) {

                 //for business card request in track page it when ever the status is Approved then it is going to replace witb Inprocess
                  // added by sandeep
                // 23 is the form id of Business card request

                if (serviceRequestDO.getFormid() == 23) {
                    if (serviceRequestDO.getStatus().equalsIgnoreCase("Approved")) {
                        serviceRequestDO.setStatus("InProcess");
                    }
                }
                //end

                if (serviceRequestDO.getCompletedTime().contains("-"))
                    serviceRequestDO.setCompletedTime(serviceRequestDO.getCompletedTime().replaceAll("-", "/"));

                if (serviceRequestDO.getModifiedOn().contains("-"))
                    serviceRequestDO.setModifiedOn(serviceRequestDO.getModifiedOn().replaceAll("-", "/"));

                if (serviceRequestDO.getRequestTime().contains("-"))
                    serviceRequestDO.setRequestTime(serviceRequestDO.getRequestTime().replaceAll("-", "/"));

                if (serviceRequestDO.getFinalLevel().equalsIgnoreCase(ServiceRequestDO.FINAL_LEVEL)) {
                    ArrayList<ServiceRequestDO> arrServiceRequest = hmTrackRequestDOs.get(serviceRequestDO.CLOSED);
                    if (arrServiceRequest == null) {
                        arrServiceRequest = new ArrayList<>();
                        arrServiceRequest.add(serviceRequestDO);
                        hmTrackRequestDOs.put(serviceRequestDO.CLOSED, arrServiceRequest);
                    } else
                        arrServiceRequest.add(serviceRequestDO);
                } else {
                    ArrayList<ServiceRequestDO> arrServiceRequest = hmTrackRequestDOs.get(serviceRequestDO.ACTIVE);
                    if (arrServiceRequest == null) {
                        arrServiceRequest = new ArrayList<>();
                        arrServiceRequest.add(serviceRequestDO);
                        hmTrackRequestDOs.put(serviceRequestDO.ACTIVE, arrServiceRequest);
                    } else
                        arrServiceRequest.add(serviceRequestDO);
                }
            }
        }
        handler.postResult(new Runnable() {
            @Override
            public void run() {

                view.onTrackServices(trackServices);
            }
        });
    }


}
