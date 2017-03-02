package com.nexus.nsnik.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Details extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_container);
        getSupportFragmentManager().beginTransaction().add(R.id.detailsContainer,new DetailsFragment()).commit();
    }
}
