package com.winit.iKonnect.module.category;


import com.winit.iKonnect.dataobject.CategoryDO;
import com.winit.iKonnect.module.base.BasePresenter;
import com.winit.iKonnect.module.category.interactors.AsyncCategoryInteractor;
import com.winit.iKonnect.module.category.interactors.IAsyncCategoryInteractor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public class CategoryPresenter extends BasePresenter implements ICategoryPresenter,IAsyncCategoryInteractor.OnCategoriesListener {

    private AsyncCategoryInteractor interactor;
    private ICategoryView view;
    private LinkedHashMap<Integer,ArrayList<CategoryDO>> hmCategoryDOs;

    public CategoryPresenter(ICategoryView view){
        super(view);
        this.view = view;
        this.interactor = new AsyncCategoryInteractor(this);
    }

    @Override
    public LinkedHashMap<Integer, ArrayList<CategoryDO>> getCategories() {
        return hmCategoryDOs;
    }

    @Override
    public String getCategoryName(int categoryId) {
        if(hmCategoryDOs != null) {
           ArrayList<CategoryDO> categoryDOs = hmCategoryDOs.get(0);
            if(categoryDOs != null){
                for(CategoryDO categoryDO : categoryDOs)
                    if(categoryDO.getId() == categoryId)
                        return categoryDO.getName();
            }
        }
        return "No Category";
    }

    @Override
    public void fetchCategories() {
        if(hmCategoryDOs == null)
            interactor.fetchCategories();
        else
            view.onCategoryList(hmCategoryDOs);
    }


    @Override
    public void onError(String Message) {

    }

    @Override
    public void loadData() {

    }

    @Override
    public synchronized void onSuccess(List<CategoryDO> categoryDOs) {
        if(hmCategoryDOs == null) {
            hmCategoryDOs = new LinkedHashMap<>();
            for (CategoryDO categoryDO : categoryDOs) {
                ArrayList<CategoryDO> arrCategoryDO = hmCategoryDOs.get(categoryDO.getParentId());
                if (arrCategoryDO == null)
                    arrCategoryDO = new ArrayList<>();
                arrCategoryDO.add(categoryDO);
                hmCategoryDOs.put(categoryDO.getParentId(), arrCategoryDO);
            }
            handler.postResult(new Runnable() {
                @Override
                public void run() {
                    view.onCategoryList(hmCategoryDOs);
                }
            });
        }
    }
}
