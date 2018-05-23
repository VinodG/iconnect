package com.winit.iKonnect.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

/**
 * Created by Ashoka.Reddy on 7/11/2017.
 */

public class ExpandableHeightListView extends ExpandableListView {
    private boolean isExpandHeight = true;

    public ExpandableHeightListView(Context context){
        super(context);
    }

    public ExpandableHeightListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ExpandableHeightListView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        if(isExpandHeight) {
            int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();

            setNestedScrollingEnabled(false);
            setOverScrollMode(ExpandableListView.OVER_SCROLL_NEVER);
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public boolean isExpandHeight() {
        return isExpandHeight;
    }

    public void setExpandHeight(boolean expandHeight) {
        isExpandHeight = expandHeight;
    }
}
