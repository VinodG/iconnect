package com.winit.iKonnect.module.category;

import com.winit.iKonnect.dataobject.CategoryDO;
import com.winit.iKonnect.module.base.IBaseView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public interface ICategoryView extends IBaseView{
    void onCategoryList(LinkedHashMap<Integer,ArrayList<CategoryDO>> hmCategoryDOs);
}
