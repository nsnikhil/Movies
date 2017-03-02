package com.nexus.nsnik.movies;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nexus.nsnik.movies.data.Tables;
import com.nexus.nsnik.movies.data.Tables.table0;
import java.io.File;


public class CursorAdapter extends android.support.v4.widget.CursorAdapter{

    private static final String mFolderName = "nsnikmovies";

    public CursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_layout,parent,false);
        MyViewHolder cursorViewHolder = new MyViewHolder(v);
        v.setTag(cursorViewHolder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(table0.mTitle));
        MyViewHolder myh = (MyViewHolder) view.getTag();
        myh.movieTitle.setText(title);
        myh.moviePoster.setImageBitmap(getBitmap(context,cursor));
    }

    private Bitmap getBitmap(Context c, Cursor cursor) {
        Bitmap img = null;
        File folder = c.getExternalFilesDir(mFolderName);
        File fi = new File(folder,cursor.getString(cursor.getColumnIndex(Tables.table0.mPosterPath)));
        String fpath = String.valueOf(fi);
        img = BitmapFactory.decodeFile(fpath);
        return img;
    }


    public static class MyViewHolder{
        TextView movieTitle;
        ImageView moviePoster;
        public MyViewHolder(View v){
            movieTitle = (TextView)v.findViewById(R.id.itemMovieTitle);
            moviePoster = (ImageView)v.findViewById(R.id.itemMoviePoster);
        }
    }
}
