package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import static com.example.popularmovies.DetailsActivity.MOVIE_DETAIL_BUNDLE;

public class MainActivity<OnCreateOptionsMenu> extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private MovieAdapter movieAdapter;
    private static final String YOUR_API_KEY = "";
    private static final String POPULAR_MOVIES_URL = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="+YOUR_API_KEY;
    ProgressBar progressIndicator;
    GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = findViewById(R.id.gridview);

        movieAdapter = new MovieAdapter(this, new ArrayList<Movie>());
        mGridView.setAdapter(movieAdapter);

        progressIndicator = findViewById(R.id.loading_indicator);
        progressIndicator.setVisibility(View.VISIBLE);

        getSupportLoaderManager().initLoader(0, null, this);


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie = (Movie) view.getTag();
                launchDetailActivity(movie);
            }
        });
    }

    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, @Nullable Bundle args) {

        return new MovieLoader(this, POPULAR_MOVIES_URL);
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<List<Movie>> loader, List<Movie> data) {
        movieAdapter.setData(data);
        if (!movieAdapter.isEmpty()) {
            progressIndicator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<List<Movie>> loader) {
        movieAdapter.clear();
    }

    private void launchDetailActivity(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MOVIE_DETAIL_BUNDLE, movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_order, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort) {
            showSortMenu();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showSortMenu() {
        PopupMenu sortMenu = new PopupMenu(this, findViewById(R.id.sort));
        sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.top_rated) {
                    movieAdapter.sortByTopRated();
                }
                else {
                    movieAdapter.sortByPopularity();
                }
                return false;
            }
        });
        sortMenu.inflate(R.menu.sort_menu);
        sortMenu.show();
    }
}










































