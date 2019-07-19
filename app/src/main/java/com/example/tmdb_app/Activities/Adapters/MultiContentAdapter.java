package com.example.tmdb_app.Activities.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tmdb_app.Activities.MovieVisor;
import com.example.tmdb_app.LocalData.RoomEntities.GenresEntity;
import com.example.tmdb_app.LocalData.RoomEntities.MoviesEntity;
import com.example.tmdb_app.LocalData.RoomEntities.SeriesEntity;
import com.example.tmdb_app.Utilities.Constants;
import com.example.tmdb_app.Activities.Holders.HolderMultiContent;
import com.example.tmdb_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
Adaptador usado para mostrar las películas o series en la barra de búsqueda

Este adaptador muestra elementos del tipo: item_query.xml

Fecha: 15/07/2919
Elaborado por: Andrés Cardona
*/

public class MultiContentAdapter extends RecyclerView.Adapter<HolderMultiContent> {

    private Context c;//Se importa el contexto para mostrar la imagen del cover en el buscador
    private List<MoviesEntity> contentMovies = new ArrayList<>();//Lista de películas
    private List<SeriesEntity> contentSeries = new ArrayList<>();//Lista de series
    private List<GenresEntity> genres;//Lista de géneros existentes
    private String type;

    public MultiContentAdapter(Context c, String type) {
        this.c = c;
        this.type = type;
    }

    public void setMode(String type){
        this.type = type;
    }

    //Método para cambiar los elementos del adaptador(dependiendo de lo que el usuario escribe)
    public void ModifyContents(List<MoviesEntity> Q1, List<SeriesEntity> Q2){
        this.contentMovies = new ArrayList<>();
        this.contentMovies.addAll(Q1);

        this.contentSeries = new ArrayList<>();
        this.contentSeries.addAll(Q2);

        notifyDataSetChanged();
    }

    public void setGenres(List<GenresEntity> genres) {
        this.genres = genres;
    }

    @NonNull
    @Override
    public HolderMultiContent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_query, parent, false);
        return new HolderMultiContent(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMultiContent holder, int position) {

        List<Long> L = new ArrayList<>();
        double average = 0;

        //Sí es una película
        if(type.equals("movies")) {

            //Se muestra la imagen del cover
            Glide.with(c).
                    load( Constants.BASE_COVER +contentMovies.get(position).getPosterPath())
                    .into(holder.getCoverItem());

            //Obteniendo el tipo de contenido(serie, película, actor, etc)
            String content_type = "Movie";
            holder.getTypeItem().setText(content_type);

            holder.getNameItem().setText(contentMovies.get(position).getOriginalTitle());
            L = contentMovies.get(position).getGenreIds();

            //Obteniendo calificación (solo si está disponible)
            if(contentMovies.get(position).getVoteAverage() != null) {
                average = contentMovies.get(position).getVoteAverage();
            }

        }
        //Sí es una serie
        else if(type.equals("series")){

            //Se muestra la imagen del cover
            Glide.with(c).
                    load( Constants.BASE_COVER +contentSeries.get(position).getPosterPath())
                    .into(holder.getCoverItem());

            //Obteniendo el tipo de contenido(serie, película, actor, etc)
            String content_type = "TV";
            holder.getTypeItem().setText(content_type);

            holder.getNameItem().setText(contentSeries.get(position).getOriginalName());
            L = contentSeries.get(position).getGenreIds();

            //Obteniendo calificación (solo si está disponible)
            if(contentSeries.get(position).getVoteAverage() != null) {
                average = contentSeries.get(position).getVoteAverage();
            }

        }

        //Asignando los géneros
        List<GenresEntity> names = new ArrayList<>();
        String generosF = "No Info";
        if(L != null & genres != null) {
            for (Long i : L) {
                List<GenresEntity> result = genres.stream()
                        .filter(item -> item.getId() == i.intValue())
                        .collect(Collectors.toList());
                names.addAll(result);
            }

            String generos = "";
            for (GenresEntity item : names) {
                generos += item.getName() + ", ";
            }

            if (generos.length() > 0) {
                generosF = generos.substring(0, generos.length() - 2);
            }
        }



        double finalAverage = average;
        String finalGenerosF = generosF;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, MovieVisor.class);

                switch (type){
                    case "movies":
                        intent.putExtra("id", String.valueOf(contentMovies.get(position).getId()));//Usado para consultar el trailer
                        intent.putExtra("media", "movie");
                        intent.putExtra("poster", Constants.BASE_COVER_BIG + contentMovies.get(position).getPosterPath());
                        intent.putExtra("name", contentMovies.get(position).getTitle());
                        intent.putExtra("rating", String.valueOf(finalAverage));
                        intent.putExtra("overview", contentMovies.get(position).getOverview());
                        intent.putExtra("genres", finalGenerosF);
                        intent.putExtra("release", contentMovies.get(position).getReleaseDate());
                        intent.putExtra("adults", contentMovies.get(position).getAdult());
                        break;
                    case "series":
                        intent.putExtra("id", String.valueOf(contentSeries.get(position).getId()));//Usado para consultar el trailer
                        intent.putExtra("media", type);
                        intent.putExtra("poster", Constants.BASE_COVER_BIG + contentSeries.get(position).getPosterPath());
                        intent.putExtra("name", contentSeries.get(position).getName());
                        intent.putExtra("rating", String.valueOf(finalAverage));
                        intent.putExtra("overview", contentSeries.get(position).getOverview());
                        intent.putExtra("genres", finalGenerosF);
                        intent.putExtra("release", contentSeries.get(position).getFirstAirDate());
                        intent.putExtra("adults", false);
                        break;
                }
                c.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(type.equals("movies")) {
            return contentMovies.size();
        }
        else if(type.equals("series")){
            return contentSeries.size();
        }
        return 0;
    }
}
