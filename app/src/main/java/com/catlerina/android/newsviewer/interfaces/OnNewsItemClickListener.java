package com.catlerina.android.newsviewer.interfaces;

import android.view.View;

import com.catlerina.android.newsviewer.model.Article;

public interface OnNewsItemClickListener {
    void onItemClick(View view, int position, Article a);
}
