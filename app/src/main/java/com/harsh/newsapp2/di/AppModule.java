package com.harsh.newsapp2.di;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.harsh.newsapp2.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class AppModule {
    @Provides
    @Singleton
    public RequestManager getGlide(@ApplicationContext Context context) {
        return Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions()
                        .error(R.drawable.ic_launcher_background)
                        .placeholder(R.drawable.ic_launcher_background));
    }
}
