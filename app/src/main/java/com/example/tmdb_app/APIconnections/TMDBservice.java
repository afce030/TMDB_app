package com.example.tmdb_app.APIconnections;

import com.example.tmdb_app.Classes.SearchResults;
import com.example.tmdb_app.Classes.TMDBmovie;
import com.example.tmdb_app.Constants.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBservice {

    @GET(Constants.popular)
    Call<SearchResults> getPopular(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") long page
    );

    @GET(Constants.top_rated)
    Call<SearchResults> getTopRated(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") long page
    );

    @GET(Constants.upcoming)
    Call<SearchResults> getUpcoming(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") long page
    );

}
