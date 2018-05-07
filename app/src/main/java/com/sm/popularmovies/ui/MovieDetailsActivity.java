package com.sm.popularmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.sm.popular_movies.R;
import com.sm.popularmovies.model.Movies;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    protected static final String NA = "Not Available";
    TextView movieTitle;
    ImageView moviePosterImage;

    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_layout);
        ButterKnife.bind(this);
        moviePosterImage = (ImageView) findViewById(R.id.movie_poster);
        movieTitle = (TextView) findViewById(R.id.title);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        Movies userElectedMovie = (Movies) intent.getParcelableExtra(EXTRA_MOVIE);
        if (userElectedMovie == null) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        populateUI(userElectedMovie);
        Picasso.with(this)
                .load(getPath(userElectedMovie.getmPosterPath()))
                .fit()
                .into(moviePosterImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        // image loading finished, so remove progressbar.
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        // On error , while loading sandwich image, display error message.
                        moviePosterImage.setVisibility(View.INVISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        movieTitle.setText(getString(R.string.detail_image_error_message));
                        movieTitle.setVisibility(View.VISIBLE);
                    }
                });

        setTitle(userElectedMovie.getmOriginalTitle());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private String getPath(String posterPath) {
        String url = "http://image.tmdb.org/t/p/" + "w500/" + posterPath;
        return url;
    }

    private void populateUI(@NonNull Movies movie) {
        // Load views, from model object
        mProgressBar = (ProgressBar) findViewById(R.id.loadingprogress);
        mProgressBar.setVisibility(View.VISIBLE);

        TextView originalTitleTextView = (TextView) findViewById(R.id.original_title_tv);
        String originalTitle = movie.getmOriginalTitle();
        if (!originalTitle.isEmpty()) {
            originalTitleTextView.setText(originalTitle);
        } else {
            originalTitleTextView.setText(NA);
        }

        TextView releaseDateTextView = (TextView) findViewById(R.id.release_date_tv);
        String releaseDate = movie.getmReleaseDate();
        if (!releaseDate.isEmpty()) {
            releaseDateTextView.setText(releaseDate);
        } else {
            releaseDateTextView.setText(NA);
        }

        TextView userRatingTextView = (TextView) findViewById(R.id.user_rating_tv);
        String userRating = movie.getmPopularity();
        if (!userRating.isEmpty()) {
            userRatingTextView.setText(userRating);
        } else {
            userRatingTextView.setText(NA);
        }

        TextView movieOverview = (TextView)findViewById(R.id.movie_synopsis_tv);
        String overview = movie.getmOverview();
        if (!overview.isEmpty()) {
            movieOverview.setText(overview);
        } else {
            movieOverview.setText(NA);
        }
    }
}
