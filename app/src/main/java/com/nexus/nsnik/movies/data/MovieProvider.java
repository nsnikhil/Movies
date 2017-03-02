package com.nexus.nsnik.movies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.nexus.nsnik.movies.data.Tables.table0;
import android.support.annotation.Nullable;


public class MovieProvider extends ContentProvider{

    private static final int uEntireList = 2001;
    private static final int uSingleMovie = 2002;

    static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(Tables.mAuthority,Tables.mTableName,uEntireList);
        sUriMatcher.addURI(Tables.mAuthority,Tables.mTableName+"/#",uSingleMovie);
    }

    MovieHelper movieHelper;

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sdb = movieHelper.getReadableDatabase();
        Cursor c =null;
        switch (sUriMatcher.match(uri)){
            case uEntireList:
                c = sdb.query(Tables.mTableName,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case uSingleMovie:
                selection = table0.mId + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                c = sdb.query(Tables.mTableName,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query from the uri :"+uri);
        }
        c.setNotificationUri(getContext().getContentResolver(),uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (sUriMatcher.match(uri)){
            case uEntireList:
                return insertVal(uri,values);
            default:
                throw new IllegalArgumentException("Cannot insert into uri :"+uri);
        }
    }

    private Uri insertVal(Uri u,ContentValues values) {
        SQLiteDatabase sdb = movieHelper.getWritableDatabase();
        long rowId = sdb.insert(Tables.mTableName,null,values);
        if(rowId==-1){
            return null;
        }else{
            getContext().getContentResolver().notifyChange(u,null);
            return Uri.withAppendedPath(u,String.valueOf(rowId));
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sdb = movieHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)){
            case uEntireList:
                getContext().getContentResolver().notifyChange(uri,null);
                return sdb.delete(Tables.mTableName,null,null);
            case uSingleMovie:
                selection = table0.mId + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                getContext().getContentResolver().notifyChange(uri,null);
                return sdb.delete(Tables.mTableName,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Cannot delete from uri :"+uri);

        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)){
            case uEntireList:
                return updateVal(uri,values,selection,selectionArgs);
            case uSingleMovie:
                selection = table0.mId + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateVal(uri,values,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Cannot update on the uri :"+uri);
        }
    }

    private int updateVal(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase sdb = movieHelper.getWritableDatabase();
        int id = sdb.update(Tables.mTableName,values,selection,selectionArgs);
        if(id==0){
            return 0;
        }else {
            getContext().getContentResolver().notifyChange(uri,null);
            return id;
        }
    }
}
