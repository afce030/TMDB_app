package com.example.tmdb_app.Activities.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tmdb_app.Activities.Adapters.CategoryAdapter;
import com.example.tmdb_app.Activities.MenuPrincipal;
import com.example.tmdb_app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView rvCategories;//Muestra las tres categorias: popular, top y upcoming
    private CategoryAdapter categoryAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoviesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_movies, container, false);

        rvCategories = vista.findViewById(R.id.rvCategoriesMov);
        //Función para configurar el adaptador del menú principal
        adapter();

        return vista;
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
        categoryAdapter = new CategoryAdapter(getContext(),categories,imagesList, "movies");
        LinearLayoutManager l = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);

        //Se añade un DividerItemDecoration para aumentar la distancia entre las categorias
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),RecyclerView.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.item_decoration_categories));

        //Se inicia el adaptador
        rvCategories.setLayoutManager(l);
        rvCategories.addItemDecoration(dividerItemDecoration);
        rvCategories.setAdapter(categoryAdapter);

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
