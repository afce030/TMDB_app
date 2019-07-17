package com.example.tmdb_app.RoomDatabases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tmdb_app.RoomDAO.MoviesDAO;
import com.example.tmdb_app.RoomEntities.MoviesEntity;

@Database(entities = {MoviesEntity.class}, version = 1, exportSchema = false)
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
