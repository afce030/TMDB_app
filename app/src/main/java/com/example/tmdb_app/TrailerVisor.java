package com.example.tmdb_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import static com.example.tmdb_app.Constants.Constants.youtubeApi;

public class TrailerVisor extends YouTubeBaseActivity {

    private YouTubePlayerView viewer;
    private YouTubePlayer.OnInitializedListener listener;

    private Button atras;
    private Button reproducir;

    private String videoKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_visor);

        Intent intencion = getIntent();
        videoKey = intencion.getStringExtra("key");

        viewer = findViewById(R.id.youTubePlayerView);
        atras = findViewById(R.id.btnAtras2);
        reproducir = findViewById(R.id.btnReproducir);

        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewer.initialize(youtubeApi, listener);
            }
        });

    }
}
