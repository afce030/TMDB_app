package com.example.tmdb_app.Business.Viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.tmdb_app.LocalData.RoomEntities.GenresEntity;
import com.example.tmdb_app.LocalData.RoomEntities.SeriesEntity;
import com.example.tmdb_app.Repositories.MoviesRepo;
import com.example.tmdb_app.LocalData.RoomEntities.MoviesEntity;
import com.example.tmdb_app.Repositories.SeriesRepo;

import java.util.List;

public class SearchActivityViewModel extends AndroidViewModel{

    private MoviesRepo moviesRepo;
    private LiveData<List<MoviesEntity>> movies;
    private LiveData<List<MoviesEntity>> moviesbyQuery;
    private LiveData<List<GenresEntity>> genresMovies;

    private SeriesRepo seriesRepo;
    private LiveData<List<SeriesEntity>> series;
    private LiveData<List<SeriesEntity>> seriesbyQuery;
    private LiveData<List<GenresEntity>> genresSeries;


    private MutableLiveData<String> category = new MutableLiveData<String>();
    private MutableLiveData<Integer> page = new MutableLiveData<Integer>();
    private MutableLiveData<String> searchPattern = new MutableLiveData<String>();

    public SearchActivityViewModel(@NonNull Application application) {
        super(application);
        moviesRepo = new MoviesRepo(application);
        seriesRepo = new SeriesRepo(application);

        category.setValue("popular");
        page.setValue(1);

        moviesbyQuery = Transformations.switchMap(searchPattern, c -> moviesRepo.SearchMovies(c));
        movies = Transformations.switchMap(page, c -> moviesRepo.getMoviesByCategory(category.getValue(),c));
        genresMovies = moviesRepo.GenresByLanguage();

        seriesbyQuery = Transformations.switchMap(searchPattern, c -> seriesRepo.SearchSeries(c));
        series = Transformations.switchMap(page, c -> seriesRepo.getSeriesByCategory(category.getValue(),c));
        genresSeries = seriesRepo.GenresByLanguage();
    }


    public LiveData<List<MoviesEntity>> getMovies() {
        return movies;
    }
    public LiveData<List<MoviesEntity>> getMoviesbyQuery() {
        return moviesbyQuery;
    }
    public LiveData<List<GenresEntity>> getGenresMovies() {
        return genresMovies;
    }



    public LiveData<List<SeriesEntity>> getSeries() {
        return series;
    }
    public LiveData<List<SeriesEntity>> getSeriesbyQuery() {
        return seriesbyQuery;
    }
    public LiveData<List<GenresEntity>> getGenresSeries() {
        return genresSeries;
    }


    public void setCategory(String category) {
        this.category.setValue(category);
    }
    public void setSearchPattern(String newSearchPattern) {
        this.searchPattern.setValue(newSearchPattern);
    }
    public void setPage(Integer page) {
        this.page.setValue(page);
    }

}
