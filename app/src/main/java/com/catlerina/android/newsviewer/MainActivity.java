package com.catlerina.android.newsviewer;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.catlerina.android.newsviewer.fragments.ArticleFragment;
import com.catlerina.android.newsviewer.fragments.NewsListFragment;
import com.catlerina.android.newsviewer.interfaces.OnNewsItemClickListener;
import com.catlerina.android.newsviewer.model.Article;

public class MainActivity extends AppCompatActivity implements OnNewsItemClickListener {

    private FragmentManager fragmentManager;
    private NewsListFragment newsListFragment;
    private ArticleFragment articleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterdetail);

        fragmentManager = getSupportFragmentManager();
        newsListFragment = NewsListFragment.newInstance();
        if(fragmentManager.findFragmentById(R.id.ll_fragment_container)==null){
            fragmentManager.beginTransaction()
                    .add(R.id.ll_fragment_container, newsListFragment)
                    .commit();
        }


    }

    @Override
    public void onItemClick(View view, int position, Article a) {
        String url = a.getArticleLink().toString();
        if (findViewById(R.id.detailed_fragment_container) == null) {//it is phone
            articleFragment = ArticleFragment.newInstance(url);
            fragmentManager.beginTransaction()
                    .replace(R.id.ll_fragment_container, articleFragment)
                    .addToBackStack(null)
                    .commit();
        } else { // it is tablet
            if (articleFragment == null) {
                articleFragment = ArticleFragment.newInstance(url);
                fragmentManager.beginTransaction()
                        .add(R.id.detailed_fragment_container, articleFragment)
                        .commit();
            } else
                articleFragment.loadUrl(url);

        }
    }
}
