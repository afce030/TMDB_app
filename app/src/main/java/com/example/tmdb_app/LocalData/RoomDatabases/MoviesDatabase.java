package com.example.tmdb_app.LocalData.RoomDatabases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tmdb_app.LocalData.RoomDAO.MoviesDAO;
import com.example.tmdb_app.LocalData.RoomEntities.GenresEntity;
import com.example.tmdb_app.LocalData.RoomEntities.MoviesEntity;

@Database(entities = {MoviesEntity.class, GenresEntity.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    private static MoviesDatabase moviesDatabase = null;

    public abstract MoviesDAO moviesDAO();

    //Singleton pattern
    synchronized public static MoviesDatabase getInstance(Context c) {
        if (moviesDatabase == null) {
            moviesDatabase = Room
                                .databaseBuilder(c.getApplicationContext(), MoviesDatabase.class, "Movies_database")
                                .fallbackToDestructiveMigration()
                                .build();
        }
        return moviesDatabase;
    }

}
