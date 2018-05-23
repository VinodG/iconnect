package com.winit.iKonnect.dataobject;

/**
 * Created by Sreekanth.P on 20-11-2017.
 */

public class ServicerequestassignmentModelsDO extends BaseDO {

    public String Id = "";
    public String ServiceRequestId = "";
    public String FormId = "";
    public String Level = "";
    public String Role = "";
    public String Status = "";
    public String Comments = "";
    public String AssignedTo = "";
    public String ActionTakenBY = "";
    public String IsCompleted = "";
    public String FinalLevel = "";
    public String CriteriaCode = "";
    public String ModifiedOn = "";
    public String CreatedOn = "";
    public String Remarks = "";
    public String AliasStatus = "";


    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getAliasStatus() {
        return AliasStatus;
    }

    public void setAliasStatus(String aliasStatus) {
        AliasStatus = aliasStatus;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getServiceRequestId() {
        return ServiceRequestId;
    }

    public void setServiceRequestId(String serviceRequestId) {
        ServiceRequestId = serviceRequestId;
    }

    public String getFormId() {
        return FormId;
    }

    public void setFormId(String formId) {
        FormId = formId;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getAssignedTo() {
        return AssignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        AssignedTo = assignedTo;
    }

    public String getActionTakenBY() {
        return ActionTakenBY;
    }

    public void setActionTakenBY(String actionTakenBY) {
        ActionTakenBY = actionTakenBY;
    }

    public String getIsCompleted() {
        return IsCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        IsCompleted = isCompleted;
    }

    public String getFinalLevel() {
        return FinalLevel;
    }

    public void setFinalLevel(String finalLevel) {
        FinalLevel = finalLevel;
    }

    public String getCriteriaCode() {
        return CriteriaCode;
    }

    public void setCriteriaCode(String criteriaCode) {
        CriteriaCode = criteriaCode;
    }

    public String getModifiedOn() {
        return ModifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        ModifiedOn = modifiedOn;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }
}
