package com.example.tmdb_app.Classes.ConsultaGeneros;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GenresWS {

    //Obtener los géneros
    @GET("genre/movie/list")
    Call<GenreResults> getMovieGenres(
            @Query("api_key") String key,
            @Query("language") String language
    );

}
