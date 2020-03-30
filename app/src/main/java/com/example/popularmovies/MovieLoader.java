package com.example.popularmovies;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    private String mUrl;

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    public MovieLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Nullable
    @Override
    public List<Movie> loadInBackground() {
        DataHolder dataHolder = DataHolder.getInstance();
        if (mUrl == null) {
            return null;
        }
        else if (!dataHolder.movies.isEmpty()){
            return dataHolder.movies;
        }

        List<Movie> movies = JsonUtils.fetchMoviesData(mUrl);
        dataHolder.movies.addAll(movies);
        return movies;
    }
}









