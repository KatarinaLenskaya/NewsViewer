package com.catlerina.android.newsviewer.network;

import android.net.Uri;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Катарина on 10.08.2017.
 */

public class UriDeserializer implements JsonDeserializer<Uri> {

    @Override
    public Uri deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String urlString = json.getAsString();
        Uri uri = Uri.parse(urlString);
        return uri;
    }
}
