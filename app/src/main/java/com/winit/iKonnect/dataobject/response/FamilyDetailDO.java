package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.BaseDO;

/**
 * Created by Rahul.Yadav on 5/23/2017.
 */

public class FamilyDetailDO extends BaseDO
{
   private String Name  ="";
   private String RelationShip="";
   private String DOB ="";
   private String Nationality ="";

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRelationShip() {
        return RelationShip;
    }

    public void setRelationShip(String relationShip) {
        RelationShip = relationShip;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }
}
