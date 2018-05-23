package com.winit.iKonnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.response.EmpEarnings;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rohitmanohar on 23-05-2017.
 */

public class PaySlipsAdapter extends BaseExpandableListAdapter
{
    private HashMap<String, Object> hashMapdata;
    private Context mContext;
    private ArrayList<String> arrHeader;

    public PaySlipsAdapter(ArrayList<String> arrHeader,HashMap<String, Object> hashMapdata, Context mContext) {
        this.arrHeader= arrHeader;
        this.hashMapdata = hashMapdata;
        this.mContext=mContext;
    }
    public void refresh(HashMap<String, Object> hashMapdata,  ArrayList<String> arrHeader)
    {
        this.hashMapdata = hashMapdata;
        this.arrHeader = arrHeader;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        if(hashMapdata!=null)
        return hashMapdata.size();
        else
            return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((Object[])hashMapdata.get(arrHeader.get(groupPosition))).length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if(hashMapdata!=null)
        return hashMapdata.get(groupPosition);
        else
            return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.hashMapdata.get(this.arrHeader.get(groupPosition));
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = arrHeader.get(groupPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.payslipheader, null);
                TextView tv = (TextView) convertView.findViewById(R.id.tv_header);
//                ExpandableListView eLV = (ExpandableListView) parent;
//                eLV.expandGroup(groupPosition);
                tv.setText(headerTitle);
            }
            return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            if(arrHeader.get(groupPosition).equalsIgnoreCase("EmpEarnings")) {
                EmpEarnings emp= ((EmpEarnings[]) hashMapdata.get(arrHeader.get(groupPosition)))[childPosition];
                LayoutInflater infalInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.emplyeearning, null);
                LinearLayout ll_backgrnd = (LinearLayout) convertView.findViewById(R.id.ll_backgrnd);
                TextView tv_basic= (TextView) convertView.findViewById(R.id.tv_basic);
                TextView earning= (TextView) convertView.findViewById(R.id.tv_earning);
                TextView tv_amnt= (TextView) convertView.findViewById(R.id.tv_amnt);
                tv_basic.setText(""+emp.getWagetype());
                earning.setText(""+emp.getWage());
                tv_amnt.setText(""+emp.getAmount());
                if((hashMapdata.size()-1)==childPosition)
                {
                    ll_backgrnd.setBackground(mContext.getResources().getDrawable(R.drawable.boxwithbelowround));
                }
            }
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}