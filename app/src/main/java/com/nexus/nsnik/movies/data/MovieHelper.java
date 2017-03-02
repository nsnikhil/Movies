package com.nexus.nsnik.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nexus.nsnik.movies.data.Tables.table0;


public class MovieHelper extends SQLiteOpenHelper{

    private static final int mDataBaseVersion = 2;

    private static final String mCreateTable = "CREATE TABLE "+ Tables.mTableName + " (" +
            table0.mId + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            table0.mPosterPath + " TEXT NOT NULL, "+
            table0.mOverView + " TEXT, "+
            table0.mReleaseDate+ " TEXT, "+
            table0.mMovieId + " INTEGER NOT NULL, "+
            table0.mTitle + " TEXT NOT NULL ,"+
            table0.mLanguague + " TEXT, "+
            table0.mBackDropPath+ " TEXT NOT NULL, "+
            table0.mPopularity+ " INTEGER NOT NULL DEFAULT 0, "+
            table0.mVoteAverage+ " INTEGER NOT NULL DEFAULT 0 "+
            ");";

    private static final String mDropTable = "DROP TABLE IF EXISTS "+ Tables.mTableName;

    public MovieHelper(Context context) {
        super(context, Tables.mDatabaseName, null, mDataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    private static void createTable(SQLiteDatabase sbd){
        sbd.execSQL(mCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(mDropTable);
        createTable(db);
    }
}
