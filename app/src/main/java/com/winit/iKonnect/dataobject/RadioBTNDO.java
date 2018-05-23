package com.winit.iKonnect.dataobject;


/**
 * Created by namashivaya.gangishe on 5/16/2017.
 */

public class RadioBTNDO extends BaseDO {
    public String headerName = "";
    public String[] options;
//    public String[] optionsAr;
    public String ans="";

    public String getOption(int option){
//        if(isArabic)
//            return optionsAr[option];
        return options[option];
    }

    public static final String LICENCE_RENEWAL          = "Licence Renewal";
    public static final String YES                      = "Yes";
    public static final String NO                       = "No";
    public static final String COMPANY                  = "Company";
    public static final String EMPLOY                   = "Employee";
    public static final String WITH_COC                 = "With CoC";
    public static final String WITHOUT_COC              = "Without CoC";
    public static final String BUSINESS                 = "Business";
    public static final String PERSONAL                 = "Personal";
    public static final String VISIT_VISA               = "Visit Visa";
    public static final String RESIDENCE_VISA            = "Residence Visa";
    public static final String WITHIN_KSA               = "Within KSA";
    public static final String OUTSIDE_KSA              = "Outside KSA";
    public static final String TWENTY_PER               = "20%";
    public static final String HUNDRED_PER              = "100%";
    public static final String Detailed_salary          = "Detailed salary";
    public static final String Monthly_amount           = "Monthly amount";

    public final static String NEW_LICENCE  = "New Licence";

}
