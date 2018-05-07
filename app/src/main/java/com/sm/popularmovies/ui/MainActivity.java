package com.sm.popularmovies.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.sm.popular_movies.R;
import com.sm.popularmovies.model.CustomAdapter;
import com.sm.popularmovies.model.MoviedbService;
import com.sm.popularmovies.model.Movies;
import com.sm.popularmovies.model.PopularMoviesDto;
import com.sm.popularmovies.model.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDoalog;
    GridView gridview;
    Context mContext;
    CustomAdapter mCustomAdapter;
    private Menu menu;
    MenuItem top;
    MenuItem pop;
    private static final String API_KEY = "28ccb476432342e7e6b8636f1e4e6772";
    List<Movies> mMoviesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridview = (GridView) findViewById(R.id.gridview);
        mContext = this;
        getMovieList(getPopularMoviewsCall());
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                    intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, mMoviesList.get(position));
                    startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movietypefilter, menu);
        top = menu.findItem(R.id.top_movies);
        pop = menu.findItem(R.id.popular_movies);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.popular_movies:
                getMovieList(getPopularMoviewsCall());
                top.setVisible(true);
                pop.setVisible(false);
                invalidateOptionsMenu();
                setTitle(R.string.popular_movies);
                break;
            // action with ID action_settings was selected
            case R.id.top_movies:
                getMovieList(getTopRatedMovieCall());
                top.setVisible(false);
                pop.setVisible(true);
                setTitle(R.string.top_movies);
                break;
            default:
                break;
        }

        return true;
    }

    private Call<PopularMoviesDto> getPopularMoviewsCall() {
        MoviedbService service = RetrofitClientInstance.getRetrofitInstance().create(MoviedbService.class);
        Call<PopularMoviesDto> call = service.getPopularMovies(API_KEY);
        return call;
    }

    private Call<PopularMoviesDto> getTopRatedMovieCall() {
        MoviedbService service = RetrofitClientInstance.getRetrofitInstance().create(MoviedbService.class);
        Call<PopularMoviesDto> call = service.getTopRatedMovies(API_KEY);
        return call;
    }

    private void getMovieList(Call<PopularMoviesDto> call) {

        call.enqueue(new Callback<PopularMoviesDto>() {
            @Override
            public void onResponse(Call<PopularMoviesDto> call, Response<PopularMoviesDto> response) {
                //progressDoalog.dismiss();
                mMoviesList = response.body().getmResults();
                generateDataList(mMoviesList);
            }

            @Override
            public void onFailure(Call<PopularMoviesDto> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void generateDataList(List<Movies> moviesList) {
        mCustomAdapter = new CustomAdapter(mContext, moviesList);
        gridview.setAdapter(mCustomAdapter);
        gridview.invalidateViews();

    }
}
