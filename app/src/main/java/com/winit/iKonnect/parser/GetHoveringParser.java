package com.winit.iKonnect.parser;

import com.winit.iKonnect.dataobject.HoveringDo;

import org.json.JSONObject;

/**
 * Created by Rohitmanohar on 12-09-2017.
 */

public class GetHoveringParser extends BaseJsonHandler
{
    private HoveringDo hoveringDo;
    @Override
    public Object getData()
    {
        return hoveringDo;
    }

    @Override
    public void parse(StringBuilder response)
    {
        try
        {
            JSONObject jsonmain = new JSONObject(response.toString());
            JSONObject jsonObject = jsonmain.getJSONObject("Hovering");
            hoveringDo = new HoveringDo();
            hoveringDo.NotificationCount=jsonObject.getInt("NotificationCount");
            hoveringDo.PostCount=jsonObject.getInt("PostCount");
            hoveringDo.ServiceCount=jsonObject.getInt("ServiceCount");
//            hoveringDo = new HoveringDo();
//            hoveringDo.NotificationCount=1;
//            hoveringDo.PostCount=5;
//            hoveringDo.ServiceCount=9;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
