package com.nexus.nsnik.movies;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by nsnik on 10-Nov-16.
 */

public class Prefrences extends AppCompatActivity{

    Toolbar prefToolBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conatiner);
        initilize();
        setSupportActionBar(prefToolBar);
        getFragmentManager().beginTransaction().add(R.id.prefrenceContainer,new PrefrencesFragment()).commit();
    }

    private void initilize() {
        prefToolBar = (Toolbar)findViewById(R.id.prefActivityToolbar);
    }

    public static class PrefrencesFragment extends PreferenceFragment{

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);
        }
    }
}
