package com.nexus.nsnik.movies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by nsnik on 05-Nov-16.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter{

    private static final CharSequence[] mPageTitle = {"Top Movies","Favourites"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        if(position==0){
            f = new TopMoviesList();
        }
        if(position==1){
            f = new FavouriteList();
        }
        return f;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence s= null;
        if(position==0)
        {
            s = mPageTitle[0];
        }
        if(position==1)
        {
            s = mPageTitle[1];
        }
        return s;
    }
}
