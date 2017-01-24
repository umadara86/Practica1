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
import com.example.ivan.audiolibros.MainActivity;
import com.example.ivan.audiolibros.R;

import java.util.Vector;

/**
 * Created by Ivan on 7/1/17.
 */

public class SelectorFragment extends Fragment implements Animation.AnimationListener{

    private Activity actividad;
    private RecyclerView recyclerView;
    //private AdaptadorLibros adaptador;
    private AdaptadorLibrosFiltro adaptador;
    private Vector<Libro> vectorLibros;


    @Override public void onAttach(Activity actividad) {

        super.onAttach(actividad);
        this.actividad = actividad;
        Aplicacion app = (Aplicacion) actividad.getApplication();
        adaptador = app.getAdaptador();
        vectorLibros = app.getVectorLibros();
    }


    @Override public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_selector, contenedor, false);
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) vista.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(actividad,2));
        recyclerView.setAdapter(adaptador);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(2000);
        animator.setMoveDuration(2000);
        recyclerView.setItemAnimator(animator);

        adaptador.setOnItemClickListener(new View.OnClickListener() {

            @Override
                public void onClick(View v) {
                /*Toast.makeText(actividad, "Seleccionado el elemento: " +
                    recyclerView.getChildAdapterPosition(v), Toast.LENGTH_SHORT).show();*/
                //((MainActivity) actividad).mostrarDetalle(recyclerView.getChildAdapterPosition(v));

                ((MainActivity) actividad).mostrarDetalle((int) adaptador.getItemId(recyclerView.getChildAdapterPosition(v)));

                }
        });
        adaptador.setOnItemLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(final View v) {
            final int id = recyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder menu = new AlertDialog.Builder(actividad);
                CharSequence[] opciones = { "Compartir", "Borrar ", "Insertar" };
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int opcion) {
                    switch (opcion) {
                    case 0: //Compartir
                        Snackbar.make(v,"¿Estás seguro que quieres compartir?", Snackbar.LENGTH_LONG).setAction("SI", new View.OnClickListener() {
                            @Override public void onClick(View view) {
                                Animation anim = AnimationUtils.loadAnimation(actividad, R.anim.agrandar);
                                anim.setAnimationListener((Animation.AnimationListener) SelectorFragment.this);
                                v.startAnimation(anim);
                            } })
                                .show();
                        Libro libro = vectorLibros.elementAt(id);
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, libro.titulo);
                        i.putExtra(Intent.EXTRA_TEXT, libro.urlAudio);
                        startActivity(Intent.createChooser(i, "Compartir"));
                        break;
                    /*case 1: //Borrar
                        Snackbar.make(v,"¿Estás seguro?", Snackbar.LENGTH_LONG).setAction("SI", new View.OnClickListener() {
                            @Override
                                public void onClick(View view) {
                                //vectorLibros.remove(id);

                                adaptador.borrar(id);
                                adaptador.notifyDataSetChanged();
                                } })
                                .show();
                        break;*/
                        case 1: //Borrar
                            Snackbar.make(v,"¿Estás seguro?", Snackbar.LENGTH_LONG).setAction("SI", new View.OnClickListener() {
                                @Override public void onClick(View view) {
                                    Animation anim = AnimationUtils.loadAnimation(actividad, R.anim.menguar);
                                    anim.setAnimationListener((Animation.AnimationListener) SelectorFragment.this);
                                    v.startAnimation(anim);

                                    adaptador.borrar(id);
                                    //adaptador.notifyDataSetChanged();
                                } })
                                    .show();
                            break;

                    case 2: //Insertar
                        //vectorLibros.add(vectorLibros.elementAt(id));
                        int posicion = recyclerView.getChildLayoutPosition(v);
                        adaptador.insertar((Libro) adaptador.getItem(posicion));
                        //adaptador.notifyDataSetChanged();
                        adaptador.notifyItemInserted(0);
                        Snackbar.make(v,"Libro insertado", Snackbar.LENGTH_INDEFINITE) .setAction("OK", new View.OnClickListener() {
                            @Override public void onClick(View view) { } })
                                .show();
                        break;
                } }
            });

            menu.create().show();

            return true;
        } });

            return vista;

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        adaptador.notifyDataSetChanged();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // inflater.inflate(R.menu.menu_selector, menu);
       // super.onCreateOptionsMenu(menu, inflater);
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
