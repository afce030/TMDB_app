package com.example.tmdb_app.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.tmdb_app.PojoClasses.ConsultaPeliculas.SearchResultsMovies;
import com.example.tmdb_app.PojoClasses.ConsultaPeliculas.TMDBmovie;
import com.example.tmdb_app.APIconnections.TMDBmovieWS;
import com.example.tmdb_app.LocalData.RoomDAO.MoviesDAO;
import com.example.tmdb_app.LocalData.RoomDatabases.MoviesDatabase;
import com.example.tmdb_app.LocalData.RoomEntities.MoviesEntity;
import com.example.tmdb_app.Utilities.Constants;
import com.example.tmdb_app.Injection.daggerComp.DaggerRetrofitComponent;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepo {
    private MoviesDAO moviesDAO;
    private LiveData<List<MoviesEntity>> allMovies;

    @Inject
    TMDBmovieWS tmdBmovieWS;//This parameter is injected by the Retrofit Module

    public MoviesRepo(Application application) {
        DaggerRetrofitComponent.create().inject(this);

        MoviesDatabase moviesDatabase = MoviesDatabase.getInstance(application);
        moviesDAO = moviesDatabase.moviesDAO();
        allMovies = moviesDAO.getPopularMovies();

    }

    public LiveData<List<MoviesEntity>> getPopular(){
        getPopularWS();
        return allMovies;
    }

    public void getPopularWS(){
        Call<SearchResultsMovies> call = tmdBmovieWS.getMoviesByType("popular", Constants.API_KEY,"es",1);

        call.enqueue(new Callback<SearchResultsMovies>() {
            @Override
            public void onResponse(Call<SearchResultsMovies> call, Response<SearchResultsMovies> response) {

                List<TMDBmovie> g = response.body().getResults();

                for(TMDBmovie i: g) {

                    LiveData<List<MoviesEntity>> x = moviesDAO.getMovieByID(i.getId());

                    int top = 0;
                    int upcoming = 0;

                    if(x.getValue() != null){
                        top = x.getValue().get(0).isTop();
                        upcoming = x.getValue().get(0).isUpcoming();
                    }

                    MoviesEntity m = new MoviesEntity(
                            i.getId(),
                            i.getVoteCount(),
                            i.getVideo(),
                            i.getVoteAverage(),
                            i.getTitle(),
                            i.getPopularity(),
                            i.getPosterPath(),
                            i.getOriginalLanguage(),
                            i.getOriginalTitle(),
                            i.getGenreIds(),
                            i.getBackdropPath(),
                            i.getAdult(),
                            i.getOverview(),
                            i.getReleaseDate(),
                            1,
                            top,
                            upcoming);

                    new insertBackground(moviesDAO).execute(m);

                }

                allMovies = moviesDAO.getPopularMovies();
            }

            @Override
            public void onFailure(Call<SearchResultsMovies> call, Throwable t) {

            }
        });
    }

    private static class insertBackground extends AsyncTask<MoviesEntity, Void, Void> {

        private MoviesDAO moviesDAO;

        public insertBackground(MoviesDAO moviesDAO) {
            this.moviesDAO = moviesDAO;
        }

        @Override
        protected Void doInBackground(MoviesEntity... moviesEntities) {
            moviesDAO.insertMovie(moviesEntities[0]);
            return null;
        }
    }
}
