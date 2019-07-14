package com.example.tmdb_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tmdb_app.APIconnections.TMDBservice;
import com.example.tmdb_app.Classes.SearchResults;
import com.example.tmdb_app.Classes.TMDBmovie;
import com.example.tmdb_app.Constants.Constants;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {

    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        texto = findViewById(R.id.texto);
        getPopularM();
    }

    void getPopularM(){

        Toast.makeText(Main2Activity.this, "Preparado", Toast.LENGTH_SHORT).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDBservice service = retrofit.create(TMDBservice.class);

        Call<SearchResults> call = service.getPopular(Constants.api_key,"en-US",1);

        Toast.makeText(Main2Activity.this, "Listooooooooo", Toast.LENGTH_SHORT).show();

        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {

                SearchResults search = response.body();

                String txt = "";
                for(TMDBmovie t: search.getResults()){
                    txt += t.getTitle() + "\n";
                }

                texto.setText(txt);

            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {

            }
        });
    }
}
