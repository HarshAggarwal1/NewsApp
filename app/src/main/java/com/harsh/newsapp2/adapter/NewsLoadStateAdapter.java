package com.harsh.newsapp2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.harsh.newsapp2.R;
import com.harsh.newsapp2.databinding.LoadStateItemBinding;

public class NewsLoadStateAdapter extends LoadStateAdapter<NewsLoadStateAdapter.LoadStateViewHolder> {

    private final View.OnClickListener retryCallback;

    public NewsLoadStateAdapter(View.OnClickListener retryCallback) {
        this.retryCallback = retryCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull LoadStateViewHolder loadStateViewHolder, @NonNull LoadState loadState) {
        loadStateViewHolder.bind(loadState);
    }

    @NonNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull LoadState loadState) {
        return new LoadStateViewHolder(viewGroup, retryCallback);
    }

    public static class LoadStateViewHolder extends RecyclerView.ViewHolder {
        private final ProgressBar progressBar;
        private final TextView textViewError;
        private final Button buttonRetry;
        public LoadStateViewHolder(@NonNull ViewGroup parent, @NonNull View.OnClickListener retryCallback) {
            super(LayoutInflater
                    .from(parent.getContext())
                    .inflate(
                            R.layout.load_state_item,
                            parent,
                            false
                    )
            );

            LoadStateItemBinding loadStateItemBinding = LoadStateItemBinding.bind(itemView);
            progressBar = loadStateItemBinding.progressBar;
            textViewError = loadStateItemBinding.errorMsg;
            buttonRetry = loadStateItemBinding.btnRetry;

            buttonRetry.setOnClickListener(retryCallback);
        }

        public void bind(LoadState loadState) {
            if (loadState instanceof LoadState.Error) {
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                textViewError.setText(loadStateError.getError().getLocalizedMessage());
            }

            progressBar.setVisibility(loadState instanceof LoadState.Loading ? View.VISIBLE : View.GONE);
            buttonRetry.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
            textViewError.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
        }
    }
}
