package com.catlerina.android.newsviewer.model;

import android.net.Uri;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {

    private transient int id;

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("urlToImage")
    private Uri imageLink;

    @SerializedName("url")
    private Uri articleLink;

    @SerializedName("publishedAt")
    private Date publishedAt;


    public Article(String author, String title, String description, Uri imageLink, Uri articleLink, Date publishedAt) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
        this.articleLink = articleLink;
        this.publishedAt = publishedAt;
    }

    public Article(int id, String author, String title, String description, Uri imageLink, Uri articleLink, Date publishedAt) {
        this(author, title, description, imageLink, articleLink, publishedAt);
        this.id = id;
    }

    public void setId(){
        id = hashCode();
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Uri getImageLink() {
        return imageLink;
    }

    public Uri getArticleLink() {
        return articleLink;
    }

    public String getFormattedDate() {
        if (publishedAt != null) {
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm, dd.MM.yyyy");
            return outputFormat.format(publishedAt);
        } else return "";
    }

    public Date getDate(){
        return publishedAt;
    }


    public int getId(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (id != article.id) return false;
        if (author != null ? !author.equals(article.author) : article.author != null) return false;
        if (!title.equals(article.title)) return false;
        if (description != null ? !description.equals(article.description) : article.description != null)
            return false;
        if (!imageLink.equals(article.imageLink)) return false;
        if (!articleLink.equals(article.articleLink)) return false;
        return publishedAt != null ? publishedAt.equals(article.publishedAt) : article.publishedAt == null;

    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }


}
