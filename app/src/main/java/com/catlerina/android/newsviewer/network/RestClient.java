package com.catlerina.android.newsviewer.network;

import android.net.Uri;

import com.catlerina.android.newsviewer.model.Article;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClient {

    private static String API_URL="https://newsapi.org/v1";
    private static RestClient sClient = new RestClient();

    private RestAdapter restAdapter;
    private ApiService service;
    private Gson gson;

    private RestClient(){

        gson = new GsonBuilder()
                .registerTypeAdapter(Uri.class, new UriDeserializer())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .create();


        restAdapter = new RestAdapter.Builder()
                .setConverter( new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .build();

        service = restAdapter.create(ApiService.class);
    }

    public static RestClient getInstance(){
        return sClient;
    }

    public ApiService getService(){
        return service;
    }
}
