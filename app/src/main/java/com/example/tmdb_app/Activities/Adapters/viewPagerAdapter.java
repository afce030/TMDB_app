package com.example.tmdb_app.Activities.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tmdb_app.Activities.Fragments.MoviesFragment;
import com.example.tmdb_app.Activities.Fragments.SeriesFragment;

public class viewPagerAdapter extends FragmentPagerAdapter {

    public viewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new MoviesFragment();
            case 1:
                return new SeriesFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch(position){
            case 0:
                return "Películas";
            case 1:
                return "Series";
        }
        return null;
    }
}
