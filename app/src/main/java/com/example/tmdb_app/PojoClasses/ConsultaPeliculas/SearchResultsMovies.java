package com.example.tmdb_app.PojoClasses.ConsultaPeliculas;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
Clase que se obtiene luego de usar el menu principal para buscar populares, top o upcoming

Se obtiene una lista (List<TMDBmovie> results) donde está la información de las películas

Fecha: 15/07/2919
Elaborado por: Andrés Cardona
*/

public class SearchResultsMovies {

    @SerializedName("page")
    @Expose
    private Long page;
    @SerializedName("total_results")
    @Expose
    private Long totalResults;
    @SerializedName("total_pages")
    @Expose
    private Long totalPages;
    @SerializedName("results")
    @Expose
    private List<TMDBmovie> results = null;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public List<TMDBmovie> getResults() {
        return results;
    }

    public void setResults(List<TMDBmovie> results) {
        this.results = results;
    }

}