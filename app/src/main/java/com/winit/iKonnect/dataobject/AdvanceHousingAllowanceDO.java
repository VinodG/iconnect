package com.winit.iKonnect.dataobject;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class AdvanceHousingAllowanceDO extends FormRequestDO{

    private String HAPreviouslyPaidUpto ="";
    private String AdvPayInmonth  ="";
    private String Period ="";
    private String From  ="";
    private String To ="";

    public String getHAPreviouslyPaidUpto() {
        return HAPreviouslyPaidUpto;
    }

    public void setHAPreviouslyPaidUpto(String HAPreviouslyPaidUpto) {
        this.HAPreviouslyPaidUpto = HAPreviouslyPaidUpto;
    }

    public String getAdvPayInmonth() {
        return AdvPayInmonth;
    }

    public void setAdvPayInmonth(String advPayInmonth) {
        AdvPayInmonth = advPayInmonth;
    }

    public String getPeriod() {
        return Period;
    }

    public void setPeriod(String period) {
        Period = period;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }
}
