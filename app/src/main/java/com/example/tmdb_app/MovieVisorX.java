package com.example.tmdb_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.net.URL;

public class MovieVisorX extends AppCompatActivity {

    private RoundedImageView cover;
    private TextView name;
    private TextView rating;
    private TextView overview;
    private TextView genders;
    private TextView release;
    private TextView adults;
    private TextView trailer;

    private Button atras;

    private String poster;
    private String nombre;
    private String calificacion;
    private String resumen;
    private String generos;
    private String estreno;
    private boolean adultos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_visor_x);

        atras = findViewById(R.id.btnAtras);
        cover = findViewById(R.id.ivMoviePhotoIC2);
        name = findViewById(R.id.tvMovieNameIC2);
        rating = findViewById(R.id.tvRatingIC2);
        overview = findViewById(R.id.tvOverviewIC2);
        release = findViewById(R.id.tvReleaseIC2);
        genders = findViewById(R.id.tvGendersIC2);
        adults = findViewById(R.id.tvAdultIC2);

        Intent intencion = getIntent();
        poster = intencion.getStringExtra("poster");
        nombre = intencion.getStringExtra("name");
        calificacion = intencion.getStringExtra("rating");
        resumen = intencion.getStringExtra("overview");
        generos = intencion.getStringExtra("genres");
        estreno = intencion.getStringExtra("release");
        adultos = intencion.getBooleanExtra("adults",false);

        asignarInfo();

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void asignarInfo() {

        Glide.with(MovieVisorX.this).load(poster).into(cover);
        name.setText(nombre);
        rating.setText(calificacion);
        overview.setText(resumen);
        genders.setText(generos);
        release.setText(estreno);

        if(adultos){
            adults.setText("Sí");
        }

    }
}
