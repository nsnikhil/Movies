package com.nexus.nsnik.movies.favouriteData;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;


public class FavouriteProvider extends ContentProvider {

    private static final int uEntireList = 3001;
    private static final int uSingleMovie = 3002;

    static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(FavouriteTable.mAuthority, FavouriteTable.mTableName, uEntireList);
        sUriMatcher.addURI(FavouriteTable.mAuthority, FavouriteTable.mTableName + "/#", uSingleMovie);
    }

    FavouriteHelper favouriteHelper;

    @Override
    public boolean onCreate() {
        favouriteHelper = new FavouriteHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sdb = favouriteHelper.getReadableDatabase();
        Cursor c = null;
        switch (sUriMatcher.match(uri)) {
            case uEntireList:
                c = sdb.query(FavouriteTable.mTableName, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case uSingleMovie:
                selection = FavouriteTable.table1.mId + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                c = sdb.query(FavouriteTable.mTableName, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query from the uri :" + uri);
        }
        c.setNotificationUri(getContext().getContentResolver(), uri);
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
        switch (sUriMatcher.match(uri)) {
            case uEntireList:
                return insertVal(uri, values);
            default:
                throw new IllegalArgumentException("Cannot insert into uri :" + uri);
        }
    }

    private Uri insertVal(Uri u, ContentValues values) {
        SQLiteDatabase sdb = favouriteHelper.getWritableDatabase();
        long rowId = sdb.insert(FavouriteTable.mTableName, null, values);
        if (rowId == -1) {
            return null;
        } else {
            getContext().getContentResolver().notifyChange(u, null);
            return Uri.withAppendedPath(u, String.valueOf(rowId));
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sdb = favouriteHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case uEntireList:
                getContext().getContentResolver().notifyChange(uri, null);
                return sdb.delete(FavouriteTable.mTableName, null, null);
            case uSingleMovie:
                selection = FavouriteTable.table1.mId + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                getContext().getContentResolver().notifyChange(uri, null);
                return sdb.delete(FavouriteTable.mTableName, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Cannot delete from uri :" + uri);

        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case uEntireList:
                return updateVal(uri, values, selection, selectionArgs);
            case uSingleMovie:
                selection = FavouriteTable.table1.mId + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateVal(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Cannot update on the uri :" + uri);
        }
    }

    private int updateVal(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase sdb = favouriteHelper.getWritableDatabase();
        int id = sdb.update(FavouriteTable.mTableName, values, selection, selectionArgs);
        if (id == 0) {
            return 0;
        } else {
            getContext().getContentResolver().notifyChange(uri, null);
            return id;
        }
    }
}
