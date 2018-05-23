package com.winit.iKonnect.dataobject;

import java.util.ArrayList;

/**
 * Created by namashivaya.gangishe on 5/18/2017.
 */

public class PayrollDO extends BaseDO {
    public String userName="";
    public String userId="";
    public String designation="";
    public String email="";
    public String mobNo="";
    public String nationality="";
    public String reasonForRequest="";
    public String bankName="";
    public String iqmaNo="";
    public ArrayList<String> attechedFilePath =new ArrayList<>();
    public ArrayList<RadioBTNDO> arrRedioBTNs =new ArrayList<>();

}
