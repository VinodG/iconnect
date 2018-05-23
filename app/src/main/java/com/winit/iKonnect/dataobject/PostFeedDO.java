package com.winit.iKonnect.dataobject;

/**
 * Created by Girish Velivela on 5/26/2017.
 */

public class PostFeedDO extends BaseDO {

    private String staffNumber;
    private CategoryFilterDO filters;
    private int offset;
    private int favourite;

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public CategoryFilterDO getFilters() {
        return filters;
    }

    public void setFilters(CategoryFilterDO filters) {
        this.filters = filters;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

}
