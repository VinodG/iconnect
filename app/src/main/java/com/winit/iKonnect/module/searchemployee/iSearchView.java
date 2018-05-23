package com.winit.iKonnect.module.searchemployee;

import com.winit.iKonnect.dataobject.ChatMemberDO;
import com.winit.iKonnect.module.base.IBaseView;

import java.util.ArrayList;

/**
 * Created by Ashoka.Reddy on 7/12/2017.
 */

public interface iSearchView extends IBaseView
{
    public void FetchedEmployeeData(ArrayList<ChatMemberDO> arrEmployee);
}
