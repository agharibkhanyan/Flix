package com.example.flix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flix.Adapters.movieAdapter;
import com.example.flix.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    //create movie data string to use for access
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";

    List<Movie> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        //create the adapter
        final movieAdapter MovieAdapter = new movieAdapter(this,movies);

        //set the adapter on the recycler view
        rvMovies.setAdapter(MovieAdapter);

        //set the layout manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        //create an async http request to get the movie data
        AsyncHttpClient client = new AsyncHttpClient();
        //use json http request since we know they are compatable
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: "+ results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    MovieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: "+ movies.size());

                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    //e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG,"onFailure");
            }
        });
    }
}
