package com.harsh.newsapp2.api;

import android.util.Log;

import com.harsh.newsapp2.R;
import com.harsh.newsapp2.model.NewsResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.rxjava3.core.Single;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class APIClient {

    static APIInterface apiInterface;

    // Retrofit Instance
    public static APIInterface getApiInterface() {

        // yesterday's date String in yyyy-MM-dd format
        Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String yesterdayDate = formatter.format(yesterday);

        Log.d("DATE_TAG", yesterdayDate);

        if (apiInterface == null) {
            OkHttpClient.Builder client = new OkHttpClient.Builder();

            client.addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apiKey", "" + R.string.news_api_key)
                        .addQueryParameter("q", "Apple")
                        .addQueryParameter("from", "" + yesterdayDate)
                        .build();
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("" + R.string.base_url)
                    .client(client.build())
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();

            apiInterface = retrofit.create(APIInterface.class);
        }
        return apiInterface;
    }

    public interface APIInterface {
        @GET("everything")
        Single<NewsResponse> getNewsByPage(@Query("page") int page);

    }
}
