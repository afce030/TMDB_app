package com.example.tmdb_app.Classes;

import java.util.List;

public class SearchResults {

    private int page;
    private int total_results;
    private int total_pages;
    private List<TMDBmovie> results;

    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<TMDBmovie> getResults() {
        return results;
    }
}
