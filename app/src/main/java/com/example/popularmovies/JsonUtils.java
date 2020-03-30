package com.example.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.popularmovies.MainActivity.LOG_TAG;

public class JsonUtils {

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";
    private static final String BASE_JSON = "results";
    private static final String AVERAGE_VOTE = "vote_average";
    private static final String POSTER_PATH = "poster_path";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String DETAILS_VIEW_POSTER = "backdrop_path";
    private static final String RELEASE_DATE = "release_date";
    private static final String PLOT_SYNOPSIS = "overview";
    private static final String POPULARITY = "popularity";

    private static List<Movie> extractMoviesFromJson(String moviesJson) {
        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(moviesJson);
            JSONArray resultsArray = baseJsonResponse.getJSONArray(BASE_JSON);
            for (int i =0; i < resultsArray.length(); i++) {
                JSONObject results = resultsArray.getJSONObject(i);
                String voteAvg = results.getString(AVERAGE_VOTE);
                String posterPath = results.getString(POSTER_PATH);
                String postFullPath = IMAGE_BASE_URL + IMAGE_SIZE + posterPath;
                String originalTitle = results.getString(ORIGINAL_TITLE);
                String detailsViewPoster = results.getString(DETAILS_VIEW_POSTER);
                String detailsViewPosterFullPath = IMAGE_BASE_URL + IMAGE_SIZE + detailsViewPoster;
                String releaseDate = results.getString(RELEASE_DATE);
                String plotSynopsis = results.getString(PLOT_SYNOPSIS);
                double popularity = results.getDouble(POPULARITY);

                Movie movieDetail = new Movie(postFullPath, originalTitle, detailsViewPosterFullPath, voteAvg, releaseDate, plotSynopsis, popularity);
                movies.add(movieDetail);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;

    }

    private static String makeHttpRequest(URL url) {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else
            {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (line != null) {
                output.append(line);
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return output.toString();
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    public static List<Movie> fetchMoviesData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse;
        jsonResponse = makeHttpRequest(url);

        return extractMoviesFromJson(jsonResponse);
    }
}
