package com.winit.iKonnect.dataobject;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class PayrollCardDO extends FormRequestDO{
    private String NameofBank;
    private String IqamaNo ="";
    private String TypeOfRequest ="";

    public String getIqamaNo() {
        return IqamaNo;
    }

    public void setIqamaNo(String iqamaNo) {
        this.IqamaNo = iqamaNo;
    }



    public String getNameofBank() {
        return NameofBank;
    }

    public void setNameofBank(String nameofBank) {
        NameofBank = nameofBank;
    }

    public String getTypeOfRequest() {
        return TypeOfRequest;
    }

    public void setTypeOfRequest(String typeOfRequest) {
        TypeOfRequest = typeOfRequest;
    }
}
