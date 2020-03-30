package com.example.popularmovies;

import java.io.Serializable;

public class Movie implements Serializable {

    private String mUserRating;
    private String mOriginalTitle;
    private String mMainMoviePoster;
    private String mPlotSynopsis;
    private String mReleaseDate;
    private String mDetailsMoviePoster;
    private double mPopularity;

    Movie(String mainMoviePoster, String originalTitle, String detailsMoviePoster, String userRating, String releaseDate, String plotSynopsis, double popularity) {
        mMainMoviePoster = mainMoviePoster;
        mOriginalTitle = originalTitle;
        mDetailsMoviePoster = detailsMoviePoster;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
        mPlotSynopsis = plotSynopsis;
        mPopularity = popularity;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public String getMainMoviePoster() {
        return mMainMoviePoster;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getDetailsMoviePoster() {
        return mDetailsMoviePoster;
    }

    public double getpopularity() {
        return mPopularity;
    }
}
