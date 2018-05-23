package com.winit.iKonnect.dataobject.response;

/**
 * Created by Rahul.Yadav on 6/20/2017.
 */

public class Result {
    private EmpPaySlip[] EmpPaySlip;
    private EmpAbsenceDetails[] EmpAbsenceDetails;
    private EmpDeductions[] EmpDeductions;
    private EmpEarnings[] EmpEarnings;
    private EmpContributions[] EmpContributions;

    public EmpAbsenceDetails[] getEmpAbsenceDetails() {
        return EmpAbsenceDetails;
    }

    public void setEmpAbsenceDetails(EmpAbsenceDetails[] empAbsenceDetails) {
        this.EmpAbsenceDetails = empAbsenceDetails;
    }

    public EmpPaySlip[] getEmpPaySlip ()
    {
        return EmpPaySlip;
    }

    public void setEmpPaySlip (EmpPaySlip[] EmpPaySlip)
    {
        this.EmpPaySlip = EmpPaySlip;
    }

    public EmpDeductions[] getEmpDeductions ()
    {
        return EmpDeductions;
    }

    public void setEmpDeductions (EmpDeductions[] EmpDeductions)
    {
        this.EmpDeductions = EmpDeductions;
    }

    public EmpEarnings[] getEmpEarnings ()
    {
        return EmpEarnings;
    }

    public void setEmpEarnings (EmpEarnings[] EmpEarnings)
    {
        this.EmpEarnings = EmpEarnings;
    }

    public EmpContributions[] getEmpContributions ()
    {
        return EmpContributions;
    }

    public void setEmpContributions (EmpContributions[] EmpContributions)
    {
        this.EmpContributions = EmpContributions;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [EmpPaySlip = "+EmpPaySlip+", EmpAbsenceDetails = "+EmpAbsenceDetails+", EmpDeductions = "+EmpDeductions+", EmpEarnings = "+EmpEarnings+", EmpContributions = "+EmpContributions+"]";
    }
}
