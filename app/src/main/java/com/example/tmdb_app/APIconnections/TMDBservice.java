package com.example.tmdb_app.APIconnections;

import com.example.tmdb_app.Classes.GenreClass;
import com.example.tmdb_app.Classes.SearchResults;
import com.example.tmdb_app.Classes.TMDBmovie;
import com.example.tmdb_app.Constants.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBservice {

    @GET("movie/{category}")
    Call<SearchResults> getMoviesByType(
            @Path("category") String category,
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") long page
    );

    @GET("genre/movie/list")
    Call<GenreClass> getMovieGenres(
            @Query("api_key") String key,
            @Query("language") String language
    );

}
