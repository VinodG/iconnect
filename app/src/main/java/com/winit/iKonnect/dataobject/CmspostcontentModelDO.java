package com.winit.iKonnect.dataobject;

import com.winit.common.util.StringUtils;

import java.util.List;

/**
 * Created by Girish Velivela on 5/16/2017.
 */

public class CmspostcontentModelDO extends BaseDO {

    /**
     * id : 25
     * cmspostId : 26
     * type : Url
     * url : https://www.icicibank.com/offers/vodafone-offer.page
     * contentEn :
     * contentAr :
     * urlAr :
     * cmspostcontentfileModels : null
     */

    private int id;
    private int cmspostId;
    private String type;
    private String url;
    private String contentEn;
    private String contentAr;
    private String urlAr;
    private List<CmspostcontentfileModels> cmspostcontentfileModels;

    public final static String URL          = "Url";
    public final static String CONTENT      = "Content";
    public final static String FILE         = "File";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCmspostId() {
        return cmspostId;
    }

    public void setCmspostId(int cmspostId) {
        this.cmspostId = cmspostId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        if(StringUtils.isEmpty(url))
            return getUrlAr();
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentEn() {
        if(isArabic)
            return getContentAr();
        return contentEn;
    }

    public void setContentEn(String contentEn) {
        this.contentEn = contentEn;
    }

    public String getContentAr() {
        if(StringUtils.isEmpty(contentAr))
            return contentEn;
        return contentAr;
    }

    public void setContentAr(String contentAr) {
        this.contentAr = contentAr;
    }

    public String getUrlAr() {
        if(StringUtils.isEmpty(urlAr))
            return url;
        return urlAr;
    }

    public void setUrlAr(String urlAr) {
        this.urlAr = urlAr;
    }

    public List<CmspostcontentfileModels> getCmspostcontentfileModels() {
        return cmspostcontentfileModels;
    }

    public void setCmspostcontentfileModels(List<CmspostcontentfileModels> cmspostcontentfileModels) {
        this.cmspostcontentfileModels = cmspostcontentfileModels;
    }

}
