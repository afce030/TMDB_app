package com.example.tmdb_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tmdb_app.APIconnections.TMDBservice;
import com.example.tmdb_app.Classes.GenreClass;
import com.example.tmdb_app.Classes.Genre_ids;
import com.example.tmdb_app.Classes.TrailerClass;
import com.example.tmdb_app.Classes.TrailerResults;
import com.example.tmdb_app.Constants.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.makeramen.roundedimageview.RoundedImageView;

import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieVisorX extends AppCompatActivity {

    private RoundedImageView cover;
    private TextView name;
    private TextView rating;
    private TextView overview;
    private TextView genders;
    private TextView release;
    private TextView adults;

    private Button atras;
    private Button verTrailer;

    private String movieID;
    private String mediaType;
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
        verTrailer = findViewById(R.id.btnVerTrailer);
        cover = findViewById(R.id.ivMoviePhotoIC2);
        name = findViewById(R.id.tvMovieNameIC2);
        rating = findViewById(R.id.tvRatingIC2);
        overview = findViewById(R.id.tvOverviewIC2);
        release = findViewById(R.id.tvReleaseIC2);
        genders = findViewById(R.id.tvGendersIC2);
        adults = findViewById(R.id.tvAdultIC2);

        Intent intencion = getIntent();
        movieID = intencion.getStringExtra("id");
        mediaType = intencion.getStringExtra("media");
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

        verTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaType.toLowerCase().equals("movie")){
                    getTrailer(movieID);
                }
                else{
                    Toast.makeText(MovieVisorX.this, "Trailer solo disponible para películas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void asignarInfo() {

        Glide.with(MovieVisorX.this).load(poster).into(cover);
        name.setText(nombre);
        rating.setText(calificacion);
        overview.setText(resumen);
        genders.setText(generos);

        //Release solo disponible para peliculas
        if(mediaType.toLowerCase().equals("movie")) {
            release.setText(estreno);
        }else {
            release.setText("No disponible");
        }

        if(adultos){
            adults.setText("Sí");
        }

    }

    void getTrailer(String id){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDBservice service = retrofit.create(TMDBservice.class);

        String language = "en-US";
        Call<TrailerResults> call = service.getMovieTrailer(id, Constants.api_key, language);

        final TaskCompletionSource<List<TrailerClass>> completionSource = new TaskCompletionSource<>();
        call.enqueue(new Callback<TrailerResults>() {
            @Override
            public void onResponse(Call<TrailerResults> call, Response<TrailerResults> response) {

                TrailerResults trailerClass = response.body();
                completionSource.setResult(trailerClass.getResults());

            }
            @Override
            public void onFailure(Call<TrailerResults> call, Throwable t) {
                Toast.makeText(MovieVisorX.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Task<List<TrailerClass>> task = completionSource.getTask();
        task.addOnSuccessListener(new OnSuccessListener<List<TrailerClass>>() {
            @Override
            public void onSuccess(List<TrailerClass> trailerClasses) {

                if(trailerClasses.get(0).getSite().toLowerCase().equals("youtube")) {
                    String key = trailerClasses.get(0).getKey();
                    Intent intent = new Intent(MovieVisorX.this, TrailerVisor.class);
                    intent.putExtra("key", key);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MovieVisorX.this, "Trailer no disponible", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
