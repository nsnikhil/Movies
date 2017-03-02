package com.nexus.nsnik.movies.image;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nexus.nsnik.movies.data.Tables;

import java.io.File;

/**
 * Created by nsnik on 07-Nov-16.
 */

public class loadImage {

    private static final String mFolderName = "nsnikmovies";

    private Bitmap getBitmap(Context c, Cursor cursor) {
        Bitmap img = null;
        File folder = c.getExternalFilesDir(mFolderName);
        File fi = new File(folder,cursor.getString(cursor.getColumnIndex(Tables.table0.mPosterPath)));
        String fpath = String.valueOf(fi);
        img = BitmapFactory.decodeFile(fpath);
        return img;
    }
}
