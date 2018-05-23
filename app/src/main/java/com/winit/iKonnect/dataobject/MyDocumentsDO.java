package com.winit.iKonnect.dataobject;

/**
 * Created by Sreekanth.P on 06-12-2017.
 */

public class MyDocumentsDO extends BaseDO{

    public String StaffNumber ="";
    public String DocPath ="";
    public String DocName ="";
    public String Type ="";
    public String FolderPath ="";
    public String UploadDate ="";

    public String getStaffNumber() {
        return StaffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        StaffNumber = staffNumber;
    }

    public String getDocPath() {
        return DocPath;
    }

    public void setDocPath(String docPath) {
        DocPath = docPath;
    }

    public String getDocName() {
        return DocName;
    }

    public void setDocName(String docName) {
        DocName = docName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getFolderPath() {
        return FolderPath;
    }

    public void setFolderPath(String folderPath) {
        FolderPath = folderPath;
    }

    public String getUploadDate() {
        return UploadDate;
    }

    public void setUploadDate(String uploadDate) {
        UploadDate = uploadDate;
    }
}
