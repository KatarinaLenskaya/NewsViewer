package com.catlerina.android.newsviewer.network;

import com.catlerina.android.newsviewer.model.NewsApiErrorResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public abstract class ApiCallback<T> implements Callback<T> {

    public abstract void failure(NewsApiErrorResponse error);

    @Override
    public void failure(RetrofitError error) {
        NewsApiErrorResponse errorResp = null;

        try {
            errorResp = (NewsApiErrorResponse) error.getBodyAs(NewsApiErrorResponse.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        if (errorResp != null) {
            failure(errorResp);
        } else {
            Response errorResponse = error.getResponse();
            if (error.getKind() == RetrofitError.Kind.NETWORK) {
                failure(new NewsApiErrorResponse("Can't connect to network", "http://internet.com"));
            } else {
                failure(new NewsApiErrorResponse("Error code: " + String.valueOf(errorResponse.getStatus()), "http://internet.com"));
            }
        }
    }
}
