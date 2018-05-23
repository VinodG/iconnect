package com.winit.iKonnect.dataobject;

/**
 * Created by Ashoka.Reddy on 5/29/2017.
 */

    public class PlanningDO extends BaseDO {
        public int EventId;
        public String DateSchedule = "";
        public String Description = "";
        public String Subject = "";

    public int getEventId() {
        return EventId;
    }

    public void setEventId(int eventId) {
        EventId = eventId;
    }

    public String getDateSchedule() {
        return DateSchedule;
    }

    public void setDateSchedule(String dateSchedule) {
        DateSchedule = dateSchedule;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }
}
