package com.winit.iKonnect.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.CategoryDO;
import com.winit.iKonnect.ui.customview.CustomExpandableListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Girish Velivela on 5/19/2017.
 */

public class CategoryFilterAdapter extends BaseExpandableListAdapter {

    public static final int LEVEL_ONE = 1;
    public static final int LEVEL_TWO = 2;

    private Context context;
    private LinkedHashMap<Integer,ArrayList<CategoryDO>> hmCategoryDOs;
    private ArrayList<CategoryDO> arrCategoryDOs;
    private int level;
    private CategoryDO parentCategoryDO;
    private LinkedHashMap<CategoryDO,ArrayList<CategoryDO>> hmSelected;
    private CategoryFilterAdapter parentCategoryFilterAdapter;
    private static int checkboxWidth = 0;
    public CategoryFilterAdapter(Context context, ArrayList<CategoryDO> arrCategoryDOs, LinkedHashMap<Integer,ArrayList<CategoryDO>> hmCategoryDOs,
                                 LinkedHashMap<CategoryDO,ArrayList<CategoryDO>> hmSelected, CategoryDO parentCategoryDO,
                                 int level){
        this.context = context;
        this.hmCategoryDOs = hmCategoryDOs;
        this.arrCategoryDOs = arrCategoryDOs;
        this.parentCategoryDO = parentCategoryDO;
        this.level = level;
        this.hmSelected = hmSelected;
        if(checkboxWidth == 0) {
            BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.plus);
            checkboxWidth = bd.getBitmap().getWidth();
        }
    }

    public CategoryFilterAdapter(Context context, ArrayList<CategoryDO> arrCategoryDOs, LinkedHashMap<Integer,ArrayList<CategoryDO>> hmCategoryDOs,
                                 LinkedHashMap<CategoryDO,ArrayList<CategoryDO>> hmSelected, CategoryDO parentCategoryDO,
                                 int level,
                                 int subLevelChildPosition,CategoryFilterAdapter parentCategoryFilterAdapter){
        this.context = context;
        this.hmCategoryDOs = hmCategoryDOs;
        this.arrCategoryDOs = arrCategoryDOs;
        this.parentCategoryDO = parentCategoryDO;
        this.level = level;
        this.hmSelected = hmSelected;
        this.parentCategoryFilterAdapter = parentCategoryFilterAdapter;
        if(checkboxWidth == 0) {
            BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.plus);
            checkboxWidth = bd.getBitmap().getWidth();
        }
    }


    @Override
    public int getGroupCount() {
        if(arrCategoryDOs != null && arrCategoryDOs.size() >0)
            return arrCategoryDOs.size();
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        CategoryDO categoryDO = arrCategoryDOs.get(groupPosition);
        return hmCategoryDOs.get(categoryDO.getId()) == null?0:hmCategoryDOs.get(categoryDO.getId()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
        CategoryDO categoryDO = arrCategoryDOs.get(groupPosition);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.category_cell, null);
            viewHolder.cbCheck = (CheckBox) convertView.findViewById(R.id.cbCheck);
            viewHolder.tvCategoryName = (TextView) convertView.findViewById(R.id.tvCategoryName);
            viewHolder.tvCategoryName.setTypeface(AppConstants.REGULAR);
            viewHolder.ivExpandGroup = (ImageView) convertView.findViewById(R.id.ivExpandGroup);
            if(level == LEVEL_ONE){
                viewHolder.tvCategoryName.setTypeface(AppConstants.BOLD);
                viewHolder.tvCategoryName.setTextColor(context.getResources().getColor(R.color.app_color));
            }else if(level == LEVEL_TWO) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(checkboxWidth, 1);
                viewHolder.spaceView = convertView.findViewById(R.id.childSpace);
                viewHolder.spaceView.setLayoutParams(params);
                viewHolder.spaceView.setVisibility(View.VISIBLE);
            }
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.cbCheck.setChecked(isChecked(parentCategoryDO,categoryDO));
        if(hmCategoryDOs.get(categoryDO.getId()) != null) {
            viewHolder.ivExpandGroup.setVisibility(View.VISIBLE);
            if(isExpanded)
                viewHolder.ivExpandGroup.setImageResource(R.drawable.minus);
            else
                viewHolder.ivExpandGroup.setImageResource(R.drawable.plus);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isExpanded) ((ExpandableListView) parent).collapseGroup(groupPosition);
                    else ((ExpandableListView) parent).expandGroup(groupPosition, true);
                }
            });
        }else
            viewHolder.ivExpandGroup.setVisibility(View.GONE);
        viewHolder.cbCheck.setTag(categoryDO);
        viewHolder.tvCategoryName.setText(categoryDO.getName());
//        viewHolder.cbCheck.setOnCheckedChangeListener(new CheckBoxListener(categoryDO,parentCategoryDO));
        viewHolder.cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryDO categoryDO = (CategoryDO) view.getTag();
                modifyFilter(((CheckBox)view).isChecked(),categoryDO,parentCategoryDO);
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CategoryDO parentCategory = arrCategoryDOs.get(groupPosition);
        CategoryDO childCategory = hmCategoryDOs.get(parentCategory.getId())!= null?hmCategoryDOs.get(parentCategory.getId()).get(childPosition):null;
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            if (level == LEVEL_ONE) {
                viewHolder.secondLevelELV = new CustomExpandableListView(context);
                ArrayList<CategoryDO> childCategories = new ArrayList<>();
                childCategories.add(childCategory);
                viewHolder.secondLevelELV.setAdapter(new CategoryFilterAdapter(context,childCategories, hmCategoryDOs, hmSelected, parentCategory,LEVEL_TWO,childPosition,this));
                viewHolder.secondLevelELV.setGroupIndicator(null);
                viewHolder.secondLevelELV.setDivider(null);
                convertView = viewHolder.secondLevelELV;
            } else {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.category_cell, null);
                viewHolder.tvCategoryName = (TextView) convertView.findViewById(R.id.tvCategoryName);
                viewHolder.spaceView = convertView.findViewById(R.id.childSpace);
                viewHolder.spaceView.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(2*checkboxWidth, 1);
                viewHolder.spaceView.setLayoutParams(params);
                viewHolder.cbCheck = (CheckBox) convertView.findViewById(R.id.cbCheck);
            }
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            if(level == LEVEL_ONE) {
                ArrayList<CategoryDO> childCategories = new ArrayList<>();
                childCategories.add(childCategory);
                ((CategoryFilterAdapter) viewHolder.secondLevelELV.getExpandableListAdapter()).refresh(childCategories, hmCategoryDOs, hmSelected, parentCategory, LEVEL_TWO, childPosition, this);
            }
        }
        if(level != LEVEL_ONE){
            viewHolder.tvCategoryName.setText(childCategory.getName());
            /*if(hmSelected.containsKey(parentCategory)){
                viewHolder.cbCheck.setChecked(hmSelected.containsKey(parentCategory)? hmSelected.get(parentCategory) == null?false:hmSelected.get(parentCategory).contains(childCategory):false);
            }else if(hmSelected.containsKey(parentCategoryDO)){
                viewHolder.cbCheck.setChecked(hmSelected.containsKey(parentCategoryDO)? hmSelected.get(parentCategoryDO) == null?false:hmSelected.get(parentCategoryDO).contains(parentCategory):false);
            }else
                viewHolder.cbCheck.setChecked(false);*/
            viewHolder.cbCheck.setChecked(isChecked(parentCategory,childCategory));
//            viewHolder.cbCheck.setOnCheckedChangeListener(new CheckBoxListener(childCategory,parentCategory));
            viewHolder.cbCheck.setTag(childCategory);
            viewHolder.cbCheck.setTag(R.string.parentCategory,parentCategory);
            viewHolder.cbCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CategoryDO categoryDO = (CategoryDO) view.getTag();
                    CategoryDO parentCategory = (CategoryDO) view.getTag(R.string.parentCategory);
                    modifyFilter(((CheckBox)view).isChecked(),categoryDO,parentCategory);
                }
            });
        }
        return convertView;
    }

    public class ViewHolder{
        CustomExpandableListView secondLevelELV;
        TextView tvCategoryName;
        View spaceView;
        CheckBox cbCheck;
        ImageView ivExpandGroup;
    }

    private boolean isChecked(CategoryDO childParentCategory, CategoryDO categoryDO){
        // need to change logic to n-level now logic is only for three level.
        if(hmSelected.containsKey(categoryDO)) {
            ArrayList<CategoryDO> arrCategorys = hmCategoryDOs.get(categoryDO.getId());
            return arrCategorys.size() == hmSelected.get(categoryDO).size();
        }else if(hmSelected.containsKey(childParentCategory))
            return hmSelected.get(childParentCategory).contains(categoryDO);
        else if(hmSelected.containsKey(parentCategoryDO))
            return hmSelected.get(parentCategoryDO).contains(childParentCategory);
        else{
            CategoryDO superParentCategory = new CategoryDO();
            superParentCategory.setLevel(parentCategoryDO.getLevel()-1);
            return hmSelected.containsKey(superParentCategory)?hmSelected.get(superParentCategory).contains(parentCategoryDO):false;
        }
    }

    private void modifyFilter(boolean isChecked, CategoryDO categoryDO, CategoryDO childParentCategoryDO){
        if(isChecked){
            hmSelected.remove(categoryDO);
            ArrayList<CategoryDO> arrCategorys = hmSelected.get(childParentCategoryDO);
            if(arrCategorys == null)
                arrCategorys = new ArrayList<>();
            arrCategorys.add(categoryDO);
            hmSelected.put(childParentCategoryDO,arrCategorys);
            ArrayList<CategoryDO> childrens = hmCategoryDOs.get(childParentCategoryDO.getId());
            if(childrens != null){
                if(childrens.size() == hmSelected.get(childParentCategoryDO).size()){
                    for(CategoryDO childCategoryDO : hmSelected.get(childParentCategoryDO))
                        hmSelected.remove(childCategoryDO);
                    if(childParentCategoryDO.getLevel() != 0)
                        hmSelected.remove(childParentCategoryDO);
                    CategoryDO parentCategoryDO = this.parentCategoryDO;
                    if(childParentCategoryDO.getParentId() == 0)
                        parentCategoryDO = new CategoryDO();
                    if(!parentCategoryDO.equals(childParentCategoryDO)) {
                        ArrayList<CategoryDO> arrParentCategorys = hmSelected.get(parentCategoryDO);
                        if (arrParentCategorys == null)
                            arrParentCategorys = new ArrayList<>();
                        arrParentCategorys.add(childParentCategoryDO);
                        hmSelected.put(parentCategoryDO, arrParentCategorys);
                    }
                }
            }
        }else{
            if(hmSelected.containsKey(categoryDO)){
                hmSelected.remove(categoryDO);
            }else if(hmSelected.containsKey(childParentCategoryDO)){
                hmSelected.get(childParentCategoryDO).remove(categoryDO);
                if(hmSelected.get(childParentCategoryDO).size() == 0){
                    hmSelected.remove(childParentCategoryDO);
                }
            }else{
                CategoryDO parentCategoryDO = this.parentCategoryDO;
                if(childParentCategoryDO.getParentId() == 0)
                    parentCategoryDO = new CategoryDO();
                if(hmSelected.containsKey(parentCategoryDO)){
                    if(hmSelected.get(parentCategoryDO).remove(childParentCategoryDO)){
                        ArrayList<CategoryDO> categoryDOs = (ArrayList<CategoryDO>) hmCategoryDOs.get(childParentCategoryDO.getId()).clone();
                        categoryDOs.remove(categoryDO);
                        hmSelected.put(childParentCategoryDO,categoryDOs);
                    }
                }
            }
        }
        if(parentCategoryFilterAdapter != null)
            parentCategoryFilterAdapter.notifyDataSetChanged();
        else
            notifyDataSetChanged();
    }

    private class CheckBoxListener implements CompoundButton.OnCheckedChangeListener{

        private CategoryDO categoryDO,parentCategoryDO;

        public CheckBoxListener(CategoryDO categoryDO,CategoryDO parentCategoryDO){
            this.categoryDO = categoryDO;
            this.parentCategoryDO = parentCategoryDO;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if(isChecked){
                addChildren(categoryDO);
                addParent(categoryDO,parentCategoryDO);
            }else{
                removeChild(categoryDO);
                removeParent(categoryDO);
            }
            if(parentCategoryFilterAdapter != null)
                parentCategoryFilterAdapter.notifyDataSetChanged();
            else
                notifyDataSetChanged();
        }
    }

    private void addChildren(CategoryDO categoryDO){
        if(hmCategoryDOs.get(categoryDO.getId()) != null){
            hmSelected.put(categoryDO, (ArrayList<CategoryDO>) hmCategoryDOs.get(categoryDO.getId()).clone());
            if(level != LEVEL_TWO) {
                for (CategoryDO childCategoryDO : hmCategoryDOs.get(categoryDO.getId())) {
                    addChildren(childCategoryDO);
                }
            }
        }
    }


    private void addParent(CategoryDO categoryDO,CategoryDO parentChildParentCategoryDO){
        if(categoryDO.getParentId() != 0) {
            ArrayList<CategoryDO> categoryDOs = hmSelected.get(parentChildParentCategoryDO);
            if(categoryDOs == null)
                categoryDOs = new ArrayList<>();
            categoryDOs.add(categoryDO);
            hmSelected.put(parentChildParentCategoryDO, categoryDOs);
            addParent(parentChildParentCategoryDO,parentCategoryDO);
        }
    }

    private void removeChild(CategoryDO categoryDO){
        if(hmSelected.containsKey(categoryDO)) {
            ArrayList<CategoryDO> categoryDOs = hmSelected.get(categoryDO);
            if(categoryDOs != null) {
                for(CategoryDO childCategory : categoryDOs)
                    removeChild(childCategory);
            }
            hmSelected.remove(categoryDO);
        }
    }

    private void removeParent(CategoryDO categoryDO){
        if(hmCategoryDOs.get(categoryDO.getId()) != null) {
            hmSelected.remove(categoryDO);
            if(categoryDO.getParentId() != 0) {
                ArrayList<CategoryDO> arrCategoryDOs = hmCategoryDOs.get(categoryDO.getParentId());
                if (arrCategoryDOs != null) {
                    for (CategoryDO parentCategoryDO : arrCategoryDOs) {
                        if (parentCategoryDO.getId() == categoryDO.getParentId()) {
                            removeParent(parentCategoryDO);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void refresh(LinkedHashMap<Integer,ArrayList<CategoryDO>> hmCategoryDOs){
        this.hmCategoryDOs = hmCategoryDOs;
        notifyDataSetChanged();
    }

    private void refresh(ArrayList<CategoryDO> arrCategoryDOs, LinkedHashMap<Integer,ArrayList<CategoryDO>> hmCategoryDOs,
                         LinkedHashMap<CategoryDO,ArrayList<CategoryDO>> hmSelected, CategoryDO parentCategoryDO,
                         int level,
                         int subLevelChildPosition,CategoryFilterAdapter parentCategoryFilterAdapter){
        this.hmCategoryDOs = hmCategoryDOs;
        this.arrCategoryDOs = arrCategoryDOs;
        this.parentCategoryDO = parentCategoryDO;
        this.level = level;
        this.hmSelected = hmSelected;
        this.parentCategoryFilterAdapter = parentCategoryFilterAdapter;
        notifyDataSetChanged();
    }
}
