package com.winit.iKonnect.module.feed;


import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.FileUtils;
import com.winit.iKonnect.dataobject.CategoryDO;
import com.winit.iKonnect.dataobject.CategoryFilterDO;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.dataobject.ServiceResponseData;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.dashboard.interactors.FeedsInteractor;
import com.winit.iKonnect.module.dashboard.interactors.IFeedsInteractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public class FeedPresenter extends BasePresenter implements IFeedPresenter,IFeedsInteractor.OnFeedsListener {

    private IFeedView view;
    private IFeedsInteractor interactor;

    private int favourite;
    private int offset;
    private List<FeedsDO> feedsDOs;
    private LinkedHashMap<CategoryDO, ArrayList<CategoryDO>> hmSelected;
    private ArrayList<CategoryDO> arrSelected;
    private CategoryFilterDO categoryFilterDO;

    public FeedPresenter(IFeedView view){
        super(view);
        this.view = view;
        interactor = new FeedsInteractor(this);
        categoryFilterDO = new CategoryFilterDO();
        categoryFilterDO.setCIds(new ArrayList<Integer>());
        categoryFilterDO.setSCIds(new ArrayList<Integer>());
        categoryFilterDO.setSSCIds(new ArrayList<Integer>());
    }

    @Override
    public void setFavourite(int favourite) {
        if(favourite == FeedsDO.FAVOURITE){
            HashMap<Integer,FeedsDO> hmfavourite = (HashMap<Integer, FeedsDO>) IKonnectApplication.getCacheObject(AppConstants.FAVOURITE_TYPE);
            if(hmfavourite != null)
                feedsDOs = new ArrayList<>(hmfavourite.values());
        }else{
            hmSelected = (LinkedHashMap<CategoryDO, ArrayList<CategoryDO>>) IKonnectApplication.getCacheObject(AppConstants.FILTER_TYPE);
        }
        this.favourite = favourite;
    }

    @Override
    public List<FeedsDO> getFeedsDOs() {
        return feedsDOs;
    }

    @Override
    public ArrayList<CategoryDO> getArrSelected() {
        return arrSelected;
    }

    @Override
    public LinkedHashMap<CategoryDO, ArrayList<CategoryDO>> getHmSelected() {
        return hmSelected!=null?cloneSelectedFilter(hmSelected):hmSelected;
    }

    private LinkedHashMap<CategoryDO, ArrayList<CategoryDO>> cloneSelectedFilter(LinkedHashMap<CategoryDO, ArrayList<CategoryDO>> hmSelected){
        LinkedHashMap<CategoryDO, ArrayList<CategoryDO>> hmSelectedFilter = new LinkedHashMap<>();
        for(CategoryDO categoryDO : hmSelected.keySet()){
            hmSelectedFilter.put(categoryDO, (ArrayList<CategoryDO>) hmSelected.get(categoryDO).clone());
        }
        return hmSelectedFilter;
    }

    @Override
    public void onError(final String message) {
        view.showAlert(message);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void fetchFeeds() {
        if(hmSelected != null && hmSelected.size()>0) {
            if(arrSelected != null)
                arrSelected.clear();
            prepareFilter(hmSelected, true, false);
        }else
            interactor.fetchFeeds(preference.getStringFromPreference(Preference.STAFF_NUMBER,"86262"),categoryFilterDO, 0,favourite);

    }

    @Override
    public void fetchFeeds(List<FeedsDO> feedsDOs) {
        if (feedsDOs != null && feedsDOs.size() > 0) {
            offset = feedsDOs.size();
            interactor.fetchFeeds(feedsDOs, preference.getStringFromPreference(Preference.STAFF_NUMBER, "86262"), categoryFilterDO, offset, favourite);
        }
    }

    @Override
    public void applyFilter(LinkedHashMap<CategoryDO, ArrayList<CategoryDO>> hmSelected) {
        this.hmSelected = hmSelected;
        if(arrSelected != null)
            arrSelected.clear();
        prepareFilter(hmSelected,true,true);
    }

    private void prepareFilter(final LinkedHashMap<CategoryDO, ArrayList<CategoryDO>> hmSelected, boolean isAdd, boolean isCache){
        categoryFilterDO.getCIds().clear();
        categoryFilterDO.getSCIds().clear();
        categoryFilterDO.getSSCIds().clear();
        Set<CategoryDO> keys = hmSelected.keySet();
        for(CategoryDO key : keys) {
            ArrayList<CategoryDO> categoryDOs = hmSelected.get(key);
            for (CategoryDO categoryDO : categoryDOs) {
                if(categoryDO.getLevel() == 1){
                    if(!categoryFilterDO.getCIds().contains(categoryDO.getId()))
                        categoryFilterDO.getCIds().add(categoryDO.getId());
                }else if(categoryDO.getLevel() == 2){
                    if(!categoryFilterDO.getSCIds().contains(categoryDO.getId()))
                        categoryFilterDO.getSCIds().add(categoryDO.getId());
                }else if(categoryDO.getLevel() == 3){
                    if(!categoryFilterDO.getSSCIds().contains(categoryDO.getId()))
                        categoryFilterDO.getSSCIds().add(categoryDO.getId());
                }
                if(isAdd) {
                    if(arrSelected == null)
                        arrSelected = new ArrayList<>();
                    arrSelected.add(categoryDO);
                }
            }
        }
        if(isCache && favourite == FeedsDO.NON_FAVOURITE){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileUtils.saveObjAsFile(AppConstants.FILTER_TYPE,hmSelected);
                    IKonnectApplication.setCacheObject(AppConstants.FILTER_TYPE,hmSelected);
                }
            }).start();
        }
        interactor.fetchFeeds(preference.getStringFromPreference(Preference.STAFF_NUMBER,"86262"),categoryFilterDO,0,favourite);
    }

    @Override
    public void removeFilter(CategoryDO categoryDO) {
        if(hmSelected.containsKey(categoryDO)){
            hmSelected.remove(categoryDO);
        }else{
            Set<CategoryDO> keys = hmSelected.keySet();
            for(CategoryDO key : keys) {
                ArrayList<CategoryDO> categoryDOs = hmSelected.get(key);
                if(categoryDOs.remove(categoryDO)) {
                    if(categoryDOs.size() == 0)
                        hmSelected.remove(key);
                    break;
                }
            }
        }
        arrSelected.remove(categoryDO);
        prepareFilter(hmSelected,false,true);
    }


    @Override
    public void onSuccess(final List<FeedsDO> feeds) {
        feedsDOs = feeds;
        handler.postResult(new Runnable() {
            @Override
            public void run() {
                if(feeds != null && feeds.size() >0)
                    view.onFeeds(feeds);
                else
                    view.noOfFeeds();
            }
        });
        if(favourite == FeedsDO.FAVOURITE && (hmSelected== null || hmSelected.size() ==0)){
            HashMap<Integer,FeedsDO> hmFeed = new HashMap<>();
            if(feeds!=null) {
                for (FeedsDO feedsDO : feeds) {
                    hmFeed.put(feedsDO.getId(), feedsDO);
                }
            }
            IKonnectApplication.setCacheObject(AppConstants.FAVOURITE_TYPE,hmFeed);
            FileUtils.saveObjAsFile(AppConstants.FAVOURITE_TYPE,hmFeed);
        }
    }

    @Override
    public void gotMessege(String s,String s_arabic) {

    }

    @Override
    public void gotFormData(HashMap<String, ServiceResponseData> hmFormDataDetail) {

    }
}
