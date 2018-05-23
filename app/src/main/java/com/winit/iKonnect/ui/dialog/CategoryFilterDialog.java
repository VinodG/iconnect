package com.winit.iKonnect.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ExpandableListView;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.CategoryFilterAdapter;
import com.winit.iKonnect.dataobject.CategoryDO;
import com.winit.iKonnect.module.category.ICategoryFilterListener;
import com.winit.iKonnect.ui.activities.BaseActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Girish Velivela on 5/18/2017.
 */

public class CategoryFilterDialog extends Dialog{


    @Bind(R.id.elCategoryFilter)
    ExpandableListView elCategoryFilter;

    private ICategoryFilterListener iCategoryFilterListener;
    private LinkedHashMap<CategoryDO,ArrayList<CategoryDO>> hmSelected = new LinkedHashMap<>();

    public LinkedHashMap<CategoryDO, ArrayList<CategoryDO>> getHmSelected() {
        return hmSelected;
    }

    public CategoryFilterDialog(Context context, ICategoryFilterListener iCategoryFilterListener,LinkedHashMap<Integer,ArrayList<CategoryDO>> hmCategoryDOs,LinkedHashMap<CategoryDO,ArrayList<CategoryDO>> hmSelected) {
        super(context, R.style.Dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.category_filter_dialog,null);
        Preference preference = Preference.getInstance();
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 600),
                preference.getIntFromPreference(Preference.DEVICE_DISPLAY_HEIGHT, 600)-((BaseActivity)context).getStatusBarHeight());
        setContentView(view,layoutParams);
        ButterKnife.bind(this, view);
        IKonnectApplication.setTypeFace(view, AppConstants.REGULAR);
//        setCancelable(false);
        this.iCategoryFilterListener = iCategoryFilterListener;
        if(hmSelected == null)
            hmSelected = new LinkedHashMap<>();
        this.hmSelected = hmSelected;
        CategoryDO parentCategoryDO = new CategoryDO();
//        parentCategoryDO.setId(-1);
        elCategoryFilter.setAdapter(new CategoryFilterAdapter(context,hmCategoryDOs.get(0),hmCategoryDOs, hmSelected,parentCategoryDO,
                CategoryFilterAdapter.LEVEL_ONE));
    }

    public void showCustomDialog(){
        try {
            show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.tvCancel,R.id.ivBack})
    public void cancel(){
        iCategoryFilterListener.onCancel();
    }

    @OnClick(R.id.tvApply)
    public void apply(){
        iCategoryFilterListener.onApply();
    }

}
