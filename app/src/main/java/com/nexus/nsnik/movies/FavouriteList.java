package com.nexus.nsnik.movies;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.nexus.nsnik.movies.R;
import com.nexus.nsnik.movies.data.Tables;
import com.nexus.nsnik.movies.favouriteData.FavouriteTable;

import java.util.ArrayList;

public class FavouriteList extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    GridView favouritesList;
    private static final int mLoaderId = 3056;
    FavouriteAdapter fad;

    public FavouriteList() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_favourite_list,container,false);
        initilize(v);
        fad = new FavouriteAdapter(getActivity(),null);
        favouritesList.setAdapter(fad);
        favouritesList.setEmptyView(v.findViewById(R.id.favouriteEmptyImage));
        loadfData();
        favouritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(v.findViewById(R.id.fovouriteTwoPane)!=null){
                    Bundle uri = new Bundle();
                    uri.putParcelable(getResources().getString(R.string.detailsBundleUriKey), Uri.withAppendedPath (FavouriteTable.mContentUri,String.valueOf(id)));
                    DetailsFragment detailsFragment = new DetailsFragment();
                    detailsFragment.setArguments(uri);
                    getFragmentManager().beginTransaction().replace(R.id.fovouriteTwoPane,detailsFragment).commit();
                }else {
                    Intent details = new Intent(getActivity(),Details.class);
                    details.setData(Uri.withAppendedPath(FavouriteTable.mContentUri,String.valueOf(id)));
                    startActivity(details);
                }
            }
        });
        return v;
    }

    private void initilize(View v) {
        favouritesList = (GridView) v.findViewById(R.id.listViewFavourite);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case mLoaderId:
                return new CursorLoader(getContext(), FavouriteTable.mContentUri,null,null,null,null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        fad.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        fad.swapCursor(null);
    }

    private void loadfData() {
        if (getActivity().getSupportLoaderManager().getLoader(mLoaderId) == null) {
            getActivity().getSupportLoaderManager().initLoader(mLoaderId, null, this).forceLoad();
        } else {
            getActivity().getSupportLoaderManager().restartLoader(mLoaderId, null, this).forceLoad();
        }
    }
}
