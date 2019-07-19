package com.example.tmdb_app.Repositories;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tmdb_app.APIconnections.GenresWS;
import com.example.tmdb_app.APIconnections.TrailersWS;
import com.example.tmdb_app.LocalData.RoomEntities.GenresEntity;
import com.example.tmdb_app.PojoClasses.ConsultaGeneros.GenreResults;
import com.example.tmdb_app.PojoClasses.ConsultaGeneros.Genre_ids;
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
    private LiveData<List<MoviesEntity>> foundMovies;
    private LiveData<List<GenresEntity>> foundGenres;

    @Inject
    TMDBmovieWS tmdBmovieWS;//This parameter is injected by the Retrofit Module

    @Inject
    GenresWS genresWS;//This parameter is injected by the Retrofit Module

    @Inject
    TrailersWS trailersWS;//This parameter is injected by the Retrofit Module


    public MoviesRepo(Application application) {
        DaggerRetrofitComponent.create().inject(this);

        MoviesDatabase moviesDatabase = MoviesDatabase.getInstance(application);
        moviesDAO = moviesDatabase.moviesDAO();

        foundGenres = GenresByLanguage();
        foundMovies = moviesDAO.getPopularMovies();
    }


    public LiveData<List<MoviesEntity>> getMoviesByCategory(String category, Integer page){
        refreshMovies(category, page);

        switch (category){
            case "popular":
                foundMovies = moviesDAO.getPopularMovies();
                break;
            case "top_rated":
                foundMovies = moviesDAO.getTopMovies();
                break;
            case "upcoming":
                foundMovies = moviesDAO.getUpcomingMovies();
                break;
        }

        return foundMovies;
    }
    void refreshMovies(String category, int page){
        Call<SearchResultsMovies> call = tmdBmovieWS.getMoviesByType(category, Constants.API_KEY,"es",page);

        call.enqueue(new Callback<SearchResultsMovies>() {
            @Override
            public void onResponse(Call<SearchResultsMovies> call, Response<SearchResultsMovies> response) {

                List<TMDBmovie> g = response.body().getResults();

                for(TMDBmovie item: g) {

                    int popular = 0;
                    int top = 0;
                    int upcoming = 0;

                    switch (category){
                        case "popular":
                            popular = 1;
                            break;
                        case "top_rated":
                            top = 1;
                            break;
                        case "upcoming":
                            upcoming = 1;
                            break;
                    }

                    MoviesEntity moviesEntity = new MoviesEntity(
                            item.getId(),
                            item.getVoteCount(),
                            item.getVideo(),
                            item.getVoteAverage(),
                            item.getTitle(),
                            item.getPopularity(),
                            item.getPosterPath(),
                            item.getOriginalLanguage(),
                            item.getOriginalTitle(),
                            item.getGenreIds(),
                            item.getBackdropPath(),
                            item.getAdult(),
                            item.getOverview(),
                            item.getReleaseDate(),
                            popular,
                            top,
                            upcoming,
                            page);

                    new insertMoviesBackground(moviesDAO, category).execute(moviesEntity);
                }
            }

            @Override
            public void onFailure(Call<SearchResultsMovies> call, Throwable t) {

            }
        });

    }


    public LiveData<List<GenresEntity>> GenresByLanguage(){
        refreshGenres("es");
        return moviesDAO.obtainGenres();
    }
    void refreshGenres(String language){
        Call<GenreResults> call = genresWS.getMovieGenres(Constants.API_KEY, language);

        call.enqueue(new Callback<GenreResults>() {
            @Override
            public void onResponse(Call<GenreResults> call, Response<GenreResults> response) {

                List<Genre_ids> g = response.body().getGenres();

                for(Genre_ids item : g){

                    GenresEntity genresEntity = new GenresEntity(item.getId(), item.getName());

                    new insertGenresBackground(moviesDAO).execute(genresEntity);

                }

            }

            @Override
            public void onFailure(Call<GenreResults> call, Throwable t) {

            }
        });

    }



    public LiveData<List<MoviesEntity>> SearchMovies(String pattern){
        refreshMoviesByPattern(pattern);
        return moviesDAO.getpattern("%"+pattern+"%");
    }
    void refreshMoviesByPattern(String pattern){

        Call<SearchResultsMovies> call = tmdBmovieWS.getMoviesByQuery(pattern, Constants.API_KEY,"es",1);
        call.enqueue(new Callback<SearchResultsMovies>() {
            @Override
            public void onResponse(Call<SearchResultsMovies> call, Response<SearchResultsMovies> response) {

                if(response.isSuccessful()){

                    List<TMDBmovie> g = response.body().getResults();
                    for(TMDBmovie item: g) {

                        int popular = 0;
                        int top = 0;
                        int upcoming = 0;
                        int pagina = 1;

                        MoviesEntity moviesEntity = new MoviesEntity(
                                item.getId(),
                                item.getVoteCount(),
                                item.getVideo(),
                                item.getVoteAverage(),
                                item.getTitle(),
                                item.getPopularity(),
                                item.getPosterPath(),
                                item.getOriginalLanguage(),
                                item.getOriginalTitle(),
                                item.getGenreIds(),
                                item.getBackdropPath(),
                                item.getAdult(),
                                item.getOverview(),
                                item.getReleaseDate(),
                                popular,
                                top,
                                upcoming,
                                pagina);

                        new insertMoviesBackground(moviesDAO, "").execute(moviesEntity);

                    }

                }

            }

            @Override
            public void onFailure(Call<SearchResultsMovies> call, Throwable t) {

            }
        });

    }


    private static class insertMoviesBackground extends AsyncTask<MoviesEntity, Void, Void> {

        private MoviesDAO moviesDAO;
        private String category;

        public insertMoviesBackground(MoviesDAO moviesDAO, String category) {
            this.moviesDAO = moviesDAO;
            this.category = category;
        }

        @Override
        protected Void doInBackground(MoviesEntity... moviesEntities) {
            try{
                moviesDAO.insertMovie(moviesEntities[0]);
            }
            catch (SQLiteConstraintException exception){
                switch (category){
                    case "popular":
                        moviesDAO.updatePopular(moviesEntities[0].getId(),moviesEntities[0].isPopular());
                        break;
                    case "top_rated":
                        moviesDAO.updateTop(moviesEntities[0].getId(),moviesEntities[0].isTop());
                        break;
                    case "upcoming":
                        moviesDAO.updateUpcoming(moviesEntities[0].getId(),moviesEntities[0].isUpcoming());
                        break;
                }
            }
            return null;
        }
    }

    private static class insertGenresBackground extends AsyncTask<GenresEntity, Void, Void> {

        private MoviesDAO moviesDAO;

        public insertGenresBackground(MoviesDAO moviesDAO) {
            this.moviesDAO = moviesDAO;
        }

        @Override
        protected Void doInBackground(GenresEntity... genresEntities) {
            moviesDAO.insertGenre(genresEntities[0]);
            return null;
        }
    }

}
