package com.winit.iKonnect.parser;

import com.google.gson.GsonBuilder;
import com.winit.iKonnect.ChatMemberResponce;

/**
 * Created by Rohitmanohar on 12-07-2017.
 */

public class EmployeeFilterParsor extends BaseJsonHandler
{
    private ChatMemberResponce chatMemberDo;
    @Override
    public Object getData()
    {
        if(chatMemberDo!=null)
          return chatMemberDo;
        else
            return null;
    }

    @Override
    public void parse(StringBuilder response)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        chatMemberDo = gsonBuilder.create().fromJson(response.toString(), ChatMemberResponce.class);
    }
}
