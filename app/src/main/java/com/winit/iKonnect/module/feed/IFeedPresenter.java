package com.winit.iKonnect.module.feed;

import com.winit.iKonnect.dataobject.CategoryDO;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.module.base.IBasePresenter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public interface IFeedPresenter extends IBasePresenter{
    void setFavourite(int favourite);

    void fetchFeeds();
    void fetchFeeds(List<FeedsDO> feedsDOs);
    List<FeedsDO> getFeedsDOs();
    ArrayList<CategoryDO> getArrSelected();
    LinkedHashMap<CategoryDO, ArrayList<CategoryDO>> getHmSelected();
    void applyFilter(LinkedHashMap<CategoryDO, ArrayList<CategoryDO>> hmSelected);
    void removeFilter(CategoryDO categoryDO);
}
