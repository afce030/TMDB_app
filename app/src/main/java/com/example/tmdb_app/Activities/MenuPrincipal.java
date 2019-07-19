package com.example.tmdb_app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tmdb_app.Activities.Adapters.CategoryAdapter;
import com.example.tmdb_app.Activities.Adapters.viewPagerAdapter;
import com.example.tmdb_app.Activities.Fragments.MoviesFragment;
import com.example.tmdb_app.Activities.Fragments.SeriesFragment;
import com.example.tmdb_app.R;
import com.google.android.material.tabs.TabLayout;

/*
Actividad principal del proyecto que muestra las tres categorias de películas

Fecha: 15/07/2919
Elaborado por: Andrés Cardona
*/

public class MenuPrincipal extends AppCompatActivity implements MoviesFragment.OnFragmentInteractionListener,
        SeriesFragment.OnFragmentInteractionListener {

    private RecyclerView rvCategories;//Muestra las tres categorias: popular, top y upcoming
    private CategoryAdapter categoryAdapter;

    private ViewPager viewPager;
    private viewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;


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

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabLayout);

        viewPagerAdapter = new viewPagerAdapter(getSupportFragmentManager(),0);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
