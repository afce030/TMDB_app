package com.example.tmdb_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.tmdb_app.APIconnections.TMDBservice;
import com.example.tmdb_app.Adapters.MoviesAdapter;
import com.example.tmdb_app.Adapters.MultiContentAdapter;
import com.example.tmdb_app.Classes.ConsultaGeneros.GenreClass;
import com.example.tmdb_app.Classes.ConsultaGeneros.Genre_ids;
import com.example.tmdb_app.Classes.ConsultaHibrida.MultiContent;
import com.example.tmdb_app.Classes.ConsultaPeliculas.SearchResultsMovies;
import com.example.tmdb_app.Classes.ConsultaHibrida.SearchResultsMulti;
import com.example.tmdb_app.Classes.ConsultaPeliculas.TMDBmovie;
import com.example.tmdb_app.Constants.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView rvMovies;
    private RecyclerView rvQuery;

    private MoviesAdapter moviesAdapter = new MoviesAdapter(SearchActivity.this, new ArrayList<>(), new ArrayList<>());
    private MultiContentAdapter multiContentAdapter = new MultiContentAdapter(SearchActivity.this, new ArrayList<>());

    private String Lang = "es"; //Default language
    private List<Genre_ids> MoviesGenres = new ArrayList<>();

    int current = 1;

    private LinearLayout lySearch;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_movie).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.length() > 0) {
                    lySearch.setVisibility(View.VISIBLE);
                    lySearch.bringToFront();
                    getMultiContent(newText);
                    return true;
                }else{
                    lySearch.setVisibility(View.INVISIBLE);
                }

                return false;
            }
        });

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        lySearch = findViewById(R.id.lySearchTool);
        lySearch.setVisibility(View.INVISIBLE);

        Toolbar myToolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        //Adicionando el botón back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intencion = getIntent();

        rvQuery = findViewById(R.id.rvQuery);
        rvMovies = findViewById(R.id.rvMovies);
        getGenres(Lang);

        final String cat = intencion.getStringExtra("category");
        getMovies(cat,Lang, current);

        LinearLayoutManager l = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);

        //Se añade un DividerItemDecoration para aumentar la distancia entre las categorias
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),RecyclerView.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.item_decoration_categories));

        rvMovies.setLayoutManager(l);
        rvMovies.addItemDecoration(dividerItemDecoration);
        rvMovies.setAdapter(moviesAdapter);

        LinearLayoutManager l2 = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        //Se añade un DividerItemDecoration para aumentar la distancia entre las categorias
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(getApplicationContext(),RecyclerView.VERTICAL);
        dividerItemDecoration2.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.item_decoration_categories));

        rvQuery.setLayoutManager(l2);
        rvQuery.addItemDecoration(dividerItemDecoration2);
        rvQuery.setAdapter(multiContentAdapter);

        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    // Bajando
                    if(recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN) == false){
                        //Final
                        current += 1;
                        getMovies(cat,Lang, current);
                    }
                }
            }
        });

    }

    void getGenres(String language){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDBservice service = retrofit.create(TMDBservice.class);

        Call<GenreClass> call = service.getMovieGenres(Constants.api_key,language);

        final TaskCompletionSource<List<Genre_ids>> completionSource = new TaskCompletionSource<>();
        call.enqueue(new Callback<GenreClass>() {
            @Override
            public void onResponse(Call<GenreClass> call, Response<GenreClass> response) {

                GenreClass genreClass = response.body();
                completionSource.setResult(genreClass.getGenres());

            }

            @Override
            public void onFailure(Call<GenreClass> call, Throwable t) {

            }
        });

        Task<List<Genre_ids>> task = completionSource.getTask();

        task.addOnSuccessListener(new OnSuccessListener<List<Genre_ids>>() {
            @Override
            public void onSuccess(List<Genre_ids> genre_ids) {

                MoviesGenres.addAll(genre_ids);
                moviesAdapter.setGenres(MoviesGenres);

            }
        });

    }

    void getMovies(String category, String language, long page){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDBservice service = retrofit.create(TMDBservice.class);

        Call<SearchResultsMovies> call = service.getMoviesByType(category,Constants.api_key,language,page);

        final TaskCompletionSource<List<TMDBmovie>> completionSource = new TaskCompletionSource<>();

        call.enqueue(new Callback<SearchResultsMovies>() {
            @Override
            public void onResponse(Call<SearchResultsMovies> call, Response<SearchResultsMovies> response) {

                SearchResultsMovies search = response.body();
                completionSource.setResult(search.getResults());

            }

            @Override
            public void onFailure(Call<SearchResultsMovies> call, Throwable t) {

            }
        });

        Task<List<TMDBmovie>> task = completionSource.getTask();

        task.addOnSuccessListener(new OnSuccessListener<List<TMDBmovie>>() {
            @Override
            public void onSuccess(List<TMDBmovie> tmdBmovies) {

                moviesAdapter.addElements(tmdBmovies);

            }
        });

    }

    void getMultiContent(String pattern){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDBservice service = retrofit.create(TMDBservice.class);

        Call<SearchResultsMulti> call = service.getPatternResults(Constants.api_key, pattern);

        final TaskCompletionSource<List<MultiContent>> completionSource = new TaskCompletionSource<>();
        call.enqueue(new Callback<SearchResultsMulti>() {
            @Override
            public void onResponse(Call<SearchResultsMulti> call, Response<SearchResultsMulti> response) {

                SearchResultsMulti search = response.body();
                completionSource.setResult(search.getResults());

            }

            @Override
            public void onFailure(Call<SearchResultsMulti> call, Throwable t) {

            }
        });

        Task<List<MultiContent>> task = completionSource.getTask();
        task.addOnSuccessListener(new OnSuccessListener<List<MultiContent>>() {
            @Override
            public void onSuccess(List<MultiContent> tmdBContent) {

                multiContentAdapter.ModifyContents(tmdBContent);
                multiContentAdapter.setGenres(MoviesGenres);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        rvMovies.clearOnScrollListeners();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
