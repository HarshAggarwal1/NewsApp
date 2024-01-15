package com.harsh.newsapp2.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.harsh.newsapp2.model.News;
import com.harsh.newsapp2.paging.NewsPagingSource;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class NewsViewModel extends ViewModel {

    public Flowable<PagingData<News>> newsPagingDataFlowable;

    public NewsViewModel() {
        init();
    }

    private void init() {

        // Defining Paging Source
        NewsPagingSource newsPagingSource = new NewsPagingSource();
        Pager<Integer, News> pager = new Pager<>(
                new PagingConfig(
                        20,
                        20,
                        false,
                        20,
                        20 * 499
                ),
                () -> newsPagingSource
        );

        // Flowable
        newsPagingDataFlowable = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(newsPagingDataFlowable, coroutineScope);
    }
}
