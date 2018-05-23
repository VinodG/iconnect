package com.winit.iKonnect.module.category;

import com.winit.iKonnect.dataobject.CategoryDO;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public interface ICategoryPresenter {
    LinkedHashMap<Integer,ArrayList<CategoryDO>> getCategories();
    String getCategoryName(int categoryId);
    void fetchCategories();
}
