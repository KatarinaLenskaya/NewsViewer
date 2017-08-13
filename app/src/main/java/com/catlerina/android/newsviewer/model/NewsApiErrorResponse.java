package com.catlerina.android.newsviewer.model;

/**
 * Created by Катарина on 10.08.2017.
 */

public class NewsApiErrorResponse {

    private String message;
    private String documentation_url;

    public NewsApiErrorResponse (String message, String documentation_url) {
        this.message = message;
        this.documentation_url = documentation_url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDocumentation_url() {
        return documentation_url;
    }

    public void setDocumentation_url(String documentation_url) {
        this.documentation_url = documentation_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass()!= o.getClass())
            return false;
        NewsApiErrorResponse other = (NewsApiErrorResponse) o;
        if(message!=null)
            return message.equals(other.message);
        else
            return other.message==null;
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }
}
