package com.example.tmdb_app.Activities.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tmdb_app.LocalData.RoomEntities.GenresEntity;
import com.example.tmdb_app.LocalData.RoomEntities.MoviesEntity;
import com.example.tmdb_app.Utilities.Constants;
import com.example.tmdb_app.Activities.Holders.HolderMovies;
import com.example.tmdb_app.Activities.MovieVisor;
import com.example.tmdb_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
Adaptador usado para mostrar las películas por cada categoria

Este adaptador muestra elementos del tipo: item_class1.xml

Fecha: 15/07/2919
Elaborado por: Andrés Cardona
*/

public class MoviesAdapter extends RecyclerView.Adapter<HolderMovies> {

    private Context c;//Se importa el contexto para mostrar el cover de cada película
    private List<MoviesEntity> movies;//Lista que contiene la información de cada película
    private List<GenresEntity> genres;//Lista que contiene todos los géneros existentes

    public MoviesAdapter(Context c, List<MoviesEntity> movies, List<GenresEntity> genres) {
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

    //Función para traer la lista de géneros de películas dependiendo del idioma(español en este caso)
    public void setGenres(List<GenresEntity> genres) {
        this.genres = genres;
    }

    //Función para agregar elementos al adaptador (se usa cuando se hace la paginación)
    public void addElements(List<MoviesEntity> m){
        this.movies = new ArrayList<>();
        if(m != null) {
            this.movies.addAll(m);
            notifyDataSetChanged();//Actualiza la información
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMovies holder, int position) {

        //Se asgina el cover a la imagen
        Glide.with(c).
                load(Constants.BASE_COVER + movies.get(position).getPosterPath())
                .into(holder.getCover());

        //Se agrega el título y la fecha de estreno en sus respectivos campos
        holder.getMovieName().setText(movies.get(position).getTitle());
        holder.getReleaseDate().setText(movies.get(position).getReleaseDate());

        //El fragmento siguiente hasta la linea 96 se usa para obtener los géneros de cada película
        List<Long> L = movies.get(position).getGenreIds();
        List<GenresEntity> names = new ArrayList<>();

        //Loop utilizado para recorrer los géneros que trae la película y obtener los nombres
        for (Long i : L) {
            List<GenresEntity> result = genres.stream()
                    .filter(item -> item.getId() == i.intValue())
                    .collect(Collectors.toList());
            names.addAll(result);
        }

        holder.getGenres().setText("");
        String generos = "";
        //Loop usado para mostrar los géneros en el item
        for (GenresEntity item : names) {
            generos += item.getName() + ", ";
        }

        String generosF = "No Info";
        //Se borra la última coma en el String del loop anterior
        if(generos.length() > 0) {
            generosF = generos.substring(0, generos.length() - 2);
            holder.getGenres().setText(generosF);
        }

        //Obteniendo la calificación de los usuarios hacia la película
        final float average = movies.get(position).getVoteAverage();
        holder.getRating().setText(String.valueOf(average) + "/10");
        //La calificaión se divide por 2 para mostrar en un rating bar de 5 estrellas
        holder.getRatingBar().setRating(average / 2);

        String finalGenerosF = generosF;//Se usa variable final para ingresarla en la interfaz
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent usado para enviar la información a la siguiente actividad
                Intent intent = new Intent(c, MovieVisor.class);

                intent.putExtra("id", String.valueOf(movies.get(position).getId()));//Usado para consultar el trailer en el siguiente activity
                intent.putExtra("media", "movie");
                intent.putExtra("poster", Constants.BASE_COVER_BIG + movies.get(position).getPosterPath());
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
