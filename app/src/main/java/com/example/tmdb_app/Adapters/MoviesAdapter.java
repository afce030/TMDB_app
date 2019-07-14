package com.example.tmdb_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb_app.Classes.TMDBmovie;
import com.example.tmdb_app.Holders.HolderCategory;
import com.example.tmdb_app.Holders.HolderMovies;
import com.example.tmdb_app.R;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<HolderMovies> {

    private List<TMDBmovie> movies;

    public MoviesAdapter(List<TMDBmovie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public HolderMovies onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_class1, parent, false);
        return new HolderMovies(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMovies holder, int position) {

        holder.getMovieName().setText( movies.get(position).getTitle() );

        holder.getReleaseDate().setText( movies.get(position).getReleaseDate() );

        float average = movies.get(position).getVoteAverage();
        holder.getRating().setText( String.valueOf(average)+"/10" );
        holder.getRatingBar().setRating(average/2);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}
