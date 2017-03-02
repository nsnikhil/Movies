package com.nexus.nsnik.movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.nexus.nsnik.movies.data.Tables;
import com.nexus.nsnik.movies.data.Tables.table0;

import static android.content.Context.MODE_PRIVATE;


public class TopMoviesList extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    GridView topList;
    private static final int mLoaderConstant = 25;
    CursorAdapter cad;
    String sortOrder = null;
    SharedPreferences.OnSharedPreferenceChangeListener spChanged = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
                    setOrder();
                    loadfData();
                }
            };
    FrameLayout detailsPane;
    ProgressBar mProgressBar;

    public TopMoviesList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_top_movies_list,container,false);
        initilize(v);
        cad = new CursorAdapter(getActivity(),null);
        topList.setAdapter(cad);
        topList.setEmptyView(v.findViewById(R.id.topEmptyImage));
        setOrder();
        loadfData();
        topList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(v.findViewById(R.id.favouriteTwoPane)!=null){
                    Bundle uri = new Bundle();
                    uri.putParcelable(getResources().getString(R.string.detailsBundleUriKey),Uri.withAppendedPath (Tables.mContentUri,String.valueOf(id)));
                    DetailsFragment detailsFragment = new DetailsFragment();
                    detailsFragment.setArguments(uri);
                    getFragmentManager().beginTransaction().replace(R.id.favouriteTwoPane,detailsFragment).commit();
                }else {
                    Intent details = new Intent(getActivity(),Details.class);
                    details.setData(Uri.withAppendedPath(Tables.mContentUri,String.valueOf(id)));
                    startActivity(details);
                }
            }
        });
        return v;
    }

    private void setOrder(){
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String d = spf.getString(getResources().getString(R.string.prefListKey),"");
        if(d.equalsIgnoreCase("1")){
            sortOrder = table0.mPopularity +" DESC";
        }if(d.equalsIgnoreCase("2")){
            sortOrder = table0.mVoteAverage +" DESC";;
        }
        spf.registerOnSharedPreferenceChangeListener(spChanged);
    }

    private void initilize(View v) {
        topList = (GridView) v.findViewById(R.id.listViewTop);
        detailsPane = (FrameLayout)v.findViewById(R.id.favouriteTwoPane);
        mProgressBar = (ProgressBar)v.findViewById(R.id.progressTopList);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case mLoaderConstant:
                return new CursorLoader(getActivity(),Tables.mContentUri,null,null,null,sortOrder);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mProgressBar.setVisibility(View.GONE);
        cad.swapCursor(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cad.swapCursor(null);
    }

    private void loadfData() {
        if (getActivity().getSupportLoaderManager().getLoader(mLoaderConstant) == null) {
            getActivity().getSupportLoaderManager().initLoader(mLoaderConstant, null, this).forceLoad();
        } else {
            getActivity().getSupportLoaderManager().restartLoader(mLoaderConstant, null, this).forceLoad();
        }
    }


    private boolean chkcon() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
