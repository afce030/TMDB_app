package com.example.tmdb_app.Injection.daggerComp;

import com.example.tmdb_app.Injection.daggerMod.RetrofitModule;
import com.example.tmdb_app.Activities.MovieVisorX;
import com.example.tmdb_app.Repositories.MoviesRepo;
import com.example.tmdb_app.Activities.SearchActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    void inject(SearchActivity searchActivity);
    void inject(MovieVisorX movieVisorX);
    void inject(MoviesRepo moviesRepo);
}
