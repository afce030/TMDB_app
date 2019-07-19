package com.example.tmdb_app.LocalData.RoomDAO;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tmdb_app.LocalData.RoomEntities.GenresEntity;
import com.example.tmdb_app.LocalData.RoomEntities.MoviesEntity;

import java.util.List;

@Dao
public interface MoviesDAO {

    @Insert
    void insertMovie(MoviesEntity moviesEntity);

    @Delete
    void deleteMovie(MoviesEntity moviesEntity);




    @Query("UPDATE Movies_table set isPopular=:a WHERE id=:id")
    void updatePopular(Long id, int a);

    @Query("UPDATE Movies_table set isTop=:a WHERE id=:id")
    void updateTop(Long id, int a);

    @Query("UPDATE Movies_table set isUpcoming=:a WHERE id=:id")
    void updateUpcoming(Long id, int a);



    @Query("SELECT * FROM Movies_table")
    LiveData<List<MoviesEntity>> getAllMovies();

    @Query("SELECT * FROM Movies_table WHERE id=:ID")
    LiveData<List<MoviesEntity>> getMovieByID(Long ID);


    @Query("SELECT * FROM Movies_table WHERE isPopular=1 ORDER BY page Asc, popularity Desc")
    LiveData<List<MoviesEntity>> getPopularMovies();
    @Query("DELETE FROM Movies_table WHERE isPopular = 1")
    void deletePopularMovies();


    @Query("SELECT * FROM Movies_table WHERE isTop=1 ORDER BY page Asc, voteAverage Desc")
    LiveData<List<MoviesEntity>> getTopMovies();
    @Query("DELETE FROM Movies_table WHERE isTop = 1")
    void deleteTopMovies();


    @Query("SELECT * FROM Movies_table WHERE isUpcoming=1 ORDER BY page Asc, releaseDate Desc")
    LiveData<List<MoviesEntity>> getUpcomingMovies();
    @Query("DELETE FROM Movies_table WHERE isUpcoming = 1")
    void deleteUpcomingMovies();


    @Query("SELECT * FROM Movies_table WHERE originalTitle LIKE :pattern OR title LIKE :pattern")
    LiveData<List<MoviesEntity>> getpattern(String pattern);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGenre(GenresEntity genresEntity);

    @Query("SELECT * FROM Genres_table")
    LiveData<List<GenresEntity>> obtainGenres();

}
