package com.example.tmdb_app.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb_app.R;

import org.w3c.dom.Text;

public class HolderMultiContent extends RecyclerView.ViewHolder {

    private ImageView coverItem;
    private TextView nameItem;
    private TextView typeItem;

    public HolderMultiContent(@NonNull View itemView) {
        super(itemView);

        coverItem = itemView.findViewById(R.id.ivCoverItem);
        nameItem = itemView.findViewById(R.id.tvNameContentItem);
        typeItem = itemView.findViewById(R.id.tvTypeContentItem);
    }

    public ImageView getCoverItem() {
        return coverItem;
    }

    public TextView getNameItem() {
        return nameItem;
    }

    public TextView getTypeItem() {
        return typeItem;
    }
}
