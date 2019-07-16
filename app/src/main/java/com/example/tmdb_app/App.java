package com.example.tmdb_app;

import android.app.Application;

import com.example.tmdb_app.Components.DaggerRetrofitComponent;
import com.example.tmdb_app.Components.RetrofitComponent;
import com.example.tmdb_app.Modules.RetrofitModule;

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
