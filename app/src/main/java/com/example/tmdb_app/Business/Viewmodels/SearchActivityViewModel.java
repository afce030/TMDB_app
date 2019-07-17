package com.example.tmdb_app.Business.Viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tmdb_app.Repositories.MoviesRepo;
import com.example.tmdb_app.LocalData.RoomEntities.MoviesEntity;

import java.util.List;

public class SearchActivityViewModel extends AndroidViewModel{

    private LiveData<List<MoviesEntity>> movies;
    private MoviesRepo moviesRepo;

    public SearchActivityViewModel(@NonNull Application application) {
        super(application);
        moviesRepo = new MoviesRepo(application);
        movies = moviesRepo.getPopular();
    }


    public LiveData<List<MoviesEntity>> getMovies() {
        return movies;
    }

}
