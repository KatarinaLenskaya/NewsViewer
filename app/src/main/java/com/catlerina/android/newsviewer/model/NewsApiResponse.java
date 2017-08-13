package com.catlerina.android.newsviewer.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewsApiResponse {

    @SerializedName("articles")
    private List<Article> articles;

    public NewsApiResponse(){
        articles = new ArrayList<>();
    }

    public List<Article> getArticles() {
        generateId();
        return articles;
    }

    private void generateId(){
        for (Article a : articles) {
            a.setId();
        }
    }
}
