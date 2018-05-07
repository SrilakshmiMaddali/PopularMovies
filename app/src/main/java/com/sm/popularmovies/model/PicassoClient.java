package com.sm.popularmovies.model;

import android.content.Context;
import android.widget.ImageView;
import com.sm.popular_movies.R;
import com.squareup.picasso.Picasso;

public class PicassoClient {

    public static void downloadImage(Context c,String url,ImageView img)
    {
        if(url != null && url.length()>0)
        {
            Picasso.with(c).load(url).placeholder(R.drawable.ic_launcher_background).into(img);
        }else
        {
            Picasso.with(c).load(R.drawable.ic_launcher_background).into(img);
        }
    }

}