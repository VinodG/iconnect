package com.winit.iKonnect.dataobject;

import android.databinding.Bindable;

import com.winit.common.util.StringUtils;

/**
 * Created by Girish Velivela on 5/8/2017.
 */

public class FeedsDO extends BaseDO{

    /**
     * id : 26
     * categoryId : 9
     * subCategoryId : 25
     * subSubCategoryId : 0
     * titleEn : Vodafone U Offer
     * titleAr :
     * subtitleEn : Vodafone U Offer
     * subtitleAr :
     * coverPictureEnUrl : dining-room-cabinets-dining-room-cabinet-ideas-an-ideabook-navyagmom-painting.jpg
     * coverPictureArUrl :
     * createdOn : 2017-05-13T19:26:02.497
     * cmspostcontentModel : {"id":25,"cmspostId":26,"type":"Url","url":"https://www.icicibank.com/offers/vodafone-offer.page","contentEn":"","contentAr":"","urlAr":"","cmspostcontentfileModels":null}
     * cmspostsettingModel : {"id":24,"cmspostId":26,"hasSharing":true,"hasComments":true,"hasLike":true,"isPinned":true,"pinUpto":"2017-05-20T00:00:00"}
     * cmspoststatModel : {"id":9,"cmspostId":26,"noOfSharing":6,"noOfComments":11,"noOfLikes":1,"noOfViews":40}
     * cmspostuserModel : {"id":16,"cmspostId":26,"staffNumber":"86262","availableTime":"0001-01-01T00:00:00","readTime":"2017-05-13T21:44:17.203","hasLiked":false,"hasCommented":true,"comment":"","commentTime":"2017-05-14T12:28:14.263","hasShared":true,"shareTime":"2017-05-14T22:31:29.723","staffName":"FARIS FOUAD KHAYYAT","likeTime":"2017-05-14T19:01:11.033","hasFavorite":true,"favoriteTime":"2017-05-14T22:56:42.577"}
     */

    private int id;
    private int categoryId;
    private int subCategoryId;
    private int subSubCategoryId;
    private String titleEn;
    private String titleAr;
    private String subtitleEn;
    private String subtitleAr;
    private String coverPictureEnUrl;
    private String coverPictureArUrl;

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    private String modifiedOn;
    private CmspostcontentModelDO cmspostcontentModel;
    private CmspostsettingModelDO cmspostsettingModel;
    private CmspoststatModelDO cmspoststatModel;
    private CmspostuserModelDO cmspostuserModel;

    public static final int NON_FAVOURITE = 0;
    public static final int FAVOURITE = 1;
    public static final int FROM_NOTIFICATION = 2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public int getSubSubCategoryId() {
        return subSubCategoryId;
    }

    public void setSubSubCategoryId(int subSubCategoryId) {
        this.subSubCategoryId = subSubCategoryId;
    }

    public String getTitleEn() {
        if(isArabic)
            return getTitleAr();
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleAr() {
        if(StringUtils.isEmpty(titleAr))
            return titleEn;
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getSubtitleEn() {
        if(isArabic)
            return getSubtitleAr();
        return subtitleEn;
    }

    public void setSubtitleEn(String subtitleEn) {
        this.subtitleEn = subtitleEn;
    }

    public String getSubtitleAr()
    {
        if(StringUtils.isEmpty(subtitleAr))
            return subtitleEn;
        return subtitleAr;
    }

    public void setSubtitleAr(String subtitleAr) {
        this.subtitleAr = subtitleAr;
    }

    public String getCoverPictureEnUrl() {
        if(isArabic)
            return getCoverPictureArUrl();
        return coverPictureEnUrl;
    }

    public void setCoverPictureEnUrl(String coverPictureEnUrl) {
        this.coverPictureEnUrl = coverPictureEnUrl;
    }

    public String getCoverPictureArUrl()    {
        if(StringUtils.isEmpty(coverPictureArUrl))
            return coverPictureEnUrl;
        return coverPictureArUrl;
    }

    public void setCoverPictureArUrl(String coverPictureArUrl) {
        this.coverPictureArUrl = coverPictureArUrl;
    }

    public String getCreatedOn() {
        return modifiedOn;
    }

    public void setCreatedOn(String createdOn) {
        this.modifiedOn = createdOn;
    }

    public CmspostcontentModelDO getCmspostcontentModel() {
        return cmspostcontentModel;
    }

    public void setCmspostcontentModel(CmspostcontentModelDO cmspostcontentModel) {
        this.cmspostcontentModel = cmspostcontentModel;
    }

    public CmspostsettingModelDO getCmspostsettingModel() {
        return cmspostsettingModel;
    }

    public void setCmspostsettingModel(CmspostsettingModelDO cmspostsettingModel) {
        this.cmspostsettingModel = cmspostsettingModel;
    }

    @Bindable
    public CmspoststatModelDO getCmspoststatModel() {
        return cmspoststatModel;
    }

    public void setCmspoststatModel(CmspoststatModelDO cmspoststatModel) {
        this.cmspoststatModel = cmspoststatModel;
    }

    @Bindable
    public CmspostuserModelDO getCmspostuserModel() {
        return cmspostuserModel;
    }

    public void setCmspostuserModel(CmspostuserModelDO cmspostuserModel) {
        this.cmspostuserModel = cmspostuserModel;
    }

}
