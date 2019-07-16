package com.example.tmdb_app.Classes.ConsultaGeneros;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
Clase que se obtiene luego de extraer el atributo genres de la clase GenreResults

Fecha: 15/07/2919
Elaborado por: Andrés Cardona
*/

public class Genre_ids {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}