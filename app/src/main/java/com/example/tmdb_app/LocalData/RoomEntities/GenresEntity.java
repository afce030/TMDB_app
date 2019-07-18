package com.example.tmdb_app.LocalData.RoomEntities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Genres_table")
public class GenresEntity {

    @PrimaryKey(autoGenerate = false)
    private Integer id;
    private String name;

    public GenresEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
