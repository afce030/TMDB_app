package com.example.tmdb_app.Holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb_app.Extended_libraries.sliderLayoutNoSwipe;
import com.example.tmdb_app.R;

public class HolderCategory extends RecyclerView.ViewHolder {

    private TextView categoryName;
    private sliderLayoutNoSwipe imagesContainer;

    public HolderCategory(@NonNull View itemView) {
        super(itemView);

        categoryName = itemView.findViewById(R.id.tvCategoryName);
        imagesContainer = itemView.findViewById(R.id.slShowLabel);

    }

    public TextView getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(TextView categoryName) {
        this.categoryName = categoryName;
    }

    public sliderLayoutNoSwipe getImagesContainer() {
        return imagesContainer;
    }

    public void setImagesContainer(sliderLayoutNoSwipe imagesContainer) {
        this.imagesContainer = imagesContainer;
    }
}
