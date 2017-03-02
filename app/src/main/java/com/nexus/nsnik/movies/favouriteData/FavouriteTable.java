package com.nexus.nsnik.movies.favouriteData;

import android.net.Uri;
import android.provider.BaseColumns;



public class FavouriteTable {


    public static final String mDatabaseName = "favouriteDatabase";
    public static final String mTableName = "favouritetable";
    public static final String mSchene = "content://";
    public static final String mAuthority = "com.nexus.nsnik.movies.favourites";
    public static final Uri mBaseUri = Uri.parse(mSchene+mAuthority);
    public static final Uri mContentUri = Uri.withAppendedPath(mBaseUri,mTableName);

    public static class table1 implements BaseColumns {
        public static final String mId = BaseColumns._ID;
        public static final String mPosterPath = "posterpath";
        public static final String mOverView = "overview";
        public static final String mReleaseDate = "releasedate";
        public static final String mMovieId = "movieid";
        public static final String mTitle = "title";
        public static final String mLanguague = "laguague";
        public static final String mBackDropPath = "backdroppath";
        public static final String mPopularity = "popularity";
        public static final String mVoteAverage = "voteaverage";
    }
}
