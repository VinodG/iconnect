package com.winit.iKonnect.module.staffdetail;

import com.winit.common.constant.AppConstants;
import com.winit.common.util.FileUtils;
import com.winit.common.util.StringUtils;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.dataobject.request.ServiceRequest;
import com.winit.iKonnect.dataobject.request.ServiceRequestBody;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.staffdetail.Interactor.StaffDetailInteractor;
import com.winit.iKonnect.module.staffdetail.Interactor.iStaffDetailInteractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ashoka.Reddy on 6/16/2017.
 */

public class StaffDetailPresentor extends BasePresenter implements iStaffDetailPresentor {

    private ArrayList<String> attachments;
    private iStaffDetailInteractor iStaffDetailInteractor;
    private String updateProfilePath="";
    private iStaffDetailView iStaffDetailView;
    private String profilePic;

    public StaffDetailPresentor(iStaffDetailView istaffDetailView) {
        super(istaffDetailView);
        this.iStaffDetailView = istaffDetailView;
        this.iStaffDetailInteractor = new StaffDetailInteractor(StaffDetailPresentor.this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void postProfilePicture(String path, int id) {
        profilePic = path;
        ArrayList<String> arrayList = new ArrayList<>();
//        if(path.contains(".jpg"))
//        updateProfilePath=path.substring(0, path.lastIndexOf("/"))+"/"+id+".jpg";
//        else
//            updateProfilePath=path.substring(0, path.lastIndexOf("/"))+"/"+id+".png";
        arrayList.add(path);
        ServiceRequest serviceRequest = new ServiceRequest();
        ServiceRequestBody serviceRequestBody = new ServiceRequestBody();
        serviceRequestBody.setServiceId(id);
        serviceRequestBody.setArrServiceAttachments(arrayList);
        serviceRequest.setServiceRequestBody(serviceRequestBody);
        if (path!=null && !StringUtils.isEmpty(path))
            iStaffDetailInteractor.postAttachments(serviceRequest);
    }

    @Override
    public void onProfilSuccess() {
        String profileCache = AppConstants.CACHE_DIR_PATH+"/"+String.valueOf((ServiceUrls.PROFILE_PIC+preference.getStringFromPreference(preference.STAFF_PHOTO_URL,"")).hashCode());
        File file = new File(profileCache);
        boolean deleted = file.delete();
        try {
            FileUtils.copyDirectory(new File(profilePic),new File(profileCache));
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.postResult(new Runnable() {
            @Override
            public void run() {
                iStaffDetailView.onProfilePicSuccess();
            }
        });
    }

    @Override
    public void onError(String s) {
        iStaffDetailView.showAlert(s);
    }


}
