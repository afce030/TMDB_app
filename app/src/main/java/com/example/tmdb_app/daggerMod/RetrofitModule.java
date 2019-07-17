package com.example.tmdb_app.daggerMod;

import com.example.tmdb_app.Classes.ConsultaGeneros.GenresWS;
import com.example.tmdb_app.Classes.ConsultaHibrida.MultiContentWS;
import com.example.tmdb_app.Classes.ConsultaPeliculas.TMDBmovieWS;
import com.example.tmdb_app.Classes.ConsultaTrailers.TrailersWS;
import com.example.tmdb_app.Utilities.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    @Provides
    @Singleton
    GsonConverterFactory provideGsonFactory(){
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit Rservice(GsonConverterFactory gson){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.base)
                .addConverterFactory(gson)
                .build();

        return retrofit;
    }

    @Provides
    @Singleton
    TMDBmovieWS provideMovie(Retrofit retrofit){
        TMDBmovieWS service = retrofit.create(TMDBmovieWS.class);
        return service;
    }

    @Provides
    @Singleton
    GenresWS provideGenre(Retrofit retrofit){
        GenresWS service = retrofit.create(GenresWS.class);
        return service;
    }

    @Provides
    @Singleton
    TrailersWS provideTrailer(Retrofit retrofit){
        TrailersWS service = retrofit.create(TrailersWS.class);
        return service;
    }

    @Provides
    @Singleton
    MultiContentWS provideContent(Retrofit retrofit){
        MultiContentWS service = retrofit.create(MultiContentWS.class);
        return service;
    }

}
