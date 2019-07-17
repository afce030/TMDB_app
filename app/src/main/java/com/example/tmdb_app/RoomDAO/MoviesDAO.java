package com.example.tmdb_app.RoomDAO;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tmdb_app.RoomEntities.MoviesEntity;

import java.util.List;

@Dao
public interface MoviesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MoviesEntity moviesEntity);

    @Delete
    void deleteMovie(MoviesEntity moviesEntity);

    @Update
    void updateMovie(MoviesEntity moviesEntity);

    @Query("SELECT * FROM Movies_table WHERE id=:ID")
    LiveData<List<MoviesEntity>> getMovieByID(Long ID);


    @Query("SELECT * FROM Movies_table WHERE isPopular=1 ORDER BY popularity Desc")
    LiveData<List<MoviesEntity>> getPopularMovies();
    @Query("DELETE FROM Movies_table WHERE isPopular = 1")
    void deletePopularMovies();


    @Query("SELECT * FROM Movies_table WHERE isTop=1 ORDER BY voteAverage Desc")
    LiveData<List<MoviesEntity>> getTopMovies();
    @Query("DELETE FROM Movies_table WHERE isTop = 1")
    void deleteTopMovies();


    @Query("SELECT * FROM Movies_table WHERE isUpcoming=1 ORDER BY releaseDate Desc")
    LiveData<List<MoviesEntity>> getUpcomingMovies();
    @Query("DELETE FROM Movies_table WHERE isUpcoming = 1")
    void deleteUpcomingMovies();

}
