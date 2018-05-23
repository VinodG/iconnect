package com.winit.iKonnect.dataobject;

/**
 * Created by Rahul.Yadav on 5/19/2017.
 */

public class FormRequestDO extends BaseDO {

    private long ServiceRequestId;
    private String StaffNumber;
    private String Status;
    private String PendingWith = "";
    private String RequestedDate;
    private String CompletedDate = "";
    private String ReasonforRequest;

    public long getServiceRequestId() {
        return ServiceRequestId;
    }

    public void setServiceRequestId(long serviceRequestId) {
        ServiceRequestId = serviceRequestId;
    }

    public String getStaffNumber() {
        return StaffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        StaffNumber = staffNumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPendingWith() {
        return PendingWith;
    }

    public void setPendingWith(String pendingWith) {
        PendingWith = pendingWith;
    }

    public String getRequestedDate() {
        return RequestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        RequestedDate = requestedDate;
    }

    public String getCompletedDate() {
        return CompletedDate;
    }

    public void setCompletedDate(String completedDate) {
        CompletedDate = completedDate;
    }

    public String getReasonforRequest() {
        return ReasonforRequest;
    }

    public void setReasonforRequest(String reasonforRequest) {
        ReasonforRequest = reasonforRequest;
    }

}
