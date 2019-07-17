package com.example.tmdb_app.APIconnections;

import com.example.tmdb_app.PojoClasses.ConsultaTrailers.TrailerResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrailersWS {

    //Bucador de trailers
    @GET("movie/{movie_id}/videos")
    Call<TrailerResults> getMovieTrailer(
            @Path("movie_id") String idMovie,
            @Query("api_key") String key,
            @Query("language") String language
    );

}
