package com.navita.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listMovies;
    private ArrayList<String> movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listMovies = (ListView) findViewById(R.id.list_movies);

        movieTitle = new ArrayList<>();

        requestMovieTitle(movieTitle);

        listMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long id) {

                requestMovieId(position);
            }
        });

    }

    private void requestMovieId(final int position) {

        // Instanciar requestQueue

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=91ccbf054dea918f20216e44a5996892";

        // Solicitar resposta (get) do backend em String

        // Listener implementa método onResponse e onError (assíncrono)

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // Transformar string em JSON

                JSONObject jsonResponse = null;

                try {

                    jsonResponse = new JSONObject(response);

                    // Array results e propriedade id

                    JSONArray jsonArrayResults = jsonResponse.getJSONArray("results");

                    JSONObject dataMovie = jsonArrayResults.getJSONObject(position);

                    int id = dataMovie.getInt("id");
                    getMovieId(id);

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

        // Adicionar request ao requestQueue

        requestQueue.add(stringRequest);

    }

    private void getMovieId(int id) {
        Intent intent = new Intent(MainActivity.this, DetailsMovieActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void requestMovieTitle(ArrayList<String> movieList) {

        // Instanciar requestQueue

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=91ccbf054dea918f20216e44a5996892";

        // Solicitar resposta (get) do backend em String

        // Listener implementa método onResponse e onError (assíncrono)

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // Transformar string em JSON

                JSONObject jsonResponse = null;

                try {
                    jsonResponse = new JSONObject(response);

                    // Array results, percorrer objetos e propriedade title

                    JSONArray jsonArrayResults = jsonResponse.getJSONArray("results");

                    for (int i = 0; i < jsonArrayResults.length(); i++) {

                        JSONObject dataMovie = jsonArrayResults.getJSONObject(i);

                        String title = dataMovie.getString("title");

                        // Adicionar título ao array de strings

                        getMovieList(title, movieTitle);
                    }

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

        // Adicionar request ao requestQueue

        requestQueue.add(stringRequest);
    }

    private void getMovieList(String title, ArrayList<String> movieTitle) {
        movieTitle.add(title);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movieTitle);
        listMovies.setAdapter(arrayAdapter);
    }
}