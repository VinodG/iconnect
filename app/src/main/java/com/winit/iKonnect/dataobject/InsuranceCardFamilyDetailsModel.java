package com.winit.iKonnect.dataobject;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class InsuranceCardFamilyDetailsModel extends FormRequestDO{

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

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public String Name="";
    public String RelationShip="";
    public String DOB="";
    public String IDNumber="";
}
