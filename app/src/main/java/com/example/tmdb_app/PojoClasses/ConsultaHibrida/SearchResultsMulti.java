package com.example.tmdb_app.PojoClasses.ConsultaHibrida;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/*
Clase que se obtiene luego de usar el buscador

Se obtiene una lista (List<MultiContent> results) donde está la información de los contenidos disponibles

Fecha: 15/07/2919
Elaborado por: Andrés Cardona
*/

public class SearchResultsMulti {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<MultiContent> results = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<MultiContent> getResults() {
        return results;
    }

    public void setResults(List<MultiContent> results) {
        this.results = results;
    }

}
