package com.example.ivan.audiolibros;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.ivan.audiolibros.fragments.SelectorFragment;

import java.util.Vector;

/**
 * Created by Ivan on 31/1/17.
 */

public class OpenOptionsLongClickAction implements LongClickAction{
    public final MainActivity mainActivity;
    private RecyclerView recyclerView = null;
    private LayoutInflater inflador;
    private final AdaptadorLibrosFiltro adaptador;
    private final Vector<Libro> vectorLibros;
    private final SelectorFragment selectorFragment;

    public OpenOptionsLongClickAction(MainActivity mainActivity, RecyclerView recyclerView, AdaptadorLibrosFiltro adaptador, Vector<Libro> vectorLibros, SelectorFragment selectorFragment) {
        this.mainActivity = mainActivity;
        this.recyclerView = recyclerView;
        this.adaptador = adaptador;
        this.vectorLibros = vectorLibros;
        this.selectorFragment = selectorFragment;

        inflador = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public void execute(final View view) {

        final int id = recyclerView.getChildAdapterPosition(view);
        AlertDialog.Builder menu = new AlertDialog.Builder(mainActivity);
        CharSequence[] opciones = { "Compartir", "Borrar ", "Insertar" };
        menu.setItems(opciones, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int opcion) {
                switch (opcion) {
                    case 0: //Compartir
                        Snackbar.make(view,"¿Estás seguro que quieres compartir?", Snackbar.LENGTH_INDEFINITE).setAction("SI", new View.OnClickListener() {
                            @Override public void onClick(View v) {
                                Animation anim = AnimationUtils.loadAnimation(mainActivity, R.anim.agrandar);
                                anim.setAnimationListener(selectorFragment);
                                v.startAnimation(anim);
                                Libro libro = vectorLibros.elementAt(id);
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("text/plain");
                                i.putExtra(Intent.EXTRA_SUBJECT, libro.titulo);
                                i.putExtra(Intent.EXTRA_TEXT, libro.urlAudio);
                                selectorFragment.startActivity(Intent.createChooser(i, "Compartir"));
                                adaptador.notifyItemChanged(id);
                            } })
                                .show();

                        break;

                    case 1: //Borrar
                        Snackbar.make(view,"¿Estás seguro?", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                            @Override public void onClick(View v) {
                                Animation anim = AnimationUtils.loadAnimation(mainActivity, R.anim.menguar);
                                anim.setAnimationListener(selectorFragment);
                                v.startAnimation(anim);
                                adaptador.borrar(id);
                                adaptador.notifyItemRemoved(id);
                            } })
                                .show();
                        break;

                    case 2: //Insertar
                        final int posicion2 = recyclerView.getChildLayoutPosition(view);
                        Snackbar.make(view,"Libro insertado", Snackbar.LENGTH_INDEFINITE) .setAction("OK", new View.OnClickListener() {
                            @Override public void onClick(View v) {
                                adaptador.insertar((Libro) adaptador.getItem(posicion2));
                                adaptador.notifyItemInserted(0);
                            } })
                                .show();

                        break;
                }
            }
        });

        menu.create().show();

    }
}
