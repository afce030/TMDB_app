package com.example.tmdb_app.APIconnections;

import com.example.tmdb_app.Classes.ConsultaGeneros.GenreClass;
import com.example.tmdb_app.Classes.ConsultaPeliculas.SearchResultsMovies;
import com.example.tmdb_app.Classes.ConsultaHibrida.SearchResultsMulti;
import com.example.tmdb_app.Classes.ConsultaPeliculas.TrailerResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBservice {

    @GET("movie/{category}")
    Call<SearchResultsMovies> getMoviesByType(
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

    //Método de buscador
    @GET("search/multi")
    Call<SearchResultsMulti> getPatternResults(
            @Query("api_key") String key,
            @Query("query") String pattern
    );

    @GET("movie/{movie_id}/videos")
    Call<TrailerResults> getMovieTrailer(
            @Path("movie_id") String idMovie,
            @Query("api_key") String key,
            @Query("language") String language
    );

}
