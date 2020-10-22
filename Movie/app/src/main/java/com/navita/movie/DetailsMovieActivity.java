package com.navita.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class DetailsMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        int id = bundle.getInt("id");
        Log.d("=====id", ""+id);
    }
}