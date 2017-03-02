package com.nexus.nsnik.movies;


import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import com.nexus.nsnik.movies.data.Tables;
import com.nexus.nsnik.movies.data.Tables.table0;

public class GetList {

    private static final String mScheme = "https";
    private static final String mAuthority = "api.themoviedb.org";
    private static final String mApiString = "api_key";
    private static final String mApiValue = "d6d415564ca67c6a275a37265bc129a0";
    private static final String mLanguagueString = "language";
    private static final String mLanguagueValue = "en-US";
    private static final String mImageUrl = "https://image.tmdb.org/t/p/w500";

    private static String makeUri(){
        Uri.Builder ub = new Uri.Builder();
        ub.scheme(mScheme)
                .authority(mAuthority)
                .appendPath("3")
                .appendPath("movie")
                .appendPath("top_rated")
                .appendQueryParameter(mApiString,mApiValue)
                .appendQueryParameter(mLanguagueString,mLanguagueValue)
                .build();
        return ub.toString();
    }

    private static URL makeUrl(String u){
        URL url = null;
        try {
            url = new URL(u);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeJson(URL u){

        String json = "";
        HttpURLConnection htpc = null;
        InputStream is = null;
        try {
            htpc = (HttpURLConnection) u.openConnection();
            htpc.setRequestMethod("GET");
            htpc.connect();
            int responseCode = htpc.getResponseCode();
            if(responseCode==200){
                is = htpc.getInputStream();
                json = getJson(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            htpc.disconnect();
        }
        return json;
    }

    private static String getJson(InputStream is) throws IOException {
        InputStreamReader ir = new InputStreamReader(is, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(ir);
        String line = br.readLine();
        StringBuilder sb = new StringBuilder();
        while (line!=null){
            sb.append(line);
            line = br.readLine();
        }
        return sb.toString();
    }

    static final void makeList(Context c) throws JSONException {
        ContentValues contentValues = new ContentValues();
        URL mainUrl = makeUrl(makeUri());
        String mainJson = makeJson(mainUrl);
        JSONObject mainObject = new JSONObject(mainJson);
        JSONArray mainListArray = mainObject.getJSONArray("results");
        for(int i=0;i<mainListArray.length();i++) {
            JSONObject arrayMainObject = mainListArray.getJSONObject(i);
            String posterPath = arrayMainObject.getString("poster_path");
            contentValues.put(table0.mPosterPath, posterPath);
            String overView = arrayMainObject.getString("overview");
            contentValues.put(table0.mOverView, overView);
            String releaseDate = arrayMainObject.getString("release_date");
            contentValues.put(table0.mReleaseDate, releaseDate);
            String movieId = arrayMainObject.getString("id");
            contentValues.put(table0.mMovieId, movieId);
            String languague = arrayMainObject.getString("original_language");
            contentValues.put(table0.mLanguague, languague);
            String title = arrayMainObject.getString("title");
            contentValues.put(table0.mTitle, title);
            String backdropPath = arrayMainObject.getString("backdrop_path");
            contentValues.put(table0.mBackDropPath, backdropPath);
            String popularity = arrayMainObject.getString("popularity");
            contentValues.put(table0.mPopularity, popularity);
            String voteAverage = arrayMainObject.getString("vote_average");
            contentValues.put(table0.mVoteAverage, voteAverage);
            c.getContentResolver().insert(Tables.mContentUri,contentValues);
        }

    }
}
