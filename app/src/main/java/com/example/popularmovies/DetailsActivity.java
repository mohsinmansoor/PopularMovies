package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    public static final String MOVIE_DETAIL_BUNDLE = "BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Movie movie = (Movie) getIntent().getSerializableExtra(MOVIE_DETAIL_BUNDLE);

        TextView originalTitleTv = findViewById(R.id.original_title_tv);
        originalTitleTv.setText(movie.getOriginalTitle());

        ImageView detailsImageVIew = findViewById(R.id.details_imageView);

        Glide.with(this).load(movie.getDetailsMoviePoster()).into(detailsImageVIew);

        TextView detailsRatings = findViewById(R.id.ratings_tv);
        detailsRatings.setText(movie.getUserRating());

        TextView releaseDate = findViewById(R.id.release_date_tv);
        releaseDate.setText(movie.getReleaseDate());

        TextView plotSynopsis = findViewById(R.id.plot_synopsis_tv);
        plotSynopsis.setText(movie.getPlotSynopsis());

    }
}
