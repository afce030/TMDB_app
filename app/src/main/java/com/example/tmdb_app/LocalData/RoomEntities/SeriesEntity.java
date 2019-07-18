package com.example.tmdb_app.LocalData.RoomEntities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.tmdb_app.LocalData.RoomConverters.CountryListConverter;
import com.example.tmdb_app.LocalData.RoomConverters.GenreListConverter;

import java.util.List;

@Entity(tableName = "Series_table")
public class SeriesEntity {

    @PrimaryKey(autoGenerate = false)
    private Integer id;
    private String originalName;
    @TypeConverters(GenreListConverter.class)
    private List<Long> genreIds;
    private String name;
    private Double popularity;
    @TypeConverters(CountryListConverter.class)
    private List<String> originCountry;
    private Integer voteCount;
    private String firstAirDate;
    private String backdropPath;
    private String originalLanguage;
    private Double voteAverage;
    private String overview;
    private String posterPath;
    //Declaring the type of serie among: Popular, Top or Upcoming
    private int isPopular;
    private int isTop;
    private int isUpcoming;

    public SeriesEntity(Integer id, String originalName, List<Long> genreIds, String name, Double popularity, List<String> originCountry, Integer voteCount, String firstAirDate, String backdropPath, String originalLanguage, Double voteAverage, String overview, String posterPath, int isPopular, int isTop, int isUpcoming) {
        this.id = id;
        this.originalName = originalName;
        this.genreIds = genreIds;
        this.name = name;
        this.popularity = popularity;
        this.originCountry = originCountry;
        this.voteCount = voteCount;
        this.firstAirDate = firstAirDate;
        this.backdropPath = backdropPath;
        this.originalLanguage = originalLanguage;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.posterPath = posterPath;
        this.isPopular = isPopular;
        this.isTop = isTop;
        this.isUpcoming = isUpcoming;
    }

    public Integer getId() {
        return id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public List<Long> getGenreIds() {
        return genreIds;
    }

    public String getName() {
        return name;
    }

    public Double getPopularity() {
        return popularity;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getIsPopular() {
        return isPopular;
    }

    public int getIsTop() {
        return isTop;
    }

    public int getIsUpcoming() {
        return isUpcoming;
    }
}
