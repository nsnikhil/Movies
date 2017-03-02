package com.nexus.nsnik.movies.drawer;

import com.nexus.nsnik.movies.R;

import java.util.ArrayList;

/**
 * Created by nsnik on 05-Nov-16.
 */

public class GetDrawerList {

    static String[] mNames = {"Test","Home","Accounts","Settings"};
    static int[] mIconId = {R.mipmap.ic_launcher,R.drawable.ic_home_white_48dp,R.drawable.ic_account_circle_white_48dp,R.drawable.ic_settings_white_48dp};

    public static ArrayList<DrawerCustomList> makeList(){
        ArrayList<DrawerCustomList> lDrawerList = new ArrayList<>();
        for(int i =0;i<mNames.length;i++){
            lDrawerList.add(new DrawerCustomList(mNames[i],mIconId[i]));
        }
        return lDrawerList;
    }
}
