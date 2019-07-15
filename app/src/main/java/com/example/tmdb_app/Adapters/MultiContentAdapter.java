package com.example.tmdb_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tmdb_app.Classes.Genre_ids;
import com.example.tmdb_app.Classes.MultiContent;
import com.example.tmdb_app.Constants.Constants;
import com.example.tmdb_app.Holders.HolderMultiContent;
import com.example.tmdb_app.MovieVisorX;
import com.example.tmdb_app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MultiContentAdapter extends RecyclerView.Adapter<HolderMultiContent> {

    private Context c;
    private List<MultiContent> content = new ArrayList<>();
    private List<Genre_ids> genres;

    public MultiContentAdapter(Context c, List<MultiContent> content) {
        this.c = c;
        this.content = content;
    }

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

        Glide.with(c).
                load( Constants.baseCover+content.get(position).getPosterPath())
                .into(holder.getCoverItem());

        String content_type = content.get(position).getMediaType();
        holder.getTypeItem().setText(content_type);

        if(content_type.equals("tv")){
            holder.getNameItem().setText(content.get(position).getOriginalName());
        }
        else{
            holder.getNameItem().setText(content.get(position).getOriginalTitle());
        }


        List<Integer> L = content.get(position).getGenreIds();
        List<Genre_ids> names = new ArrayList<>();
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

        String generosF = "No Info";
        if(generos.length() > 0) {
            generosF = generos.substring(0, generos.length() - 2);
        }

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

                intent.putExtra("id", String.valueOf(content.get(position).getId()));
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
