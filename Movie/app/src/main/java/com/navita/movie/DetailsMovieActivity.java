package com.navita.movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsMovieActivity extends AppCompatActivity {

    private TextView movieTitle;
    private TextView moviePopularity;
    private TextView movieGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        movieTitle = (TextView) findViewById(R.id.title_movie);
        moviePopularity = (TextView) findViewById(R.id.popularity_movie);
        movieGenre = (TextView) findViewById(R.id.genres_movie);
        BottomNavigationView bottomMenu = (BottomNavigationView) findViewById(R.id.menu);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        assert bundle != null;
        int movieId = bundle.getInt("id");
        requestDetailsMovie(movieId);

        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;

                if (item.getItemId() == R.id.page_list) {

                    intent = new Intent(DetailsMovieActivity.this, MainActivity.class);
                    startActivity(intent);

                    return true;
                } else if (item.getItemId() == R.id.page_login) {

                    // Limpar shared preferences

                    SharedPreferences sharedPreferences = DetailsMovieActivity.this.getSharedPreferences("data_login", Context.MODE_PRIVATE);
                    sharedPreferences.edit().clear().apply();

                    if (sharedPreferences.getAll().isEmpty()) {
                        intent = new Intent(DetailsMovieActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    return true;
                } else {
                    return false;
                }

            }
        });


    }

    private void requestDetailsMovie(int movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=91ccbf054dea918f20216e44a5996892&language=pt-PT";

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                JSONObject movieDetails = null;

                try {
                    movieDetails = new JSONObject(response);

                    JSONArray arrayGenres = movieDetails.getJSONArray("genres");

                    for (int i = 0; i < arrayGenres.length(); i++) {
                        JSONObject dataGenres = arrayGenres.getJSONObject(i);

                        movieGenre.append(" " + dataGenres.getString("name") + ".");
                    }

                    String popularityMovie = String.valueOf(movieDetails.getInt("popularity"));
                    String popularity = "Popularidade: " + popularityMovie + " mil";
                    moviePopularity.setText(popularity);

                    String title = "Título Original: " + movieDetails.getString("original_title");
                    movieTitle.setText(title);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);

    }
}