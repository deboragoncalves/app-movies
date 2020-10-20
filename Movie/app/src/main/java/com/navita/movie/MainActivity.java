package com.navita.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView listMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listMovies = (ListView) findViewById(R.id.list_movies);

        String[] movies = {"Atleta A", "Filme B", "Filme C", "Filme D", "Filme E", "Filme F", "Filme G", "Filme H",
        "Filme I", "Filme J", "Filme K", "Filme M", "Filme N", "Filme O", "Filme P", "Filme Q", "Filme S"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movies);
        listMovies.setAdapter(arrayAdapter);
    }
}