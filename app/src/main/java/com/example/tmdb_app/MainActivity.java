package com.example.tmdb_app;

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

import com.example.tmdb_app.Adapters.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCategories;
    private CategoryAdapter categoryAdapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(this, "ok1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.topRated:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(this, "ok2", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.upcoming:
                // User chose the "Settings" item, show the app settings UI...
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
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        rvCategories = findViewById(R.id.rvCategories);

        adapter();

    }

    void adapter(){

        List<String> categories = new ArrayList<>();
        categories.add("Popular");
        categories.add("Top Rated");
        categories.add("Upcoming");

        List<List<Integer>> imagesList  = new ArrayList<>();

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

        categoryAdapter = new CategoryAdapter(MainActivity.this,categories,imagesList);

        LinearLayoutManager l = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);

        //Se añade un DividerItemDecoration para aumentar la distancia entre las categorias
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),RecyclerView.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.item_decoration_categories));

        rvCategories.setLayoutManager(l);
        rvCategories.addItemDecoration(dividerItemDecoration);
        rvCategories.setAdapter(categoryAdapter);
        rvCategories.getRecycledViewPool().setMaxRecycledViews(0,0);

    }
}
