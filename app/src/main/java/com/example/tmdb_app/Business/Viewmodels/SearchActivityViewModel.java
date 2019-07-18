package com.example.tmdb_app.Business.Viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tmdb_app.LocalData.RoomEntities.GenresEntity;
import com.example.tmdb_app.LocalData.RoomEntities.SeriesEntity;
import com.example.tmdb_app.Repositories.MoviesRepo;
import com.example.tmdb_app.LocalData.RoomEntities.MoviesEntity;
import com.example.tmdb_app.Repositories.SeriesRepo;

import java.util.List;

public class SearchActivityViewModel extends AndroidViewModel{

    private MoviesRepo moviesRepo;
    private LiveData<List<MoviesEntity>> movies;
    private MutableLiveData<String> category = new MutableLiveData<String>();
    private MutableLiveData<Integer> page = new MutableLiveData<Integer>();
    private LiveData<List<GenresEntity>> genresMovies;


    private LiveData<List<SeriesEntity>> series;
    private SeriesRepo seriesRepo;


    public SearchActivityViewModel(@NonNull Application application) {
        super(application);
        moviesRepo = new MoviesRepo(application);

        category.setValue("popular");
        page.setValue(1);
        movies = moviesRepo.getMoviesByCategory(category.getValue(), page.getValue());
        genresMovies = moviesRepo.GenresByLanguage();
    }


    public LiveData<List<MoviesEntity>> getMovies() {
        movies = moviesRepo.getMoviesByCategory(category.getValue(), page.getValue());
        return movies;
    }
    public LiveData<List<GenresEntity>> getGenresMovies() {
        return genresMovies;
    }
    public void setCategory(String category) {
        this.category.setValue(category);
        movies = moviesRepo.getMoviesByCategory(this.category.getValue(), page.getValue());
    }
    public void setPage(Integer page) {
        this.page.setValue(page);
        movies = moviesRepo.getMoviesByCategory(category.getValue(), this.page.getValue());
    }


}
