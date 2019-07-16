package com.example.tmdb_app.Classes.ConsultaPeliculas;

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

}
