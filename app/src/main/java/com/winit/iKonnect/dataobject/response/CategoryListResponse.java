package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.CategoryDO;

import java.util.List;

/**
 * Created by Girish Velivela on 5/18/2017.
 */

public class CategoryListResponse extends BaseResponse {

    private List<CategoryDO> categoriesModels;

    public List<CategoryDO> getCategoriesModels() {
        return categoriesModels;
    }

    public void setCategoriesModels(List<CategoryDO> categoriesModels) {
        this.categoriesModels = categoriesModels;
    }

}
