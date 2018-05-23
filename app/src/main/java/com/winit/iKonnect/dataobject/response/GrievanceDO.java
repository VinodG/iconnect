package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.FormRequestDO;

/**
 * Created by Srikanth on 12-07-2017.
 */

public class GrievanceDO extends FormRequestDO{
    public String GrievanceAgainst = "";
    public String Location = "";

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getGrievanceAgainst() {
        return GrievanceAgainst;
    }

    public void setGrievanceAgainst(String grievanceAgainst) {
        GrievanceAgainst = grievanceAgainst;
    }
}
