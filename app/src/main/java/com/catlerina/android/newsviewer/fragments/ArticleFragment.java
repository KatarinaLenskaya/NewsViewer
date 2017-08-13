package com.catlerina.android.newsviewer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.catlerina.android.newsviewer.R;

public class ArticleFragment extends Fragment {

    private static final String URL_ARG = "uriRes";

    private WebView webArticle;
    private ProgressBar articleLoadProgress;

    public static ArticleFragment newInstance(String url){
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(URL_ARG, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_web, container, false);
        webArticle = (WebView) view.findViewById(R.id.wv_article);
        articleLoadProgress = (ProgressBar) view.findViewById(R.id.pb_article_page_load);
        articleLoadProgress.setMax(100);
        articleLoadProgress.setVisibility(View.VISIBLE);
        webArticle.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        webArticle.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
               if(newProgress == 100){
                   articleLoadProgress.setVisibility(View.GONE);
               } else {
                   articleLoadProgress.setVisibility(View.VISIBLE);
                   articleLoadProgress.setProgress(newProgress);
               }
            }
        });
        webArticle.loadUrl(getArguments().getString(URL_ARG));
        webArticle.getSettings().setJavaScriptEnabled(true);
        return view;
    }

    public void loadUrl(String url){
        webArticle.loadUrl(url);
    }


}
