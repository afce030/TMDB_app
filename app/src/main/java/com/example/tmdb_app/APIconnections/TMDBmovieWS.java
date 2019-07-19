package com.example.tmdb_app.APIconnections;

import com.example.tmdb_app.PojoClasses.ConsultaPeliculas.SearchResultsMovies;
import com.example.tmdb_app.PojoClasses.ConsultaTV.SearchResultsTV;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBmovieWS {

    //Obteniendo películas populares, top o upcoming
    @GET("movie/{category}")
    Call<SearchResultsMovies> getMoviesByType(
            @Path("category") String category,
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") long page
    );

    //Obteniendo series populares, top o upcoming
    @GET("search/movie")
    Call<SearchResultsMovies> getMoviesByQuery(
            @Query("query") String pattern,
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") long page
    );

}
