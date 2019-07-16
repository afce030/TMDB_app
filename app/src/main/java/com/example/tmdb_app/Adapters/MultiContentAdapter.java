package com.example.tmdb_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tmdb_app.Classes.ConsultaGeneros.Genre_ids;
import com.example.tmdb_app.Classes.ConsultaHibrida.MultiContent;
import com.example.tmdb_app.Utilities.Constants;
import com.example.tmdb_app.Holders.HolderMultiContent;
import com.example.tmdb_app.MovieVisorX;
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
    private List<MultiContent> content;//Lista de películas o series
    private List<Genre_ids> genres;//Lista de géneros existentes

    public MultiContentAdapter(Context c, List<MultiContent> content) {
        this.c = c;
        this.content = content;
    }

    //Método para cambiar los elementos del adaptador(dependiendo de lo que el usuario escribe)
    public void ModifyContents(List<MultiContent> Q){
        this.content = new ArrayList<>();
        this.content.addAll(Q);
        notifyDataSetChanged();
    }

    public void setGenres(List<Genre_ids> genres) {
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

        //Se muestra la imagen del cover
        Glide.with(c).
                load( Constants.baseCover+content.get(position).getPosterPath())
                .into(holder.getCoverItem());

        //Obteniendo el tipo de contenido(serie, película, actor, etc)
        String content_type = content.get(position).getMediaType();
        holder.getTypeItem().setText(content_type);


        if(content_type.equals("tv")){//Si es una serie se busca por nombre
            holder.getNameItem().setText(content.get(position).getOriginalName());
        }
        else{//Si es una película se busca por itulo
            holder.getNameItem().setText(content.get(position).getOriginalTitle());
        }

        List<Integer> L = content.get(position).getGenreIds();
        List<Genre_ids> names = new ArrayList<>();

        //Asgignando los géneros
        String generosF = "No Info";
        if(!(L == null)) {
            for (Integer i : L) {
                List<Genre_ids> result = genres.stream()
                        .filter(item -> item.getId() == i)
                        .collect(Collectors.toList());
                names.addAll(result);
            }

            String generos = "";
            for (Genre_ids item : names) {
                generos += item.getName() + ", ";
            }

            if (generos.length() > 0) {
                generosF = generos.substring(0, generos.length() - 2);
            }
        }

        //Obteniendo calificación (solo si está disponible)
        double average = 0;
        if(content.get(position).getVoteAverage() != null) {
            average = content.get(position).getVoteAverage();
        }

        double finalAverage = average;
        String finalGenerosF = generosF;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, MovieVisorX.class);

                intent.putExtra("id", String.valueOf(content.get(position).getId()));//Usado para consultar el trailer
                intent.putExtra("media", content.get(position).getMediaType());
                intent.putExtra("poster", Constants.baseCoverBig + content.get(position).getPosterPath());
                intent.putExtra("name", content.get(position).getTitle());
                intent.putExtra("rating", String.valueOf(finalAverage));
                intent.putExtra("overview", content.get(position).getOverview());
                intent.putExtra("genres", finalGenerosF);
                intent.putExtra("release", content.get(position).getReleaseDate());
                intent.putExtra("adults", content.get(position).getAdult());

                c.startActivity(intent);            }
        });

    }

    @Override
    public int getItemCount() {
        return content.size();
    }

}
