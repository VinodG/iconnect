package com.winit.iKonnect.dataobject;

/**
 * Created by Ojha.Sandeep on 10/31/2017.
 */

public class C3CardATMSwitchClaimDO extends FormRequestDO {

    public String CardNumber ="";
    public String CardmemberMobileNumber ="";
    public String TransactionDate ="";
    public String TransactionTime ="";
    public String AmountWithdrawn ="";
    public String AmountReceived ="";
    public String BankName ="";
    public String AmountDisputed ="";
    public String ATMLocation ="";
    public String AdditionalComments ="";
    public String Signature ="";

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getCardmemberMobileNumber() {
        return CardmemberMobileNumber;
    }

    public void setCardmemberMobileNumber(String cardmemberMobileNumber) {
        CardmemberMobileNumber = cardmemberMobileNumber;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        TransactionDate = transactionDate;
    }

    public String getTransactionTime() {
        return TransactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        TransactionTime = transactionTime;
    }

    public String getAmountWithdrawn() {
        return AmountWithdrawn;
    }

    public void setAmountWithdrawn(String amountWithdrawn) {
        AmountWithdrawn = amountWithdrawn;
    }

    public String getAmountReceived() {
        return AmountReceived;
    }

    public void setAmountReceived(String amountReceived) {
        AmountReceived = amountReceived;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getAmountDisputed() {
        return AmountDisputed;
    }

    public void setAmountDisputed(String amountDisputed) {
        AmountDisputed = amountDisputed;
    }

    public String getATMLocation() {
        return ATMLocation;
    }

    public void setATMLocation(String ATMLocation) {
        this.ATMLocation = ATMLocation;
    }

    public String getAdditionalComments() {
        return AdditionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        AdditionalComments = additionalComments;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }
}
