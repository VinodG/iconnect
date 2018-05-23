package com.winit.iKonnect.dataobject.response;

import com.winit.iKonnect.dataobject.BaseDO;

/**
 * Created by Ashoka.Reddy on 5/29/2017.
 */

public class ThoughtOfthedayDetail extends BaseDO {
    private String quote="";
    private String author="";

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQuoteAr() {
        return quoteAr;
    }

    public void setQuoteAr(String quoteAr) {
        this.quoteAr = quoteAr;
    }

    public String getAuthorAr() {
        return authorAr;
    }

    public void setAuthorAr(String authorAr) {
        this.authorAr = authorAr;
    }

    private String quoteAr="";
    private String authorAr="";
}
