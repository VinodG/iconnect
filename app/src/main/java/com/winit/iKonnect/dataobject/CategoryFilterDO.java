package com.winit.iKonnect.dataobject;

import java.util.ArrayList;

/**
 * Created by Girish Velivela on 5/26/2017.
 */

public class CategoryFilterDO extends BaseDO{

    private ArrayList<Integer> CIds;
    private ArrayList<Integer> SCIds;
    private ArrayList<Integer> SSCIds;

    public ArrayList<Integer> getCIds() {
        return CIds;
    }

    public void setCIds(ArrayList<Integer> CIds) {
        this.CIds = CIds;
    }

    public ArrayList<Integer> getSCIds() {
        return SCIds;
    }

    public void setSCIds(ArrayList<Integer> SCIds) {
        this.SCIds = SCIds;
    }

    public ArrayList<Integer> getSSCIds() {
        return SSCIds;
    }

    public void setSSCIds(ArrayList<Integer> SSCIds) {
        this.SSCIds = SSCIds;
    }
}
