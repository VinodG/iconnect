package com.winit.iKonnect.dataobject;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class BankAccountUpdateDO extends FormRequestDO{
    private String NameAsPerBankRecord ="";
    private String NameofBank= "";
    private String Branch ="";
    private String BankAddress ="";
    private String ACNumber ="";
    private String IBAN  ="";
    private String SwiftCode ="";
    private String BankSortCode="";
    private String Currency ="";

    public String getNameAsPerBankRecord() {
        return NameAsPerBankRecord;
    }

    public void setNameAsPerBankRecord(String nameAsPerBankRecord) {
        NameAsPerBankRecord = nameAsPerBankRecord;
    }

    public String getNameofBank() {
        return NameofBank;
    }

    public void setNameofBank(String nameofBank) {
        NameofBank = nameofBank;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getBankAddress() {
        return BankAddress;
    }

    public void setBankAddress(String bankAddress) {
        BankAddress = bankAddress;
    }

    public String getACNumber() {
        return ACNumber;
    }

    public void setACNumber(String ACNumber) {
        this.ACNumber = ACNumber;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getSwiftCode() {
        return SwiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        SwiftCode = swiftCode;
    }

    public String getBankSortCode() {
        return BankSortCode;
    }

    public void setBankSortCode(String bankSortCode) {
        BankSortCode = bankSortCode;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }


}
