package com.winit.iKonnect.dataobject;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class ChangeofSalaryBankAccountDO extends FormRequestDO{
    private String NameofBank;
    private String Branch;
    private String ACNumber;
    private String IBAN;
    private String SwiftCode;
    private String bankAddress;

    public String getACNumber() {
        return ACNumber;
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getSwiftCode() {
        return SwiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        SwiftCode = swiftCode;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public void setACNumber(String ACNumber) {
        this.ACNumber = ACNumber;
    }



    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }



    public String getNameofBank() {
        return NameofBank;
    }

    public void setNameofBank(String nameofBank) {
        NameofBank = nameofBank;
    }
}
