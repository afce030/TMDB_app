package com.example.tmdb_app.Repositories;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.tmdb_app.APIconnections.GenresWS;
import com.example.tmdb_app.APIconnections.TMDBserieWS;
import com.example.tmdb_app.LocalData.RoomEntities.GenresEntity;
import com.example.tmdb_app.PojoClasses.ConsultaGeneros.GenreResults;
import com.example.tmdb_app.PojoClasses.ConsultaGeneros.Genre_ids;
import com.example.tmdb_app.LocalData.RoomDAO.SeriesDAO;
import com.example.tmdb_app.LocalData.RoomDatabases.SeriesDatabase;
import com.example.tmdb_app.LocalData.RoomEntities.SeriesEntity;
import com.example.tmdb_app.PojoClasses.ConsultaTV.SearchResultsTV;
import com.example.tmdb_app.PojoClasses.ConsultaTV.TMDBserie;
import com.example.tmdb_app.Utilities.Constants;
import com.example.tmdb_app.Injection.daggerComp.DaggerRetrofitComponent;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesRepo {
    private SeriesDAO SeriesDAO;
    private LiveData<List<SeriesEntity>> foundSeries;
    private LiveData<List<GenresEntity>> foundGenres;

    @Inject
    TMDBserieWS tmdBSerieWS;//This parameter is injected by the Retrofit Module

    @Inject
    GenresWS genresWS;//This parameter is injected by the Retrofit Module

    public SeriesRepo(Application application) {
        DaggerRetrofitComponent.create().inject(this);

        SeriesDatabase SeriesDatabase = com.example.tmdb_app.LocalData.RoomDatabases.SeriesDatabase.getInstance(application);
        SeriesDAO = SeriesDatabase.SeriesDAO();

        foundGenres = GenresByLanguage();
        foundSeries = SeriesDAO.getPopularSeries();
    }

    //se obtienen las series y se almacenan localmente
    public LiveData<List<SeriesEntity>> getSeriesByCategory(String category, Integer page){
        refreshSeries(category, page);

        switch (category){
            case "popular":
                break;
            case "top_rated":
                foundSeries = SeriesDAO.getTopSeries();
                break;
        }

        return foundSeries;
    }
    void refreshSeries(String category, int page){

        Call<SearchResultsTV> call = tmdBSerieWS.getSeriesByType(category, Constants.API_KEY,"es",page);

        String finalCategory = category;
        call.enqueue(new Callback<SearchResultsTV>() {
            @Override
            public void onResponse(Call<SearchResultsTV> call, Response<SearchResultsTV> response) {

                List<TMDBserie> g = response.body().getResults();

                for(TMDBserie item: g) {

                    int popular = 0;
                    int top = 0;

                    switch (finalCategory){
                        case "popular":
                            popular = 1;
                            break;
                        case "top_rated":
                            top = 1;
                            break;
                    }

                    SeriesEntity SeriesEntity = new SeriesEntity(
                            item.getId(),
                            item.getOriginalName(),
                            item.getGenreIds(),
                            item.getName(),
                            item.getPopularity(),
                            item.getOriginCountry(),
                            item.getVoteCount(),
                            item.getFirstAirDate(),
                            item.getBackdropPath(),
                            item.getOriginalLanguage(),
                            item.getVoteAverage(),
                            item.getOverview(),
                            item.getPosterPath(),
                            popular,
                            top,
                            page);

                    new insertSeriesBackground(SeriesDAO, category).execute(SeriesEntity);
                }
            }

            @Override
            public void onFailure(Call<SearchResultsTV> call, Throwable t) {

            }
        });

    }


    public LiveData<List<GenresEntity>> GenresByLanguage(){
        refreshGenres("es");
        return SeriesDAO.obtainGenres();
    }
    //Función para obtener los géneros desde TMDB
    void refreshGenres(String language){

        Call<GenreResults> call = genresWS.getSerieGenres(Constants.API_KEY, language);

        call.enqueue(new Callback<GenreResults>() {
            @Override
            public void onResponse(Call<GenreResults> call, Response<GenreResults> response) {

                List<Genre_ids> g = response.body().getGenres();

                for(Genre_ids item : g){

                    GenresEntity genresEntity = new GenresEntity(item.getId(), item.getName());
                    new insertGenresBackground(SeriesDAO).execute(genresEntity);

                }

            }

            @Override
            public void onFailure(Call<GenreResults> call, Throwable t) {

            }
        });

    }


    //Funciones para buscar series mediante una palabra clave
    public LiveData<List<SeriesEntity>> SearchSeries(String pattern){
        refreshMoviesByPattern(pattern);
        return SeriesDAO.getpattern("%"+pattern+"%");
    }
    void refreshMoviesByPattern(String pattern){

        Call<SearchResultsTV> call = tmdBSerieWS.getSeriesByQuery(pattern, Constants.API_KEY, "es", 1);
        call.enqueue(new Callback<SearchResultsTV>() {
            @Override
            public void onResponse(Call<SearchResultsTV> call, Response<SearchResultsTV> response) {

                if(response.isSuccessful()){

                    List<TMDBserie> g = response.body().getResults();
                    for(TMDBserie item: g) {

                        int popular = 0;
                        int top = 0;
                        int pagina = 1;

                        SeriesEntity SeriesEntity = new SeriesEntity(
                                item.getId(),
                                item.getOriginalName(),
                                item.getGenreIds(),
                                item.getName(),
                                item.getPopularity(),
                                item.getOriginCountry(),
                                item.getVoteCount(),
                                item.getFirstAirDate(),
                                item.getBackdropPath(),
                                item.getOriginalLanguage(),
                                item.getVoteAverage(),
                                item.getOverview(),
                                item.getPosterPath(),
                                popular,
                                top,
                                pagina);

                        new insertSeriesBackground(SeriesDAO, "").execute(SeriesEntity);
                    }

                }

            }

            @Override
            public void onFailure(Call<SearchResultsTV> call, Throwable t) {

            }
        });

    }

    //Se introducen las series en la base de datos local
    private static class insertSeriesBackground extends AsyncTask<SeriesEntity, Void, Void> {

        private SeriesDAO seriesDAO;
        private String category;

        public insertSeriesBackground(com.example.tmdb_app.LocalData.RoomDAO.SeriesDAO seriesDAO, String category) {
            this.seriesDAO = seriesDAO;
            this.category = category;
        }

        @Override
        protected Void doInBackground(SeriesEntity... SeriesEntities) {
            try{
                seriesDAO.insertSerie(SeriesEntities[0]);
            }
            catch (SQLiteConstraintException exception){
                switch (category){
                    case "popular":
                        seriesDAO.updatePopular(SeriesEntities[0].getId(),SeriesEntities[0].getIsPopular());
                        break;
                    case "top_rated":
                        seriesDAO.updateTop(SeriesEntities[0].getId(),SeriesEntities[0].getIsTop());
                        break;
                }
            }            return null;
        }
    }
    //Se inserta la tabla de géneros en la base de datos local
    private static class insertGenresBackground extends AsyncTask<GenresEntity, Void, Void> {

        private SeriesDAO SeriesDAO;

        public insertGenresBackground(SeriesDAO SeriesDAO) {
            this.SeriesDAO = SeriesDAO;
        }

        @Override
        protected Void doInBackground(GenresEntity... genresEntities) {
            SeriesDAO.insertGenre(genresEntities[0]);
            return null;
        }
    }

}
