package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.CalenderDetailDo;

import java.util.ArrayList;

/**
 * Created by Ashoka.Reddy on 5/29/2017.
 */

public class CalenderListResponse extends BaseResponse {

    private ArrayList<CalenderDetailDo> eventModels;

    public ArrayList<CalenderDetailDo> getEventModels() {
        return eventModels;
    }

    public void setEventModels(ArrayList<CalenderDetailDo> eventModels) {
        this.eventModels = eventModels;
    }

}
