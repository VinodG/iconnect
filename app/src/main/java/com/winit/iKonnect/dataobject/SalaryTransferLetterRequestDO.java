package com.winit.iKonnect.dataobject;

import java.util.ArrayList;

/**
 * Created by Sreekanth.P on 31-10-2017.
 */

public class SalaryTransferLetterRequestDO extends FormRequestDO {

    public String CurrentsalaryAccountBankName = "";
    public String CurrentsalaryIBAN = "";
    public String SalaryTransfersameBank = "";
    public String BankName = "";
    public String IBANNumber = "";
    public String DoyouhaveanyexistingBankloans = "";
    public String ExistingBankName = "";
    public String TotalLoanTaken = "";
    public String LoanAppliedon = "";
    public String OutstandingasofDate = "";
    public String Lastinstallmentdue = "";
    public String Monthlyinstallment = "";
    public String PurposeofLoan = "";
    public String proposedBankName = "";
    public String TotalLoanAmreq = "";
    public String MonthlyInst_50 = "";
    public String creditBankName = "";
    public String OutstandingAmount = "";
    public String dec_confirm = "";
    public String dec_approval = "";
    public String dec_request = "";
    public String BasicSalary = "";
    public String HousingAllowance = "";
    public String TransportAllowance = "";
    public String CurIndeAmount = "";
    public String DedAmt = "";
    private String AccountNumber = "";


    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getCurrentsalaryAccountBankName() {
        return CurrentsalaryAccountBankName;
    }

    public void setCurrentsalaryAccountBankName(String currentsalaryAccountBankName) {
        CurrentsalaryAccountBankName = currentsalaryAccountBankName;
    }

    public String getCurrentsalaryIBAN() {
        return CurrentsalaryIBAN;
    }

    public void setCurrentsalaryIBAN(String currentsalaryIBAN) {
        CurrentsalaryIBAN = currentsalaryIBAN;
    }

    public String getSalaryTransfersameBank() {
        return SalaryTransfersameBank;
    }

    public void setSalaryTransfersameBank(String salaryTransfersameBank) {
        SalaryTransfersameBank = salaryTransfersameBank;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getIBANNumber() {
        return IBANNumber;
    }

    public void setIBANNumber(String IBANNumber) {
        this.IBANNumber = IBANNumber;
    }

    public String getDoyouhaveanyexistingBankloans() {
        return DoyouhaveanyexistingBankloans;
    }

    public void setDoyouhaveanyexistingBankloans(String doyouhaveanyexistingBankloans) {
        DoyouhaveanyexistingBankloans = doyouhaveanyexistingBankloans;
    }

    public String getExistingBankName() {
        return ExistingBankName;
    }

    public void setExistingBankName(String existingBankName) {
        ExistingBankName = existingBankName;
    }

    public String getTotalLoanTaken() {
        return TotalLoanTaken;
    }

    public void setTotalLoanTaken(String totalLoanTaken) {
        TotalLoanTaken = totalLoanTaken;
    }

    public String getLoanAppliedon() {
        return LoanAppliedon;
    }

    public void setLoanAppliedon(String loanAppliedon) {
        LoanAppliedon = loanAppliedon;
    }

    public String getOutstandingasofDate() {
        return OutstandingasofDate;
    }

    public void setOutstandingasofDate(String outstandingasofDate) {
        OutstandingasofDate = outstandingasofDate;
    }

    public String getLastinstallmentdue() {
        return Lastinstallmentdue;
    }

    public void setLastinstallmentdue(String lastinstallmentdue) {
        Lastinstallmentdue = lastinstallmentdue;
    }

    public String getMonthlyinstallment() {
        return Monthlyinstallment;
    }

    public void setMonthlyinstallment(String monthlyinstallment) {
        Monthlyinstallment = monthlyinstallment;
    }

    public String getPurposeofLoan() {
        return PurposeofLoan;
    }

    public void setPurposeofLoan(String purposeofLoan) {
        PurposeofLoan = purposeofLoan;
    }

    public String getProposedBankName() {
        return proposedBankName;
    }

    public void setProposedBankName(String proposedBankName) {
        this.proposedBankName = proposedBankName;
    }

    public String getTotalLoanAmreq() {
        return TotalLoanAmreq;
    }

    public void setTotalLoanAmreq(String totalLoanAmreq) {
        TotalLoanAmreq = totalLoanAmreq;
    }

    public String getMonthlyInst_50() {
        return MonthlyInst_50;
    }

    public void setMonthlyInst_50(String monthlyInst_50) {
        MonthlyInst_50 = monthlyInst_50;
    }

    public String getCreditBankName() {
        return creditBankName;
    }

    public void setCreditBankName(String creditBankName) {
        this.creditBankName = creditBankName;
    }

    public String getOutstandingAmount() {
        return OutstandingAmount;
    }

    public void setOutstandingAmount(String outstandingAmount) {
        OutstandingAmount = outstandingAmount;
    }

    public String getDec_confirm() {
        return dec_confirm;
    }

    public void setDec_confirm(String dec_confirm) {
        this.dec_confirm = dec_confirm;
    }

    public String getDec_approval() {
        return dec_approval;
    }

    public void setDec_approval(String dec_approval) {
        this.dec_approval = dec_approval;
    }

    public String getDec_request() {
        return dec_request;
    }

    public void setDec_request(String dec_request) {
        this.dec_request = dec_request;
    }

    public String getBasicSalary() {
        return BasicSalary;
    }

    public void setBasicSalary(String basicSalary) {
        BasicSalary = basicSalary;
    }

    public String getHousingAllowance() {
        return HousingAllowance;
    }

    public void setHousingAllowance(String housingAllowance) {
        HousingAllowance = housingAllowance;
    }

    public String getTransportAllowance() {
        return TransportAllowance;
    }

    public void setTransportAllowance(String transportAllowance) {
        TransportAllowance = transportAllowance;
    }

    public String getCurIndeAmount() {
        return CurIndeAmount;
    }

    public void setCurIndeAmount(String curIndeAmount) {
        CurIndeAmount = curIndeAmount;
    }

    public String getDedAmt() {
        return DedAmt;
    }

    public void setDedAmt(String dedAmt) {
        DedAmt = dedAmt;
    }
}
