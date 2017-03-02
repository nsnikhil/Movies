package com.nexus.nsnik.movies;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.nexus.nsnik.movies.data.Tables;

import org.json.JSONException;

/**
 * Created by nsnik on 07-Nov-16.
 */

public class AysncLoader extends AsyncTaskLoader{

    Context mContext;

    public AysncLoader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Object loadInBackground() {
        try {
            mContext.getContentResolver().delete(Tables.mContentUri,null,null);
            GetList.makeList(mContext);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
