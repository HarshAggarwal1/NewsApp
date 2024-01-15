package com.harsh.newsapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.bumptech.glide.RequestManager;
import com.harsh.newsapp2.adapter.NewsAdapter;
import com.harsh.newsapp2.adapter.NewsLoadStateAdapter;
import com.harsh.newsapp2.databinding.ActivityMainBinding;
import com.harsh.newsapp2.util.GridSpace;
import com.harsh.newsapp2.util.NewsComparator;
import com.harsh.newsapp2.viewmodel.NewsViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    NewsViewModel mainActivityViewModel;
    ActivityMainBinding activityMainBinding;
    NewsAdapter newsAdapter;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        newsAdapter = new NewsAdapter(new NewsComparator(), requestManager);
        mainActivityViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        initRecyclerViewAdapter();

        // subscribe to paging
        Disposable subscribeResult = mainActivityViewModel.newsPagingDataFlowable.subscribe(newsPagingData -> {
                newsAdapter.submitData(getLifecycle(), newsPagingData);
                }
        );
    }

    private void initRecyclerViewAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        activityMainBinding.recyclerViewNews.setLayoutManager(gridLayoutManager);
        activityMainBinding.recyclerViewNews.addItemDecoration(new GridSpace(2, 20, true));
        activityMainBinding.recyclerViewNews.setAdapter(
                newsAdapter.withLoadStateFooter(
                        new NewsLoadStateAdapter(view -> {
                            newsAdapter.retry();
                        }
                        )
                )
        );
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return newsAdapter.getItemViewType(position) == NewsAdapter.LOADING_ITEM ? 1 : 2;
            }
        });
    }
}