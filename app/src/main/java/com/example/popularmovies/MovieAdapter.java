package com.example.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, viewGroup, false);
        }

        Movie currentMovie = getItem(i);
        view.setTag(currentMovie);

        ImageView photoImageView = view.findViewById(R.id.movie_thumbnail_iv);
        String imagePath = currentMovie.getMainMoviePoster();
        Glide.with(photoImageView)
                .load(imagePath).centerCrop()
                .into(photoImageView);

        return view;
    }

    public void setData(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void sortByTopRated() {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie movie, Movie movie2) {
                return movie2.getUserRating().compareTo(movie.getUserRating());
            }
        });
        notifyDataSetChanged();
    }

    public void sortByPopularity() {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie movie, Movie movie2) {
                return Double.compare(movie2.getpopularity(),movie.getpopularity());
            }
        });
        notifyDataSetChanged();
    }
}




























































