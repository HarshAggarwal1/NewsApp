package com.harsh.newsapp2.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.harsh.newsapp2.databinding.SingleNewsItemBinding;
import com.harsh.newsapp2.model.News;

public class NewsAdapter extends PagingDataAdapter<News, NewsAdapter.NewsViewHolder> {

    public static final int LOADING_ITEM = 0;
    public static final int MOVIE_ITEM = 0;

    RequestManager glide;

    public NewsAdapter(@NonNull DiffUtil.ItemCallback<News> diffCallback, RequestManager glide) {
        super(diffCallback);
        this.glide = glide;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(SingleNewsItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = getItem(position);
        if (news != null) {
            Log.d("URL", news.getUrlToImage());
            glide.load(news.getUrlToImage()).
                    into(holder.newsItemBinding.imageViewNews);
            holder.newsItemBinding.textViewTitle.setText(news.getTitle().substring(0, 6));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == getItemCount() ? LOADING_ITEM : MOVIE_ITEM;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        SingleNewsItemBinding newsItemBinding;
        public NewsViewHolder(@NonNull SingleNewsItemBinding newsItemBinding) {
            super(newsItemBinding.getRoot());
            this.newsItemBinding = newsItemBinding;
        }
    }
}
