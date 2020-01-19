package com.example.flix.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    String backgroundPath;
    String postPath;
    String title;
    String overview;

    //throw a json exception so the called is responsible of handling the exception
    public Movie (JSONObject jsonObject) throws JSONException {
        backgroundPath = jsonObject.getString("backdrop_path");
        postPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }

    //create json array of movies to send back
    public static List<Movie> fromJsonArray (JSONArray movieArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i=0; i<movieArray.length();i++){
            movies.add(new Movie(movieArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPostPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",postPath);
    }

    public String getBackgroundPath()
    {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backgroundPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}

