package com.winit.iKonnect.dataobject.response;

/**
 * Created by Ashoka.Reddy on 5/29/2017.
 */

public class ThoughtOfTheDayResponse extends BaseResponse {
    private ThoughtOfthedayDetail quoteModel;


    public ThoughtOfthedayDetail getQuoteModel() {
        return quoteModel;
    }

    public void setQuoteModel(ThoughtOfthedayDetail quoteModel) {
        this.quoteModel = quoteModel;
    }
}
