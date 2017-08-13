package com.catlerina.android.newsviewer.network;

import com.catlerina.android.newsviewer.model.NewsApiResponse;

import retrofit.http.GET;

public interface ApiService {
    @GET("/articles?source=the-times-of-india&sortBy=latest&apiKey=954ffcb5d78d4393aed268974d3fd132")
    void getArticles(ApiCallback<NewsApiResponse> callback);
}
