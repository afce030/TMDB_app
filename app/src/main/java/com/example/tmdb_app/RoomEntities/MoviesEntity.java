package com.example.tmdb_app.RoomEntities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.tmdb_app.RoomConverters.ListConverter;

import java.util.List;

@Entity(tableName = "Movies_table")
public class MoviesEntity {

    @PrimaryKey(autoGenerate = false)
    private Long id;
    private Long voteCount;
    private Boolean video;
    private Float voteAverage;
    private String title;
    private Float popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    @TypeConverters(ListConverter.class)
    private List<Long> genreIds;
    private String backdropPath;
    private Boolean adult;
    private String overview;
    private String releaseDate;

    //Declaring the type of movie among: Popular, Top or Upcoming
    private int isPopular;
    private int isTop;
    private int isUpcoming;

    public MoviesEntity(Long id, Long voteCount, Boolean video, Float voteAverage, String title, Float popularity, String posterPath, String originalLanguage, String originalTitle, List<Long> genreIds, String backdropPath, Boolean adult, String overview, String releaseDate, int isPopular, int isTop, int isUpcoming) {
        this.id = id;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.isPopular = isPopular;
        this.isTop = isTop;
        this.isUpcoming = isUpcoming;
    }

    public Long getId() {
        return id;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public Float getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public List<Long> getGenreIds() {
        return genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int isPopular() {
        return isPopular;
    }

    public int isTop() {
        return isTop;
    }

    public int isUpcoming() {
        return isUpcoming;
    }
}
