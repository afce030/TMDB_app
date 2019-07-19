package com.example.tmdb_app.APIconnections;

import com.example.tmdb_app.PojoClasses.ConsultaTV.SearchResultsTV;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBserieWS {

    //Obteniendo series populares, top o upcoming
    @GET("tv/{category}")
    Call<SearchResultsTV> getSeriesByType(
            @Path("category") String category,
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") long page
    );

    //Obteniendo series populares, top o upcoming
    @GET("search/tv")
    Call<SearchResultsTV> getSeriesByQuery(
            @Query("query") String pattern,
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") long page
    );

}
