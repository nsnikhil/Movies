package com.nexus.nsnik.movies.image;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.nexus.nsnik.movies.data.Tables;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.nexus.nsnik.movies.data.Tables.table0;


public class saveImage extends AsyncTaskLoader{

    static Context mContext;

    private static final String mFolderName = "nsnikmovies";
    private static final String LOG_TAG = saveImage.class.getSimpleName();

    public saveImage(Context context) {
        super(context);
        mContext = context;
    }


    @Override
    public Object loadInBackground() {
        store();
        return null;
    }

    private void delall() {
        File dir = mContext.getExternalFilesDir(mFolderName);
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }
    }

    public static void store(){
        Cursor c = mContext.getContentResolver().query(Tables.mContentUri,null,null,null,null);
        while (c.moveToNext()){
            String s = c.getString(c.getColumnIndex(table0.mPosterPath));
            if(!exists(s)){
                URL imgUrl = makeUrl(s);
                Bitmap poster = getBitmap(imgUrl);
                saveImage(s,mContext,poster);
            }
        }
    }

    private static boolean exists(String s){
        File folder  = mContext.getExternalFilesDir(mFolderName);
        if(folder.exists()){
            File f = new File(folder,s);
            if(f.exists()){
                return true;
            }
        }
        return false;
    }

    private static URL makeUrl(String t){
        final String mImageBaseUrl = "https://image.tmdb.org/t/p/w500";
        URL u = null;
        try {
            u = new URL(mImageBaseUrl+t);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return u;
    }

    private static Bitmap getBitmap(URL imageUrl) {
        Bitmap pstr = null;
        HttpURLConnection htpc = null;
        InputStream ir;
        try {
            htpc = (HttpURLConnection) imageUrl.openConnection();
            htpc.setRequestMethod("GET");
            htpc.connect();
            if(htpc.getResponseCode()==200){
                ir = htpc.getInputStream();
                pstr = BitmapFactory.decodeStream(ir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            htpc.disconnect();
        }
        return pstr;
    }

    private static void saveImage(String s,Context c,Bitmap b) {
        File folder  = c.getExternalFilesDir(mFolderName);
        if(!folder.exists()){
            folder.mkdir();
        }
        File f = new File(folder,s);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            b.compress(Bitmap.CompressFormat.JPEG, 20, fos);
            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
