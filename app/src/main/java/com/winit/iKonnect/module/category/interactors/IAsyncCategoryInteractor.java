package com.winit.iKonnect.module.category.interactors;

import com.winit.iKonnect.dataobject.CategoryDO;
import com.winit.iKonnect.module.base.interactors.IBaseInteractor;

import java.util.List;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public interface IAsyncCategoryInteractor extends IBaseInteractor{
    void fetchCategories();

    /**
     * Created by Girish Velivela on 5/11/15.
     */
    interface OnCategoriesListener extends BaseListener{
        void onSuccess(List<CategoryDO> categoryDOs);
    }
}
