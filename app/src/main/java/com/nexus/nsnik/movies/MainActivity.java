package com.nexus.nsnik.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nexus.nsnik.movies.data.Tables;
import com.nexus.nsnik.movies.drawer.DrawerAdapter;
import com.nexus.nsnik.movies.drawer.DrawerCustomList;
import com.nexus.nsnik.movies.drawer.GetDrawerList;
import com.nexus.nsnik.movies.image.saveBanner;
import com.nexus.nsnik.movies.image.saveImage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks{

    private Toolbar mainActivityToolbar;
    private ListView mainActivityDrawerListView;
    private DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    ViewPager mMainActivityViewPager;
    TabLayout mMainActivityTabs;
    private static final int mLoaderId1 = 2005;
    private static final int mLoaderId2 = 2006;
    private static final int mLoaderId3 = 2007;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initilize();
        setSupportActionBar(mainActivityToolbar);
        ArrayList<DrawerCustomList> olist = GetDrawerList.makeList();
        DrawerAdapter mAdapetr = new DrawerAdapter(this, olist);
        mainActivityDrawerListView.setAdapter(mAdapetr);
        mainActivityDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==2){
                    mDrawerLayout.closeDrawers();
                    startActivity(new Intent(MainActivity.this,Profile.class));
                }
                if(position==3){
                    mDrawerLayout.closeDrawers();
                    startActivity(new Intent(MainActivity.this,Prefrences.class));
                }
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mainActivityToolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        ViewPagerAdapter lPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mMainActivityViewPager.setAdapter(lPagerAdapter);
        mMainActivityTabs.setupWithViewPager(mMainActivityViewPager);
        loadfData();
    }

    private void initilize() {
        mainActivityToolbar = (Toolbar)findViewById(R.id.mainActivityToolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mainActivityDrawerListView = (ListView)findViewById(R.id.mainDrawer);
        mMainActivityViewPager = (ViewPager)findViewById(R.id.mainActivityViewPager);
        mMainActivityTabs = (TabLayout)findViewById(R.id.mainActivityTabBar);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id){
            case mLoaderId1:
                return new AysncLoader(this);
            case mLoaderId2:
                return new saveImage(this);
            case mLoaderId3:
                return new saveBanner(this);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if(loader.getId()==mLoaderId1){
            loadfImages();
        }if(loader.getId()==mLoaderId2){
            loadfBackdrop();
        }
    }


    @Override
    public void onLoaderReset(Loader loader) {

    }

    private void loadfData() {
        if (getSupportLoaderManager().getLoader(mLoaderId1) == null) {
            getSupportLoaderManager().initLoader(mLoaderId1, null, this).forceLoad();
        } else {
            getSupportLoaderManager().restartLoader(mLoaderId1, null, this).forceLoad();
        }
    }

    private void loadfBackdrop() {
        if (getSupportLoaderManager().getLoader(mLoaderId3) == null) {
            getSupportLoaderManager().initLoader(mLoaderId3, null, this).forceLoad();
        } else {
            getSupportLoaderManager().restartLoader(mLoaderId3, null, this).forceLoad();
        }
    }

    private void loadfImages() {
        if (getSupportLoaderManager().getLoader(mLoaderId2) == null) {
            getSupportLoaderManager().initLoader(mLoaderId2, null, this).forceLoad();
        } else {
            getSupportLoaderManager().restartLoader(mLoaderId2, null, this).forceLoad();
        }
    }
}
