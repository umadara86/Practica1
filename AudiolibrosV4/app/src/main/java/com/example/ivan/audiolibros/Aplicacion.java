package com.example.ivan.audiolibros;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by Ivan on 3/1/17.
 */

public class Aplicacion extends Application {

    //private Vector<Libro> vectorLibros;
    private static RequestQueue colaPeticiones;
    private static ImageLoader lectorImagenes;
    private FirebaseAuth auth;
    private final static String BOOKS_CHILD = "libros";
    private final static String USERS_CHILD = "usuarios";
    private DatabaseReference usersReference;



    @Override
    public void onCreate() {
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        DatabaseReference booksReference = database.getReference().child(BOOKS_CHILD);
        usersReference = database.getReference().child(USERS_CHILD);

        //Se inicializa el vector de libros haciendo uso de libro ejemplo libros dado que sino habria que añadir uno a uno todos los paramentros de cada Libro.
        LibrosSingleton.getInstance().setVectorLibros(Libro.ejemploLibros());
        LibrosSingleton.getInstance().setAdaptadorLibros(new AdaptadorLibrosFiltro (this,  LibrosSingleton.getInstance().getVectorLibros()));
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

    public FirebaseAuth getAuth() {
        return auth;
    }

    public DatabaseReference getUsersReference() {
        return usersReference;
    }

/*
    public AdaptadorLibrosFiltro getAdaptador() {

        return adaptador;
    }
*/
/*   public Vector<Libro> getVectorLibros() {

        return vectorLibros;
    }
*/
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
