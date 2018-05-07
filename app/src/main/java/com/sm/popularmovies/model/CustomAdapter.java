package com.sm.popularmovies.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sm.popular_movies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Movies> {
    private List<Movies> mMoviesList;
    private LayoutInflater mInflater;
    ViewHolder mHolder;
    Context mContext;


    private class ViewHolder {
        ImageView moviePoster;

    }

    public CustomAdapter(@NonNull Context context, @NonNull List<Movies> moviesList) {
        super(context, R.layout.grid_view_item, moviesList);
        mContext = context;
        Log.d("Pavan", moviesList.toString());
        mMoviesList = moviesList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View recordView = convertView;
        if (recordView == null) {
            mHolder = new ViewHolder();
            Log.d("Pavan", mMoviesList.get(position).getmPosterPath().toString());
            recordView = mInflater.inflate(R.layout.grid_view_item, null);
            mHolder.moviePoster = (ImageView) recordView.findViewById(R.id.poster);
            recordView.setTag(mHolder);

        }
        ViewHolder holder = (ViewHolder) recordView.getTag();
        String url = loadImage(position);
        //populateImagePoster( holder.moviePoster, url, position);
        PicassoClient.downloadImage(mContext, url, holder.moviePoster);
        return recordView;
    }

    private void populateImagePoster(View iview, String Imageurl, final int position) {
        Picasso.with(mContext)
                .load(Imageurl)
                .fit()
                .into((ImageView) iview);
    }


    private String loadImage(int position) {
        String url = "http://image.tmdb.org/t/p/" + "w500/" + mMoviesList.get(position).getmPosterPath();
        return url;
    }
}
