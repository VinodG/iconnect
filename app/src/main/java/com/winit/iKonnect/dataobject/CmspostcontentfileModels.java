package com.winit.iKonnect.dataobject;

/**
 * Created by Girish Velivela on 5/22/2017.
 */

public class CmspostcontentfileModels extends BaseDO {

    /**
     * id : 157
     * cmspostcontentId : 105
     * fileName : Highlights__Full_Year_2016_-_EN.pdf
     * fileUrl : Highlights__Full_Year_2016_-_EN.pdf
     */

    private int id;
    private int cmspostcontentId;
    private String fileName;
    private String fileUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCmspostcontentId() {
        return cmspostcontentId;
    }

    public void setCmspostcontentId(int cmspostcontentId) {
        this.cmspostcontentId = cmspostcontentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
