package com.example.tmdb_app.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.tmdb_app.Holders.HolderCategory;
import com.example.tmdb_app.Main2Activity;
import com.example.tmdb_app.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<HolderCategory>{

    private Context c;
    private List<String> categories;
    private List<List<Integer>> imagesList;

    public CategoryAdapter(Context c, List<String> categories, List<List<Integer>> imagesList) {
        this.c = c;
        this.categories = categories;
        this.imagesList = imagesList;
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

        if( true ) {
            holder.getCategoryName().setText(categories.get(position));

            for (int j = 0; j < imagesList.get(position).size(); j++) {
                DefaultSliderView defaultSliderView = new DefaultSliderView(c);
                defaultSliderView
                        .image(imagesList.get(position).get(j));

                holder.getImagesContainer().addSlider(defaultSliderView);
            }

            //holder.getImagesContainer().stopAutoCycle();
            holder.getImagesContainer().setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
            holder.getImagesContainer().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(c, Main2Activity.class);

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
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}
