package com.winit.iKonnect.dataobject;

import com.winit.common.util.CalendarUtil;
import com.winit.common.util.StringUtils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

/**
 * Created by Rahul.Yadav on 5/23/2017.
 */

public class ServiceRequestDO extends BaseDO {

    /**
     * id : 91
     * requestby : 86262
     * formid : 2
     * orgunit :
     * hrStaffNumber : 86262
     * status : Pending
     * requestReason : sadf
     * hrComments :
     * empComments :
     * requestTime : 19/05/2017 12:15
     * ackTime :
     * completedTime :
     * servicerequestlogModels : null
     */

    private int id;
    private String requestby;
    private int formid;
    private String orgunit;
    private String hrStaffNumber;
    private String status;
    private String statusToCheck;
    private String requestReason;
    private String hrComments;
    private String empComments;
    private String requestTime;
    private String ackTime;
    private String completedTime;
    private Object servicerequestlogModels;

    private ArrayList<ServicerequestassignmentModelsDO> servicerequestassignmentModels;
    //    public Object servicerequestassignmentModels;
    private String aliasStatus = "";
    private String finalLevel = "";

    public static final String FINAL_LEVEL = "true";
    public static final String IS_COMPLETED = "true";
    public static final String LEVEL = "1";

    public Vector<String> imagePath = new Vector<String>();
    public static final String PENDING = "Pending";

    public static final String CLOSED = "Closed";
    public static final String APPROVED = "Approved";
    public static final String REJECTED = "Rejected";
    public static final String ACTIVE = "Active";
    public static final String HOLD = "On Hold";
    public static final String Work_in_progress = "Work in progress";
    public static final String READY_TO_COLLECT = "Ready to collect from your hr representative";
    public static final String READY_TO_COLLECT_FROM_HR = "Approved and ready to collect from your HR representative";
    public static final String APPROVED_TO_COLLECT = "Approved and will be send to your location";
    private String datePartDisplay = "";
    private String datePartMonth = "";
    private String datePartTime = "";

    /************************** Newly added for History *************************************************/

    private String modifiedTime="";
    private String createdBy="";
    private String modifiedBy="";
    private String createdOn="";
    private String modifiedOn="";
    private String MappingHR="";
    private String DelegateHR="";
    private String StatusLevel="";
    private String DoneBy="";
    private String NoLevels="";
    private String AssignedBy="";


    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    /************************** Newly added for History *************************************************/


    public void setServicerequestassignmentModels(ArrayList<ServicerequestassignmentModelsDO> servicerequestassignmentModels) {
        this.servicerequestassignmentModels = servicerequestassignmentModels;
    }

    public ArrayList<ServicerequestassignmentModelsDO> getServicerequestassignmentModels() {
        return servicerequestassignmentModels;
    }

    public String getAliasStatus() {
        return aliasStatus;
    }

    public void setAliasStatus(String aliasStatus) {
        this.aliasStatus = aliasStatus;
    }

    public String getFinalLevel() {
        return finalLevel;
    }

    public void setFinalLevel(String finalLevel) {
        this.finalLevel = finalLevel;
    }

    public String getStatusToCheck() {
        return statusToCheck;
    }

    public void setStatusToCheck(String statusToCheck) {
        this.statusToCheck = statusToCheck;
    }

    public int getId() {
        return id;
    }

    public String getDatePartDisplay() {
        return CalendarUtil.getDate(requestTime, CalendarUtil.DD_MMM_YYYY_HHMM_PATTERN, "dd", Locale.getDefault(), "");
    }

    public void setDatePartDisplay(String datePartDisplay) {
        this.datePartDisplay = datePartDisplay;
    }

    public String getDatePartMonth() {
        String[] arrtmp = getRequestTime().split("/");
        if (arrtmp != null && arrtmp.length > 2)
            return CalendarUtil.getMonthFromNumber(StringUtils.getInt(arrtmp[1]));
        else
            return "";
    }

    public void setDatePartMonth(String datePartMonth) {
        this.datePartMonth = datePartMonth;
    }

    public String getDatePartTime() {
        return CalendarUtil.getDate(requestTime, CalendarUtil.DD_MMM_YYYY_HHMM_PATTERN, CalendarUtil.yyyy_HH_mm_PATTERN1, Locale.getDefault(), "") + "  ";
    }

    public void setDatePartTime(String datePartTime) {
        this.datePartTime = datePartTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequestby() {
        return requestby;
    }

    public void setRequestby(String requestby) {
        this.requestby = requestby;
    }

    public int getFormid() {
        return formid;
    }

    public void setFormid(int formid) {
        this.formid = formid;
    }

    public String getOrgunit() {
        return orgunit;
    }

    public void setOrgunit(String orgunit) {
        this.orgunit = orgunit;
    }

    public String getHrStaffNumber() {
        return hrStaffNumber;
    }

    public void setHrStaffNumber(String hrStaffNumber) {
        this.hrStaffNumber = hrStaffNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestReason() {
        return requestReason;
    }

    public void setRequestReason(String requestReason) {
        this.requestReason = requestReason;
    }

    public String getHrComments() {
        return hrComments;
    }

    public void setHrComments(String hrComments) {
        this.hrComments = hrComments;
    }

    public String getEmpComments() {
        return empComments;
    }

    public void setEmpComments(String empComments) {
        this.empComments = empComments;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getAckTime() {
        return ackTime;
    }

    public void setAckTime(String ackTime) {
        this.ackTime = ackTime;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public Object getServicerequestlogModels() {
        return servicerequestlogModels;
    }

    public void setServicerequestlogModels(Object servicerequestlogModels) {
        this.servicerequestlogModels = servicerequestlogModels;
    }
}
