package com.example.tmdb_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.system.ErrnoException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tmdb_app.Classes.Genre_ids;
import com.example.tmdb_app.Classes.TMDBmovie;
import com.example.tmdb_app.Constants.Constants;
import com.example.tmdb_app.Holders.HolderMovies;
import com.example.tmdb_app.MovieVisorX;
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

    public void setGenres(List<Genre_ids> genres) {
        this.genres = genres;
    }

    public void addElements(List<TMDBmovie> m){
        this.movies.addAll(m);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMovies holder, int position) {

        Glide.with(c).
                load(Constants.baseCover + movies.get(position).getPosterPath())
                .into(holder.getCover());

        holder.getMovieName().setText(movies.get(position).getTitle());
        holder.getReleaseDate().setText(movies.get(position).getReleaseDate());

        List<Long> L = movies.get(position).getGenreIds();
        List<Genre_ids> names = new ArrayList<>();
        for (Long i : L) {
            List<Genre_ids> result = genres.stream()
                    .filter(item -> item.getId() == i.intValue())
                    .collect(Collectors.toList());
            names.addAll(result);
        }

        holder.getGenres().setText("");
        String generos = "";
        for (Genre_ids item : names) {
            generos += item.getName() + ", ";
        }

        String generosF = "No Info";
        if(generos.length() > 0) {
            generosF = generos.substring(0, generos.length() - 2);
            holder.getGenres().setText(generosF);
        }

        final float average = movies.get(position).getVoteAverage();
        holder.getRating().setText(String.valueOf(average) + "/10");
        holder.getRatingBar().setRating(average / 2);

        String finalGenerosF = generosF;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, MovieVisorX.class);

                intent.putExtra("id", String.valueOf(movies.get(position).getId()));
                intent.putExtra("poster", Constants.baseCoverBig + movies.get(position).getPosterPath());
                intent.putExtra("name", movies.get(position).getTitle());
                intent.putExtra("rating", String.valueOf(average));
                intent.putExtra("overview", movies.get(position).getOverview());
                intent.putExtra("genres", finalGenerosF);
                intent.putExtra("release", movies.get(position).getReleaseDate());
                intent.putExtra("adults", movies.get(position).getAdult());

                c.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}
