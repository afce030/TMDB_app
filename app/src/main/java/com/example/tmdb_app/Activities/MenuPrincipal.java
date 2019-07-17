package com.example.tmdb_app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tmdb_app.Activities.Adapters.CategoryAdapter;
import com.example.tmdb_app.R;

import java.util.ArrayList;
import java.util.List;

/*
Actividad principal del proyecto que muestra las tres categorias de películas

Fecha: 15/07/2919
Elaborado por: Andrés Cardona
*/

public class MenuPrincipal extends AppCompatActivity {

    private RecyclerView rvCategories;//Muestra las tres categorias: popular, top y upcoming
    private CategoryAdapter categoryAdapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                Toast.makeText(this, "ok1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.topRated:
                Toast.makeText(this, "ok2", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.upcoming:
                Toast.makeText(this, "ok3", Toast.LENGTH_SHORT).show();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        rvCategories = findViewById(R.id.rvCategories);

        //Función para configurar el adaptador del menú principal
        adapter();

    }

    //Función para configurar el adaptador y ponerle las imágenes
    void adapter(){

        List<String> categories = new ArrayList<>();

        categories.add("Popular");
        categories.add("Top Rated");
        categories.add("Upcoming");

        List<List<Integer>> imagesList  = new ArrayList<>();

        //Se agregan las iḿagenes a cada categoria del adapter
        List<Integer> popularMovies = new ArrayList<>();
        popularMovies.add(R.drawable.popular1);
        popularMovies.add(R.drawable.popular2);
        imagesList.add(popularMovies);

        List<Integer> topRatedMovies = new ArrayList<>();
        topRatedMovies.add(R.drawable.top_rated1);
        topRatedMovies.add(R.drawable.top_rated2);
        imagesList.add(topRatedMovies);

        List<Integer> upcomingMovies = new ArrayList<>();
        upcomingMovies.add(R.drawable.estreno1);
        upcomingMovies.add(R.drawable.estreno2);
        imagesList.add(upcomingMovies);

        //Se construye el adaptador
        categoryAdapter = new CategoryAdapter(MenuPrincipal.this,categories,imagesList);
        LinearLayoutManager l = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);

        //Se añade un DividerItemDecoration para aumentar la distancia entre las categorias
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),RecyclerView.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.item_decoration_categories));

        //Se inicia el adaptador
        rvCategories.setLayoutManager(l);
        rvCategories.addItemDecoration(dividerItemDecoration);
        rvCategories.setAdapter(categoryAdapter);

    }
}
