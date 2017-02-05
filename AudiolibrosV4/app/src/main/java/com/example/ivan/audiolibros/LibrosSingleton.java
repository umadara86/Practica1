package com.example.ivan.audiolibros;

import java.util.Vector;

/**
 * Created by Ivan on 4/2/17.
 */
public class LibrosSingleton {

    private Vector<Libro> vectorLibros;
    private AdaptadorLibrosFiltro adaptadorLibros;

    private static LibrosSingleton ourInstance = new LibrosSingleton();

    public static LibrosSingleton getInstance() {
        return ourInstance;
    }

    public void setVectorLibros(Vector<Libro> vectorLibros) {
        this.ourInstance.vectorLibros = vectorLibros;
    }

    public void setAdaptadorLibros(AdaptadorLibrosFiltro adaptadorLibros) {
        this.adaptadorLibros = adaptadorLibros;
    }

    public Vector<Libro> getVectorLibros() {
        return this.vectorLibros;
    }

    public AdaptadorLibrosFiltro getAdaptadorLibros() {
        return adaptadorLibros;
    }
}
