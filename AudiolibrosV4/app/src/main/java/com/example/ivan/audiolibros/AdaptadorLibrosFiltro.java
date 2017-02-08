package com.example.ivan.audiolibros;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by Ivan on 8/1/17.
 */

public class AdaptadorLibrosFiltro extends AdaptadorLibros implements Observer {
    //private Vector<Libro> vectorSinFiltro;
    private int librosUltimoFiltro; //Número libros del padre en último filtro
    private Vector<Integer> indiceFiltro;
    private String busqueda = "";
    private String genero = "";
    private boolean novedad = false;
    private boolean leido = false;

    public AdaptadorLibrosFiltro(Context contexto, DatabaseReference reference) {
        super(contexto, reference);
        recalculaFiltro();
    }
    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda.toLowerCase();
        recalculaFiltro();
    }

    public void setGenero(String genero) {
        this.genero = genero;
        recalculaFiltro();
    }

    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
        recalculaFiltro();
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
        recalculaFiltro();
    }

    public void recalculaFiltro() {
        //vectorLibros = new Vector<Libro>();
        indiceFiltro = new Vector<Integer>();
        librosUltimoFiltro = super.getItemCount();
        for (int i = 0; i < librosUltimoFiltro ; i++) {
            Libro libro = super.getItem(i);
            if ((libro.titulo.toLowerCase().contains(busqueda) ||
                    libro.autor.toLowerCase().contains(busqueda))
                    && (libro.genero.startsWith(genero))
                    && (!novedad || (novedad && libro.novedad)
                    && (!leido || (leido /*&& libro.leido*/)))){
                indiceFiltro.add(i);
            }
        }
    }

    public Libro getItem(int posicion) {

        if (librosUltimoFiltro != super.getItemCount()) {
            recalculaFiltro();
        }
        return super.getItem(indiceFiltro.elementAt(posicion));
    }

    public long getItemId(int posicion) {
        return indiceFiltro.elementAt(posicion);
    }

    public int getItemCount() {
        if (librosUltimoFiltro != super.getItemCount()) {
            recalculaFiltro();
        }
        return indiceFiltro.size();
    }

    public Libro getItemById(int id) {
        return super.getItem(id);
    }

    public void borrar(int posicion){
        DatabaseReference referencia=getRef(indiceFiltro.elementAt(posicion));
        referencia.removeValue();
        recalculaFiltro();
    }

    public void insertar(Libro libro){
        booksReference.push().setValue(libro);
        recalculaFiltro();
    }

    @Override
    public void update(Observable observable, Object data) {
        setBusqueda((String) data);
        notifyDataSetChanged();
    }
}