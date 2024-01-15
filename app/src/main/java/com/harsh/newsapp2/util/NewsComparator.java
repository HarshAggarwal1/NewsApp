package com.harsh.newsapp2.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.harsh.newsapp2.model.News;

public class NewsComparator extends DiffUtil.ItemCallback<News> {

        @Override
        public boolean areItemsTheSame(@NonNull News oldItem, @NonNull News newItem) {
            return oldItem.getSource().getId().equals(newItem.getSource().getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull News oldItem, @NonNull News newItem) {
            return oldItem.getSource().getId().equals(newItem.getSource().getId());
        }
}
