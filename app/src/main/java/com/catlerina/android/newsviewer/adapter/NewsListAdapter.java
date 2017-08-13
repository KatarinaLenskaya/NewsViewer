package com.catlerina.android.newsviewer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.catlerina.android.newsviewer.R;
import com.catlerina.android.newsviewer.interfaces.OnNewsItemClickListener;
import com.catlerina.android.newsviewer.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private ArrayList<Article> articlesList;
    private Context context;
    private OnNewsItemClickListener listener;

    public NewsListAdapter(ArrayList<Article> articlesList, Context context, OnNewsItemClickListener listener) {
        this.articlesList = articlesList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_of_news_list, parent, false);
        final NewsListAdapter.ViewHolder holder = new NewsListAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = holder.getAdapterPosition();
                if (listener != null)
                    listener.onItemClick(v, index, articlesList.get(index));

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article a = articlesList.get(position);
        holder.articleTitle.setText(a.getTitle());
        holder.articleDescription.setText(a.getDescription());
        holder.articleTime.setText(a.getFormattedDate());
        holder.articleAuthor.setText(a.getAuthor());
        Picasso.with(context)
                .load(a.getImageLink())
                .error(R.drawable.news)
                .into(holder.articleImage);
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public void updateData(ArrayList<Article> articlesList) {
        this.articlesList = articlesList;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView articleImage;
        TextView articleTitle;
        TextView articleDescription;
        TextView articleTime;
        TextView articleAuthor;


        ViewHolder(View itemView) {
            super(itemView);
            articleImage = (ImageView) itemView.findViewById(R.id.iv_articles_image);
            articleTitle = (TextView) itemView.findViewById(R.id.tv_articles_title);
            articleDescription = (TextView) itemView.findViewById(R.id.tv_articles_description);
            articleTime = (TextView) itemView.findViewById(R.id.tv_article_time);
            articleAuthor = (TextView) itemView.findViewById(R.id.tv_article_author);
        }
    }

}
