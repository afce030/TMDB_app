package com.example.tmdb_app.Activities;

import android.app.Application;

import com.example.tmdb_app.Injection.daggerComp.DaggerRetrofitComponent;
import com.example.tmdb_app.Injection.daggerComp.RetrofitComponent;

public class App extends Application {

    private RetrofitComponent retrofitComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofitComponent = DaggerRetrofitComponent.builder().build();
    }

    public RetrofitComponent getRetrofitComponent() {
        return retrofitComponent;
    }
}
