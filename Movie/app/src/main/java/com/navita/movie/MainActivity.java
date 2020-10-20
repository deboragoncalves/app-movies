package com.navita.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listMovies = (ListView) findViewById(R.id.list_movies);

        final ArrayList<String> movieTitle = new ArrayList<String>();

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

                        Log.d("=======titulo", title);

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
                Log.d("error", ""+error);
            }
        });

        // Adicionar request ao requestQueue

        requestQueue.add(stringRequest);

        Log.d("=======movieTitle", ""+movieTitle);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movieTitle);
        listMovies.setAdapter(arrayAdapter);
    }

    public ArrayList<String> getMovieList(String title, ArrayList<String> movieTitle) {
        movieTitle.add(title);
        return movieTitle;
    }
}