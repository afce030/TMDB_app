package com.example.tmdb_app.Activities.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.tmdb_app.Activities.Holders.HolderCategory;
import com.example.tmdb_app.Activities.SearchActivity;
import com.example.tmdb_app.R;

import java.util.List;

/*
Adaptador para mostrar las categorias: popular, top, upcoming

Este adaptador muestra elementos del tipo: item_categorias.xml

Fecha: 15/07/2919
Elaborado por: Andrés Cardona
*/

public class CategoryAdapter extends RecyclerView.Adapter<HolderCategory>{

    private Context c;//Se importa el contexto para llegar a la siguiente actividad desde el adapter
    private List<String> categories;//Nombres de las categorias
    private List<List<Integer>> imagesList;//Imágenes de cada categoria

    private String typ;

    //Constructor
    public CategoryAdapter(Context c, List<String> categories, List<List<Integer>> imagesList, String typ) {
        this.c = c;
        this.categories = categories;
        this.imagesList = imagesList;
        this.typ = typ;
    }

    @NonNull
    @Override
    public HolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categorias, parent, false);
        return new HolderCategory(vista);    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final HolderCategory holder, final int position) {

        holder.getCategoryName().setText(categories.get(position));

        //Loop utilizado para rellenar cada item con sus respectivas imágenes
        for (int j = 0; j < imagesList.get(position).size(); j++) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(c);
            defaultSliderView.image(imagesList.get(position).get(j));

            holder.getImagesContainer().addSlider(defaultSliderView);
        }

        //holder.getImagesContainer().stopAutoCycle();
        holder.getImagesContainer().setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        holder.getImagesContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent usado para cambiar a la siguiente actividad
                Intent intent = new Intent(c, SearchActivity.class);
                intent.putExtra("type", typ);

                //Se toma la categoria escogida y se pasa como parámetro a la siguiente actividad
                String cat = categories.get(position);
                switch (cat){
                    case "Popular":
                        intent.putExtra("category", "popular");
                        break;
                    case "Top Rated":
                        intent.putExtra("category", "top_rated");
                        break;
                    case "Upcoming":
                        intent.putExtra("category", "upcoming");
                        break;
                }

                c.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}
