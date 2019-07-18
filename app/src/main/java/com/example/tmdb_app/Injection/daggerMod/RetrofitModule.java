package com.example.tmdb_app.Injection.daggerMod;

import com.example.tmdb_app.APIconnections.GenresWS;
import com.example.tmdb_app.APIconnections.MultiContentWS;
import com.example.tmdb_app.APIconnections.TMDBmovieWS;
import com.example.tmdb_app.APIconnections.TMDBserieWS;
import com.example.tmdb_app.APIconnections.TrailersWS;
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
                .baseUrl(Constants.BASE)
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
    TMDBserieWS provideSerie(Retrofit retrofit){
        TMDBserieWS service = retrofit.create(TMDBserieWS.class);
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
