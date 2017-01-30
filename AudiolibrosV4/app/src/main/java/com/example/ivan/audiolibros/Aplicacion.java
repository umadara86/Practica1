package com.example.ivan.audiolibros;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by Ivan on 3/1/17.
 */

public class Aplicacion extends Application {

    private Vector<Libro> vectorLibros;
    //private AdaptadorLibros adaptador;
    private AdaptadorLibrosFiltro adaptador;

    private static RequestQueue colaPeticiones;
    private static ImageLoader lectorImagenes;



    @Override
    public void onCreate() {
        vectorLibros = Libro.ejemploLibros();

        //adaptador = new AdaptadorLibros (this, vectorLibros);
        adaptador = new AdaptadorLibrosFiltro (this, vectorLibros);
        colaPeticiones = Volley.newRequestQueue(this);
        lectorImagenes = new ImageLoader(colaPeticiones, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
        });


    }
    //public AdaptadorLibros getAdaptador() {return adaptador;}
    public AdaptadorLibrosFiltro getAdaptador() {

        return adaptador;
    }

    public Vector<Libro> getVectorLibros() {

        return vectorLibros;
    }

    public static ImageLoader getLectorImagenes() {

        return lectorImagenes;
    }


    public static RequestQueue getColaPeticiones() {

        return colaPeticiones;
    }

    public static void setColaPeticiones(RequestQueue colaPeticiones) {
        Aplicacion.colaPeticiones = colaPeticiones;
    }

    public static void setLectorImagenes(ImageLoader lectorImagenes) {
        Aplicacion.lectorImagenes = lectorImagenes;
    }
}
