package com.harsh.newsapp2.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.harsh.newsapp2.api.APIClient;
import com.harsh.newsapp2.model.News;
import com.harsh.newsapp2.model.NewsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewsPagingSource extends RxPagingSource<Integer, News>{
    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, News> pagingState) {
        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, News>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        try {
            int page = loadParams.getKey() == null ? 1 : loadParams.getKey();
            Log.d("PAGE_TAG", "" + page);
            return APIClient.getApiInterface()
                    .getNewsByPage(page)
                    .subscribeOn(Schedulers.io())
                    .map(NewsResponse::getArticles)
                    .map(articles -> toLoadResult(articles, page))
                    .onErrorReturn(LoadResult.Error::new);
        }
        catch (Exception e) {
            return Single.just(new LoadResult.Error<>(e));
        }
    }

    private LoadResult<Integer, News> toLoadResult(List<News> articles, int page) {
        return new LoadResult.Page<>(
                articles,
                page == 1 ? null : page - 1,
                page == 1 ? null : page + 1
        );
    }
}
