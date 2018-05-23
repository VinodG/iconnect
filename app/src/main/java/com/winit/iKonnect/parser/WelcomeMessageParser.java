package com.winit.iKonnect.parser;

import com.winit.iKonnect.dataobject.WelcomeMessageDO;

import org.json.JSONObject;

/**
 * Created by Rohitmanohar on 12-07-2017.
 */

public class WelcomeMessageParser extends BaseJsonHandler
{
    private WelcomeMessageDO welcomeMessageDO;
    @Override
    public Object getData()
    {
        return welcomeMessageDO;
    }

    @Override
    public void parse(StringBuilder response)
    {
        try {
            JSONObject jsonmain = new JSONObject(response.toString());
            welcomeMessageDO = new WelcomeMessageDO();
            JSONObject jsonMsg = jsonmain.getJSONObject("welcomemsgModel");
            welcomeMessageDO.msg=jsonMsg.getString("msg");
            welcomeMessageDO.msgAr=jsonMsg.getString("msgAr");
            welcomeMessageDO.statusAr=jsonmain.getString("statusAr");
            welcomeMessageDO.statusEn=jsonmain.getString("statusEn");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
