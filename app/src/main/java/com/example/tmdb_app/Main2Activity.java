package com.example.tmdb_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.tmdb_app.APIconnections.TMDBservice;
import com.example.tmdb_app.Adapters.MoviesAdapter;
import com.example.tmdb_app.Classes.SearchResults;
import com.example.tmdb_app.Classes.TMDBmovie;
import com.example.tmdb_app.Constants.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView rvMovies;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rvMovies = findViewById(R.id.rvMovies);
        getMovies("popular","en-US", 1);

    }

    void getMovies(String category, String language, long page){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDBservice service = retrofit.create(TMDBservice.class);

        Call<SearchResults> call = service.getMoviesByType(category,Constants.api_key,language,page);

        final TaskCompletionSource<List<TMDBmovie>> completionSource = new TaskCompletionSource<>();

        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {

                SearchResults search = response.body();

                completionSource.setResult(search.getResults());

            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {

            }
        });

        Task<List<TMDBmovie>> task = completionSource.getTask();

        task.addOnSuccessListener(new OnSuccessListener<List<TMDBmovie>>() {
            @Override
            public void onSuccess(List<TMDBmovie> tmdBmovies) {

                moviesAdapter = new MoviesAdapter(tmdBmovies);
                LinearLayoutManager l = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);

                //Se añade un DividerItemDecoration para aumentar la distancia entre las categorias
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),RecyclerView.VERTICAL);
                dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.item_decoration_categories));

                rvMovies.setLayoutManager(l);
                rvMovies.addItemDecoration(dividerItemDecoration);
                rvMovies.setAdapter(moviesAdapter);

                Toast.makeText(Main2Activity.this, "ooo", Toast.LENGTH_SHORT).show();

            }
        });

    }
}