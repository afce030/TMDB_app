package com.example.tmdb_app.Injection.daggerComp;

import com.example.tmdb_app.Injection.daggerMod.RetrofitModule;
import com.example.tmdb_app.Activities.MovieVisor;
import com.example.tmdb_app.Repositories.MoviesRepo;
import com.example.tmdb_app.Repositories.SeriesRepo;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    void inject(MovieVisor movieVisor);
    void inject(MoviesRepo moviesRepo);
    void inject(SeriesRepo seriesRepo);
}
