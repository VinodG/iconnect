package com.winit.iKonnect.dataobject.response;

/**
 * Created by Rahul.Yadav on 6/20/2017.
 */

public class EmpDeductions {


    private String Amount;

    private String Units;

    private String WageType;

    private String Number;

    private String Ratro;

    private String Rate;

    public String getAmount ()
    {
        return Amount;
    }

    public void setAmount (String Amount)
    {
        this.Amount = Amount;
    }

    public String getUnits ()
    {
        return Units;
    }

    public void setUnits (String Units)
    {
        this.Units = Units;
    }

    public String getWageType ()
    {
        return WageType;
    }

    public void setWageType (String WageType)
    {
        this.WageType = WageType;
    }

    public String getNumber ()
    {
        return Number;
    }

    public void setNumber (String Number)
    {
        this.Number = Number;
    }

    public String getRatro ()
    {
        return Ratro;
    }

    public void setRatro (String Ratro)
    {
        this.Ratro = Ratro;
    }

    public String getRate ()
    {
        return Rate;
    }

    public void setRate (String Rate)
    {
        this.Rate = Rate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Amount = "+Amount+", Units = "+Units+", WageType = "+WageType+", Number = "+Number+", Ratro = "+Ratro+", Rate = "+Rate+"]";
    }
}
