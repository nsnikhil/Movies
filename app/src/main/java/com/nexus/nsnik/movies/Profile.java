package com.nexus.nsnik.movies;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nexus.nsnik.movies.favouriteData.FavouriteTable;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    TextView count;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initilize();
        count.setText(" "+getResources().getString(R.string.profileFavourite)+getContentResolver().query(FavouriteTable.mContentUri,null,null,null,null).getCount());
        if(getContentResolver().query(FavouriteTable.mContentUri,null,null,null,null).getCount()<=0){
            delete.setClickable(false);
        }else {
            delete.setOnClickListener(this);
        }
    }

    private void initilize() {
        count = (TextView)findViewById(R.id.profileFavouriteCount);
        delete = (Button)findViewById(R.id.profileDeleteAllFavouriteCount);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profileDeleteAllFavouriteCount:
                AlertDialog.Builder adb =  new AlertDialog.Builder(Profile.this);
                adb.setTitle(getResources().getString(R.string.warning)).setMessage(getResources().getString(R.string.areyousure))
                        .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = getContentResolver().delete(FavouriteTable.mContentUri,null,null);
                        if(id==0){
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.cannotDelete),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.deleted),Toast.LENGTH_SHORT).show();
                            delete.setClickable(false);
                            count.setText(getResources().getString(R.string.profileFavourite)+" "+0);
                        }
                    }
                });
                adb.create();
                adb.show();
                break;
        }
    }
}
