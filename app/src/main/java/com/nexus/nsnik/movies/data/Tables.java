package com.nexus.nsnik.movies.data;

import android.net.Uri;
import android.provider.BaseColumns;


public class Tables {

    public static final String mDatabaseName = "movieDatabase";
    public static final String mTableName = "movietable";
    public static final String mSchene = "content://";
    public static final String mAuthority = "com.nexus.nsnik.movies";
    public static final Uri mBaseUri = Uri.parse(mSchene+mAuthority);
    public static final Uri mContentUri = Uri.withAppendedPath(mBaseUri,mTableName);

    public static class table0 implements BaseColumns {
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
