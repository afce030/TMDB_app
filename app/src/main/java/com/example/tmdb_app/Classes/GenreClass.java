package com.example.tmdb_app.Classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenreClass {

    @SerializedName("genres")
    @Expose
    private List<Genre_ids> genres = null;

    public List<Genre_ids> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre_ids> genres) {
        this.genres = genres;
    }

}