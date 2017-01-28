package com.example.ivan.audiolibros;

import java.util.Vector;

/**
 * Created by Ivan on 3/1/17.
 */

public class Libro {
    public String titulo;
    public String autor;
    public int recursoImagen;
    public String urlAudio;
    public String genero; // Género literario
    public Boolean novedad; // Es una novedad
    public Boolean leido; // Leído por el usuario
    public final static String G_TODOS = "Todos los géneros";
    public final static String G_EPICO = "Poema épico";
    public final static String G_S_XIX = "Literatura siglo XIX";
    public final static String G_SUSPENSE = "Suspense";
    public final static String[] G_ARRAY = new String[] {G_TODOS, G_EPICO, G_S_XIX, G_SUSPENSE };
    public final static Libro LIBRO_EMPTY = new Libro("", "anónimo", R.drawable.sin_portada, "", G_TODOS, true, false);
    public Libro(String titulo, String autor, int recursoImagen, String urlAudio, String genero, Boolean novedad, Boolean leido) {
        this.titulo = titulo;
        this.autor = autor;
        this.recursoImagen = recursoImagen;
        this.urlAudio = urlAudio;
        this.genero = genero;
        this.novedad = novedad;
        this.leido = leido;
    }

    public static Vector<Libro> ejemploLibros() {
        final String SERVIDOR = "http://www.dcomg.upv.es/~jtomas/android/audiolibros/";
        Vector<Libro> libros = new Vector<Libro>();


        libros.add(new Libro("Kappa", "Akutagawa",
                R.drawable.kappa, SERVIDOR+"kappa.mp3",
                Libro.G_S_XIX, false, false));
        libros.add(new Libro("Avecilla", "Alas Clarín, Leopoldo",
                R.drawable.avecilla, SERVIDOR+"avecilla.mp3",
                Libro.G_S_XIX, true, false)); libros.add(new Libro("Divina Comedia", "Dante",
                R.drawable.divinacomedia, SERVIDOR+"divina_comedia.mp3",
                Libro.G_EPICO, true, false));
        libros.add(new Libro("Viejo Pancho, El", "Alonso y Trelles, José",
                R.drawable.viejo_pancho, SERVIDOR+"viejo_pancho.mp3",
                Libro.G_S_XIX, true, true));
        libros.add(new Libro("Canción de Rolando", "Anónimo",
                R.drawable.cancion_rolando, SERVIDOR+"cancion_rolando.mp3",
                Libro.G_EPICO, false, true));
        libros.add(new Libro("Matrimonio de sabuesos", "Agata Christie",
                R.drawable.matrimonio_sabuesos,SERVIDOR+"matrim_sabuesos.mp3",
                Libro.G_SUSPENSE, false, true));
        libros.add(new Libro("La iliada", "Homero",
                R.drawable.iliada, SERVIDOR+"la_iliada.mp3",
                Libro.G_EPICO, true, false));
        return libros;
    }

    public static class LibroBuilder {
        private String titulo = "";
        private String autor = "anónimo";
        public int recursoImagen;
        //private String urlImagen = "http://www.dcomg.upv.es/~jtomas/android/audiolibros/sin_portada.jpg";
        private String urlAudio = "";
        private String genero = G_TODOS;
        private boolean nuevo = true;
        private boolean leido = false;

        public LibroBuilder withTitulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public LibroBuilder withAutor(String autor) {
            this.autor = autor;
            return this;
        }

        public LibroBuilder withRecursoImagen(int recursoImagen) {
            this.recursoImagen = recursoImagen;
            return  this;
        }

        /*public LibroBuilder withUrlImagen(String urlImagen) {
            this.urlImagen = urlImagen;
            return this;
        }*/

        public LibroBuilder withUrlAudio(String urlAudio) {
            this.urlAudio = urlAudio;
            return this;
        }

        public LibroBuilder withGenero(String genero) {
            this.genero = genero;
            return this;
        }

        public LibroBuilder withNuevo(boolean nuevo) {
            this.nuevo = nuevo;
            return this;
        }

        public LibroBuilder withLeido(boolean leido) {
            this.leido = leido;
            return this;
        }

        public Libro build() {
            return new Libro(titulo, autor, recursoImagen, urlAudio, genero, nuevo, leido);
        }
    }
}
