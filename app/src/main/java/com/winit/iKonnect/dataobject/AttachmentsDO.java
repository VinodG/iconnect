package com.winit.iKonnect.dataobject;

/**
 * Created by Sreekanth.P on 23-03-2018.
 */

public class AttachmentsDO extends BaseDO{

    public String FileName="";
    public String FilePath="";

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }
}
