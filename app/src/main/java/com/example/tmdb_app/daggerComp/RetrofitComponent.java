package com.example.tmdb_app.daggerComp;

import com.example.tmdb_app.daggerMod.RetrofitModule;
import com.example.tmdb_app.MovieVisorX;
import com.example.tmdb_app.Repositories.MoviesRepo;
import com.example.tmdb_app.SearchActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    void inject(SearchActivity searchActivity);
    void inject(MovieVisorX movieVisorX);
    void inject(MoviesRepo moviesRepo);
}
