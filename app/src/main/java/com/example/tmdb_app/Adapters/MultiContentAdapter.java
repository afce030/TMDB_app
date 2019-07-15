package com.example.tmdb_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tmdb_app.Classes.MultiContent;
import com.example.tmdb_app.Constants.Constants;
import com.example.tmdb_app.Holders.HolderMovies;
import com.example.tmdb_app.Holders.HolderMultiContent;
import com.example.tmdb_app.R;

import java.util.ArrayList;
import java.util.List;

public class MultiContentAdapter extends RecyclerView.Adapter<HolderMultiContent> {

    private Context c;
    private List<MultiContent> content = new ArrayList<>();

    public MultiContentAdapter(Context c, List<MultiContent> content) {
        this.c = c;
        this.content = content;
    }

    public void ModifyContents(List<MultiContent> Q){
        this.content = new ArrayList<>();
        this.content.addAll(Q);

        notifyDataSetChanged();
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

    }

    @Override
    public int getItemCount() {
        return content.size();
    }

}
