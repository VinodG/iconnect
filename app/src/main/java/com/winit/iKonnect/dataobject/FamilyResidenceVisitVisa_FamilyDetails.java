package com.winit.iKonnect.dataobject;

/**
 * Created by Rohitmanohar on 23-05-2017.
 */

public class FamilyResidenceVisitVisa_FamilyDetails extends FormRequestDO {

    private String Name="";
    public String Relationship="";
    public String Dob="";
    public String Nationality="";

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRelationship() {
        return Relationship;
    }

    public void setRelationship(String relationship) {
        Relationship = relationship;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }
}
