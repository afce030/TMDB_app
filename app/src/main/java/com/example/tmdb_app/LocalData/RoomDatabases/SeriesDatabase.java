package com.example.tmdb_app.LocalData.RoomDatabases;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tmdb_app.LocalData.RoomDAO.SeriesDAO;
import com.example.tmdb_app.LocalData.RoomEntities.GenresEntity;
import com.example.tmdb_app.LocalData.RoomEntities.SeriesEntity;

@Database(entities = {SeriesEntity.class, GenresEntity.class}, version = 1, exportSchema = false)
public abstract class SeriesDatabase extends RoomDatabase {
    
    private static SeriesDatabase SeriesDatabase = null;

    public abstract SeriesDAO SeriesDAO();

    //Singleton pattern
    synchronized public static SeriesDatabase getInstance(Context c) {
        if (SeriesDatabase == null) {
            SeriesDatabase = Room
                    .databaseBuilder(c.getApplicationContext(), SeriesDatabase.class, "Series_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return SeriesDatabase;
    }

}
