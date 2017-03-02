package com.nexus.nsnik.movies.favouriteData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nexus.nsnik.movies.data.Tables;
import com.nexus.nsnik.movies.favouriteData.FavouriteTable.table1;


public class FavouriteHelper extends SQLiteOpenHelper{

    private static final int mDataBaseVersion = 2;

    private static final String mCreateTable = "CREATE TABLE "+ FavouriteTable.mTableName + " (" +
            table1.mId + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            table1.mPosterPath + " TEXT NOT NULL, "+
            table1.mOverView + " TEXT, "+
            table1.mReleaseDate+ " TEXT, "+
            table1.mMovieId + " INTEGER NOT NULL, "+
            table1.mTitle + " TEXT NOT NULL ,"+
            table1.mLanguague + " TEXT, "+
            table1.mBackDropPath+ " TEXT NOT NULL, "+
            table1.mPopularity+ " INTEGER NOT NULL DEFAULT 0, "+
            table1.mVoteAverage+ " INTEGER NOT NULL DEFAULT 0 "+
            ");";

    private static final String mDropTable = "DROP TABLE IF EXISTS "+ Tables.mTableName;


    public FavouriteHelper(Context context) {
        super(context, FavouriteTable.mDatabaseName, null, mDataBaseVersion);
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
