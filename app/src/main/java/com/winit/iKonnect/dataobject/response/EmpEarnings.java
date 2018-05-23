package com.winit.iKonnect.dataobject.response;

/**
 * Created by Rahul.Yadav on 6/20/2017.
 */

public class EmpEarnings{
    private String Amount;

    private String Units;

    private String Wagetype;

    private String Number;

    private String Wage;

    private String Ratro;

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

    public String getWagetype ()
    {
        return Wagetype;
    }

    public void setWagetype (String Wagetype)
    {
        this.Wagetype = Wagetype;
    }

    public String getNumber ()
    {
        return Number;
    }

    public void setNumber (String Number)
    {
        this.Number = Number;
    }

    public String getWage ()
    {
        return Wage;
    }

    public void setWage (String Wage)
    {
        this.Wage = Wage;
    }

    public String getRatro ()
    {
        return Ratro;
    }

    public void setRatro (String Ratro)
    {
        this.Ratro = Ratro;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Amount = "+Amount+", Units = "+Units+", Wage type = "+Wagetype+", Number = "+Number+", Wage = "+Wage+", Ratro = "+Ratro+"]";
    }}
