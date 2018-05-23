package com.winit.iKonnect.dataobject;

import com.winit.common.util.StringUtils;

/**
 * Created by Girish Velivela on 5/18/2017.
 */

public class CategoryDO extends BaseDO{

    /**
     * id : 1
     * level : 1
     * type : Category
     * name : Other
     * nameAr : -
     * parentId : 0
     * isCompulsory : true
     */

    private int id;
    private int level;
    private String type;
    private String name;
    private String nameAr;
    private int parentId;
    private boolean isCompulsory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        if(isArabic)
            return getNameAr();
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAr() {
        if(StringUtils.isEmpty(nameAr))
            return name;
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public boolean isIsCompulsory() {
        return isCompulsory;
    }

    public void setIsCompulsory(boolean isCompulsory) {
        this.isCompulsory = isCompulsory;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object b){
        if(b instanceof CategoryDO)
            return id == ((CategoryDO)b).id && level == ((CategoryDO)b).level;
        return false;
    }

}
