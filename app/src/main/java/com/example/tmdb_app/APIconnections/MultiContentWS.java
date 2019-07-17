package com.example.tmdb_app.APIconnections;

import com.example.tmdb_app.PojoClasses.ConsultaHibrida.SearchResultsMulti;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MultiContentWS {

    //Método de buscador
    @GET("search/multi")
    Call<SearchResultsMulti> getPatternResults(
            @Query("api_key") String key,
            @Query("query") String pattern
    );

}
