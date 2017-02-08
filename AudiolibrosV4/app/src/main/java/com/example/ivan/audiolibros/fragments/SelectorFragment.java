package com.example.ivan.audiolibros.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.ivan.audiolibros.AdaptadorLibros;
import com.example.ivan.audiolibros.AdaptadorLibrosFiltro;
import com.example.ivan.audiolibros.Aplicacion;
import com.example.ivan.audiolibros.Libro;
import com.example.ivan.audiolibros.LibrosSingleton;
import com.example.ivan.audiolibros.MainActivity;
import com.example.ivan.audiolibros.OpenDetailClickAction;
import com.example.ivan.audiolibros.OpenOptionsLongClickAction;
import com.example.ivan.audiolibros.R;
import com.example.ivan.audiolibros.SearchObservable;

import java.util.Vector;

/**
 * Created by Ivan on 7/1/17.
 */

public class SelectorFragment extends Fragment implements Animation.AnimationListener{

    private Activity actividad;
    private RecyclerView recyclerView;
    //private AdaptadorLibrosFiltro adaptador;
    //private Vector<Libro> vectorLibros;


    @Override public void onAttach(Activity actividad) {

        super.onAttach(actividad);
        this.actividad = actividad;
        //Aplicacion app = (Aplicacion) actividad.getApplication();
        //adaptador = LibrosSingleton.getInstance().getAdaptadorLibros();
        //vectorLibros = app.getVectorLibros();
    }


    @Override public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_selector, contenedor, false);
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) vista.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(actividad,2));
        recyclerView.setAdapter(LibrosSingleton.getInstance().getAdaptadorLibros());
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(2000);
        animator.setMoveDuration(2000);
        recyclerView.setItemAnimator(animator);
        LibrosSingleton.getInstance().getAdaptadorLibros().setClickAction(new OpenDetailClickAction((MainActivity) getActivity()));

        LibrosSingleton.getInstance().getAdaptadorLibros().setLongClickAction(new OpenOptionsLongClickAction((MainActivity) getActivity(),
                recyclerView, LibrosSingleton.getInstance().getAdaptadorLibros(), LibrosSingleton.getInstance().getAdaptadorLibros(), this));

            return vista;

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        LibrosSingleton.getInstance().getAdaptadorLibros().notifyDataSetChanged();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_ultimo) {
            ((MainActivity) actividad).irUltimoVisitado();
            return true;
        } else if (id == R.id.menu_buscar) {
            return true; }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onResume(){
        ((MainActivity) getActivity()).mostrarElementos(true);
        super.onResume();
    }
}
