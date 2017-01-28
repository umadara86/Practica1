package com.example.ivan.audiolibros;

import android.app.Application;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ivan on 3/1/17.
 */

public class Aplicacion extends Application {

    private Vector<Libro> vectorLibros;
    //private AdaptadorLibros adaptador;
    private AdaptadorLibrosFiltro adaptador;



    @Override
    public void onCreate() {
        vectorLibros = Libro.ejemploLibros();
        //adaptador = new AdaptadorLibros (this, vectorLibros);
        adaptador = new AdaptadorLibrosFiltro (this, vectorLibros);
    }
    //public AdaptadorLibros getAdaptador() {return adaptador;}
    public AdaptadorLibrosFiltro getAdaptador() {

        return adaptador;
    }

    public Vector<Libro> getVectorLibros() {

        return vectorLibros;
    }

    public static AtomicBoolean getLectorImagenes() {

        return null;
    }
}
