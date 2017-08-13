package com.catlerina.android.newsviewer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.catlerina.android.newsviewer.R;
import com.catlerina.android.newsviewer.adapter.NewsListAdapter;
import com.catlerina.android.newsviewer.interfaces.OnNewsItemClickListener;
import com.catlerina.android.newsviewer.model.Article;
import com.catlerina.android.newsviewer.model.NewsApiErrorResponse;
import com.catlerina.android.newsviewer.model.NewsApiResponse;
import com.catlerina.android.newsviewer.network.ApiCallback;
import com.catlerina.android.newsviewer.network.RestClient;
import com.catlerina.android.newsviewer.storage.Database;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NewsListFragment extends Fragment {

    private RecyclerView newsList;
    private NewsListAdapter adapter;
    private SwipeRefreshLayout reload;

    private Database database;
    private OnNewsItemClickListener listener;


    public static NewsListFragment newInstance(){
        return new NewsListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnNewsItemClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnNewsItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        newsList = (RecyclerView) view.findViewById(R.id.rv_news_list);
        database = new Database(getActivity());
        database.open();
        ArrayList<Article> news = database.getArticles();

        loadArticles();
        adapter= new NewsListAdapter(news, getActivity(), listener);
        newsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsList.setAdapter(adapter);

        //add dividers between recycler items
        DividerItemDecoration divider = new DividerItemDecoration(newsList.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_divider));
        newsList.addItemDecoration(divider);

        reload = (SwipeRefreshLayout) view.findViewById(R.id.srl_reload);
        reload.setColorSchemeResources(R.color.colorAccent);
        reload.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadArticles();
            }
        });
        return view;
    }

    private void loadArticles() {
        RestClient.getInstance().getService().getArticles(new ApiCallback<NewsApiResponse>() {
            @Override
            public void failure(NewsApiErrorResponse error) {
                Toast.makeText(getActivity(), error.getMessage() + "\n" + "Details:" + error.getDocumentation_url(), Toast.LENGTH_LONG).show();
                reload.setRefreshing(false);
            }

            @Override
            public void success(NewsApiResponse newsApiResponse, Response response) {
                database.insertArticles(newsApiResponse.getArticles());
                adapter.updateData(database.getArticles());
                reload.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
        database.close();
    }
}
