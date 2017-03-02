package com.nexus.nsnik.movies.drawer;

import java.util.ArrayList;

/**
 * Created by nsnik on 05-Nov-16.
 */

public class DrawerCustomList {

    String mListName;
    int mListImage;

    public DrawerCustomList(String nm,int ig){
        mListImage = ig;
        mListName = nm;
    }

    public int getmListImage() {
        return mListImage;
    }

    public String getmListName() {
        return mListName;
    }
}

