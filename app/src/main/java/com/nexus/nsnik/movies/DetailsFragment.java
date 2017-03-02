package com.nexus.nsnik.movies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nexus.nsnik.movies.data.Tables;
import com.nexus.nsnik.movies.data.Tables.table0;
import com.nexus.nsnik.movies.favouriteData.FavouriteTable;
import com.nexus.nsnik.movies.favouriteData.FavouriteTable.table1;
import java.io.File;


public class DetailsFragment extends Fragment  implements View.OnClickListener{

    TextView movieName,ratings,overview,playTrailerText;
    ImageView poster,banner,playTrailer,addToFvourite;
    private static final String mFolderName = "nsnikmovies";
    Cursor c = null;
    Uri uri = null;
    private static final String LOG_TAG = DetailsFragment.class.getSimpleName();

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v   = LayoutInflater.from(getContext()).inflate(R.layout.activity_details,container,false);
        inititlize(v);
        Bundle arg = getArguments();
        if(arg!=null){
            uri = arg.getParcelable(getResources().getString(R.string.detailsBundleUriKey));
        }else {
            if(getActivity().getIntent()!=null) {
                uri = getActivity().getIntent().getData();
            }
        }
        if (uri!=null) {
            c = getActivity().getContentResolver().query(uri,null,null,null,null);
            if(c.moveToFirst()){
                movieName.setText(c.getString(c.getColumnIndex(Tables.table0.mTitle)));
                ratings.setText(c.getString(c.getColumnIndex(Tables.table0.mVoteAverage)).substring(0,3)+"/10");
                overview.setText(c.getString(c.getColumnIndex(Tables.table0.mOverView)));
            }
            poster.setImageBitmap(getBitmap(getActivity(),c.getString(c.getColumnIndex(Tables.table0.mPosterPath))));
            banner.setImageBitmap(getBitmap(getActivity(),c.getString(c.getColumnIndex(Tables.table0.mBackDropPath))));
        }
        playTrailer.setOnClickListener(this);
        playTrailerText.setOnClickListener(this);
        addToFvourite.setOnClickListener(this);
        if(!checkExists()){
            addToFvourite.setImageResource(R.drawable.ic_favorite_white_48dp);
            addToFvourite.setClickable(false);
        }
        return v;
    }

    private Bitmap getBitmap(Context c, String s) {
        Bitmap img = null;
        File folder = c.getExternalFilesDir(mFolderName);
        File fi = new File(folder,s);
        String fpath = String.valueOf(fi);
        img = BitmapFactory.decodeFile(fpath);
        return img;
    }

    private void inititlize(View v) {
        movieName = (TextView)v.findViewById(R.id.detailsMovieName);
        poster = (ImageView)v.findViewById(R.id.detailsPosterImage);
        banner = (ImageView)v.findViewById(R.id.detailsBannerImage);
        ratings = (TextView)v.findViewById(R.id.detailsMovieRatings);
        overview = (TextView)v.findViewById(R.id.detailsMovieOverview);
        playTrailer = (ImageView)v.findViewById(R.id.detailsPlayIcon);
        playTrailerText = (TextView)v.findViewById(R.id.detailsPlayText);
        addToFvourite = (ImageView)v.findViewById(R.id.detailsAddToFavourite);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detailsPlayIcon:
            case R.id.detailsPlayText:
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/results?search_query="+c.getString(c.getColumnIndex(Tables.table0.mTitle)))));
                break;
            case R.id.detailsAddToFavourite:
                ContentValues contentValues = new ContentValues();
                Uri u = null;
                if(checkExists()){
                    if(c.moveToFirst()){
                        contentValues.put(table1.mPosterPath,c.getString(c.getColumnIndex(table0.mPosterPath)));
                        contentValues.put(table1.mOverView,c.getString(c.getColumnIndex(table0.mOverView)));
                        contentValues.put(table1.mReleaseDate,c.getString(c.getColumnIndex(table0.mReleaseDate)));
                        contentValues.put(table1.mMovieId,c.getString(c.getColumnIndex(table0.mMovieId)));
                        contentValues.put(table1.mTitle,c.getString(c.getColumnIndex(table0.mTitle)));
                        contentValues.put(table1.mLanguague,c.getString(c.getColumnIndex(table0.mLanguague)));
                        contentValues.put(table1.mBackDropPath,c.getString(c.getColumnIndex(table0.mBackDropPath)));
                        contentValues.put(table1.mPopularity,c.getString(c.getColumnIndex(table0.mPopularity)));
                        contentValues.put(table1.mVoteAverage,c.getString(c.getColumnIndex(table0.mVoteAverage)));
                        u = getContext().getContentResolver().insert(FavouriteTable.mContentUri,contentValues);
                    }
                    if(u==null){
                        Toast.makeText(getActivity(),getResources().getString(R.string.favouriteCannotAdd),Toast.LENGTH_SHORT).show();
                    }else {
                        addToFvourite.setImageResource(R.drawable.ic_favorite_white_48dp);
                        Toast.makeText(getActivity(),getResources().getString(R.string.favouriteAdded),Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(),getResources().getString(R.string.favouriteAlreadyPresent),Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean checkExists() {
        Cursor temp = getContext().getContentResolver().query(FavouriteTable.mContentUri,null,null,null,null);
        while (temp.moveToNext()){
            if(c.getString(c.getColumnIndex(table0.mMovieId)).equalsIgnoreCase(temp.getString(temp.getColumnIndex(table1.mMovieId)))){
                return false;
            }
        }
        return true;
    }

}




