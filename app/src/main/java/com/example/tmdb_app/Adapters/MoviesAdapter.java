package com.example.tmdb_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tmdb_app.Classes.Genre_ids;
import com.example.tmdb_app.Classes.TMDBmovie;
import com.example.tmdb_app.Constants.Constants;
import com.example.tmdb_app.Holders.HolderMovies;
import com.example.tmdb_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoviesAdapter extends RecyclerView.Adapter<HolderMovies> {

    private Context c;
    private List<TMDBmovie> movies;
    private List<Genre_ids> genres;

    public MoviesAdapter(Context c, List<TMDBmovie> movies, List<Genre_ids> genres) {
        this.c = c;
        this.movies = movies;
        this.genres = genres;
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

        Glide.with(c).
                load( Constants.baseCover+movies.get(position).getPosterPath())
                .into(holder.getCover());

        holder.getMovieName().setText( movies.get(position).getTitle() );
        holder.getReleaseDate().setText( movies.get(position).getReleaseDate() );

        List<Long> L = movies.get(position).getGenreIds();
        List<Genre_ids> names = new ArrayList<>();
        for (Long i : L){
            List<Genre_ids> result = genres.stream()
                    .filter(item -> item.getId() == i.intValue())
                    .collect(Collectors.toList());
            names.addAll(result);
        }

        holder.getGenres().setText("");
        for(Genre_ids item: names) {
            holder.getGenres().append(item.getName() + ", ");
        }

        float average = movies.get(position).getVoteAverage();
        holder.getRating().setText( String.valueOf(average)+"/10" );
        holder.getRatingBar().setRating(average/2);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}
