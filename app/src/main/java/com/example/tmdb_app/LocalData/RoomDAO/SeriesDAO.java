package com.example.tmdb_app.LocalData.RoomDAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tmdb_app.LocalData.RoomEntities.SeriesEntity;

import java.util.List;

@Dao
public interface SeriesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSerie(SeriesEntity SeriesEntity);

    @Delete
    void deleteSerie(SeriesEntity SeriesEntity);

    @Update
    void updateSerie(SeriesEntity SeriesEntity);

    @Query("SELECT * FROM Series_table WHERE id=:ID")
    LiveData<List<SeriesEntity>> getSerieByID(Long ID);


    @Query("SELECT * FROM Series_table WHERE isPopular=1 ORDER BY popularity Desc")
    LiveData<List<SeriesEntity>> getPopularSeries();
    @Query("DELETE FROM Series_table WHERE isPopular = 1")
    void deletePopularSeries();


    @Query("SELECT * FROM Series_table WHERE isTop=1 ORDER BY voteAverage Desc")
    LiveData<List<SeriesEntity>> getTopSeries();
    @Query("DELETE FROM Series_table WHERE isTop = 1")
    void deleteTopSeries();


    @Query("SELECT * FROM Series_table WHERE isUpcoming=1 ORDER BY firstAirDate Desc")
    LiveData<List<SeriesEntity>> getUpcomingSeries();
    @Query("DELETE FROM Series_table WHERE isUpcoming = 1")
    void deleteUpcomingSeries();

}
