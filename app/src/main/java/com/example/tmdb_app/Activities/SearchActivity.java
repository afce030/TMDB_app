package com.example.tmdb_app.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
import android.widget.Toast;

import com.example.tmdb_app.Activities.Adapters.MoviesAdapter;
import com.example.tmdb_app.Activities.Adapters.MultiContentAdapter;
import com.example.tmdb_app.Activities.Adapters.SeriesAdapter;
import com.example.tmdb_app.LocalData.RoomEntities.GenresEntity;
import com.example.tmdb_app.LocalData.RoomEntities.SeriesEntity;
import com.example.tmdb_app.PojoClasses.ConsultaGeneros.Genre_ids;
import com.example.tmdb_app.PojoClasses.ConsultaHibrida.MultiContent;
import com.example.tmdb_app.APIconnections.MultiContentWS;
import com.example.tmdb_app.PojoClasses.ConsultaHibrida.SearchResultsMulti;
import com.example.tmdb_app.R;
import com.example.tmdb_app.LocalData.RoomEntities.MoviesEntity;
import com.example.tmdb_app.Utilities.Constants;
import com.example.tmdb_app.Business.Viewmodels.SearchActivityViewModel;
import com.example.tmdb_app.Injection.daggerComp.DaggerRetrofitComponent;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    @Inject
    MultiContentWS searchService;

    private RecyclerView rvMovies;
    private RecyclerView rvQuery;

    private MoviesAdapter moviesAdapter = new MoviesAdapter(SearchActivity.this, new ArrayList<>(), new ArrayList<>());
    private SeriesAdapter seriesAdapter = new SeriesAdapter(SearchActivity.this, new ArrayList<>(), new ArrayList<>());
    private MultiContentAdapter multiContentAdapter = new MultiContentAdapter(SearchActivity.this, "series");

    private List<Genre_ids> MoviesGenres = new ArrayList<>();
    int current = 1;

    private LinearLayout lySearch;
    private SearchActivityViewModel searchActivityViewModel;

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
                    searchActivityViewModel.setSearchPattern(newText);
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
        DaggerRetrofitComponent.create().inject(this);


        Toolbar myToolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        //Adicionando el botón back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        lySearch = findViewById(R.id.lySearchTool);
        lySearch.setVisibility(View.INVISIBLE);


        Intent intencion = getIntent();
        final String cat = intencion.getStringExtra("category");
        final String typ = intencion.getStringExtra("type");

        rvQuery = findViewById(R.id.rvQuery);
        rvMovies = findViewById(R.id.rvMovies);

        LinearLayoutManager l = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        //Se añade un DividerItemDecoration para aumentar la distancia entre las categorias
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),RecyclerView.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.item_decoration_categories));
        rvMovies.setLayoutManager(l);
        rvMovies.addItemDecoration(dividerItemDecoration);

        LinearLayoutManager l2 = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        //Se añade un DividerItemDecoration para aumentar la distancia entre las categorias
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(getApplicationContext(),RecyclerView.VERTICAL);
        dividerItemDecoration2.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.item_decoration_categories));

        rvQuery.setLayoutManager(l2);
        rvQuery.addItemDecoration(dividerItemDecoration2);
        rvQuery.setAdapter(multiContentAdapter);

        searchActivityViewModel = ViewModelProviders.of(this).get(SearchActivityViewModel.class);
        searchActivityViewModel.setCategory(cat);
        if(typ.equals("movies")){
            rvMovies.setAdapter(moviesAdapter);

            searchActivityViewModel.getMovies().observe(this, new Observer<List<MoviesEntity>>() {
                @Override
                public void onChanged(List<MoviesEntity> moviesEntities) {
                    moviesAdapter.addElements(moviesEntities);
                }
            });

            searchActivityViewModel.getGenresMovies().observe(this, new Observer<List<GenresEntity>>() {
                @Override
                public void onChanged(List<GenresEntity> genresEntities) {
                    moviesAdapter.setGenres(genresEntities);
                }
            });

            multiContentAdapter.setMode(typ);
            searchActivityViewModel.getMoviesbyQuery().observe(this, new Observer<List<MoviesEntity>>() {
                @Override
                public void onChanged(List<MoviesEntity> moviesEntities) {
                    multiContentAdapter.ModifyContents(moviesEntities, new ArrayList<>());
                }
            });

        }
        else if(typ.equals("series")){
            rvMovies.setAdapter(seriesAdapter);

            searchActivityViewModel.getSeries().observe(this, new Observer<List<SeriesEntity>>() {
                @Override
                public void onChanged(List<SeriesEntity> seriesEntities) {
                    seriesAdapter.addElements(seriesEntities);
                 }
            });

            searchActivityViewModel.getGenresSeries().observe(this, new Observer<List<GenresEntity>>() {
                @Override
                public void onChanged(List<GenresEntity> genresEntities) {
                    seriesAdapter.setGenres(genresEntities);
                }
            });

            multiContentAdapter.setMode(typ);
            searchActivityViewModel.getSeriesbyQuery().observe(this, new Observer<List<SeriesEntity>>() {
                @Override
                public void onChanged(List<SeriesEntity> seriesEntities) {
                    multiContentAdapter.ModifyContents(new ArrayList<>(), seriesEntities);
                }
            });

        }

        //cargar mas peliculas o series
        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    // Bajando
                    if(recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN) == false){
                        //Final
                        current += 1;
                        searchActivityViewModel.setPage(current);
                    }
                }
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
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
