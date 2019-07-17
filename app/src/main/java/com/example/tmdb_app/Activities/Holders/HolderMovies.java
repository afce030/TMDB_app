package com.example.tmdb_app.Activities.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb_app.R;

public class HolderMovies extends RecyclerView.ViewHolder {

    private ImageView cover;
    private TextView movieName;
    private TextView genres;
    private TextView releaseDate;
    private RatingBar ratingBar;
    private TextView rating;

    public HolderMovies(@NonNull View itemView) {
        super(itemView);

        cover = itemView.findViewById(R.id.ivMoviePhotoIC1);
        movieName = itemView.findViewById(R.id.tvMovieNameIC1);
        genres = itemView.findViewById(R.id.tvGenresIC1);
        releaseDate = itemView.findViewById(R.id.tvReleaseIC1);
        ratingBar = itemView.findViewById(R.id.rbRatingIC1);
        rating = itemView.findViewById(R.id.tvRatingIC1);

    }

    public ImageView getCover() {
        return cover;
    }

    public TextView getMovieName() {
        return movieName;
    }

    public TextView getGenres() {
        return genres;
    }

    public TextView getReleaseDate() {
        return releaseDate;
    }

    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public TextView getRating() {
        return rating;
    }
}
