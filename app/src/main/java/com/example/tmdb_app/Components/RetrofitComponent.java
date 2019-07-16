package com.example.tmdb_app.Components;

import com.example.tmdb_app.Modules.RetrofitModule;
import com.example.tmdb_app.MovieVisorX;
import com.example.tmdb_app.SearchActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    void inject(SearchActivity searchActivity);
    void inject(MovieVisorX movieVisorX);
}
