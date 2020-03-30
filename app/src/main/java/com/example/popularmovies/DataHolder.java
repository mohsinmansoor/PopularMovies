package com.example.popularmovies;

import java.util.ArrayList;

public class DataHolder {

    final ArrayList<Movie> movies = new ArrayList<>();

    private static DataHolder instance;

    public static DataHolder getInstance() {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    private DataHolder() {}

}
