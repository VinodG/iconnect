package com.winit.iKonnect.dataobject;

import java.util.ArrayList;

/**
 * Created by Ojha.Sandeep on 10/28/2017.
 */

public class SalaryTransferRequestBankAccountDo extends FormRequestDO {

    public String StaffName ="";
    public String Department ="";
    public String BankName ="";
    public String AccountNo ="";
    public String IBAN ="";
    public String Signature ="";


    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }


    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

}
