package com.example.tmdb_app.PojoClasses.ConsultaTrailers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResults {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<TrailerClass> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<TrailerClass> getResults() {
        return results;
    }

    public void setResults(List<TrailerClass> results) {
        this.results = results;
    }

}
