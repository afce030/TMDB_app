package com.example.tmdb_app.PojoClasses.ConsultaGeneros;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
Clase que se obtiene luego de buscar los géneros

Se obtiene una lista (List<Genre_ids> genres) donde está la información de los géneros disponibles

Fecha: 15/07/2919
Elaborado por: Andrés Cardona
*/

public class GenreResults {

    @SerializedName("genres")
    @Expose
    private List<Genre_ids> genres;

    public List<Genre_ids> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre_ids> genres) {
        this.genres = genres;
    }

}