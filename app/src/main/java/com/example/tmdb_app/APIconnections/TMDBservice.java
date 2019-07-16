package com.example.tmdb_app.APIconnections;

import com.example.tmdb_app.Classes.ConsultaGeneros.GenreResults;
import com.example.tmdb_app.Classes.ConsultaPeliculas.SearchResultsMovies;
import com.example.tmdb_app.Classes.ConsultaHibrida.SearchResultsMulti;
import com.example.tmdb_app.Classes.ConsultaTrailers.TrailerResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
Interfaz utlizada para consumir la api

Fecha: 15/07/2919
Elaborado por: Andrés Cardona
*/

public interface TMDBservice {

    //Obteniendo películas populares, top o upcoming
    @GET("movie/{category}")
    Call<SearchResultsMovies> getMoviesByType(
            @Path("category") String category,
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") long page
    );

    //Obtener los géneros
    @GET("genre/movie/list")
    Call<GenreResults> getMovieGenres(
            @Query("api_key") String key,
            @Query("language") String language
    );

    //Método de buscador
    @GET("search/multi")
    Call<SearchResultsMulti> getPatternResults(
            @Query("api_key") String key,
            @Query("query") String pattern
    );

    //Bucador de trailers
    @GET("movie/{movie_id}/videos")
    Call<TrailerResults> getMovieTrailer(
            @Path("movie_id") String idMovie,
            @Query("api_key") String key,
            @Query("language") String language
    );

}
