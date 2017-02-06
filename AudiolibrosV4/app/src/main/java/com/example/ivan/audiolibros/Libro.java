package com.example.ivan.audiolibros;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Ivan on 3/1/17.
 */

public class Libro {
    public String titulo;
    public String autor;
    public String urlImagen;
    public String urlAudio;
    public String genero; // Género literario
    public Boolean novedad; // Es una novedad
    //public Boolean leido; // Leído por el usuario
    private Map<String, Boolean> leido;
    public final static String G_TODOS = "Todos los géneros";
    public final static String G_EPICO = "Poema épico";
    public final static String G_S_XIX = "Literatura siglo XIX";
    public final static String G_SUSPENSE = "Suspense";
    public final static String[] G_ARRAY = new String[] {G_TODOS, G_EPICO, G_S_XIX, G_SUSPENSE };
    public final static Libro LIBRO_EMPTY = new Libro("", "anónimo", "http://www.dcomg.upv.es/~jtomas/android/audiolibros/sin_portada.jpg", "", G_TODOS, true, false);
    private Libro(String titulo, String autor, String urlImagen, String urlAudio, String genero, Boolean novedad, boolean leido) {
        this.titulo = titulo;
        this.autor = autor;
        this.urlImagen = urlImagen;
        this.urlAudio = urlAudio;
        this.genero = genero;
        this.novedad = novedad;
        //this.leido = leido;
        this.leido = new HashMap<String, Boolean>();
    }

    public Libro(){}

    public static Vector<Libro> ejemploLibros() {
        final String SERVIDOR = "http://www.dcomg.upv.es/~jtomas/android/audiolibros/";
        Vector<Libro> libros = new Vector<Libro>();
        libros.add(new Libro("Kappa", "Akutagawa",
                SERVIDOR+"kappa.jpg", SERVIDOR+"kappa.mp3",
                Libro.G_S_XIX, false, false));
        libros.add(new Libro("Avecilla", "Alas Clarín, Leopoldo",
                SERVIDOR+"avecilla.jpg", SERVIDOR+"avecilla.mp3",
                Libro.G_S_XIX, true, false));
        libros.add(new Libro("Divina Comedia", "Dante",
                SERVIDOR+"divina_comedia.jpg", SERVIDOR+"divina_comedia.mp3",
                Libro.G_EPICO, true, false));
        libros.add(new Libro("Viejo Pancho, El", "Alonso y Trelles, José",
                SERVIDOR+"viejo_pancho.jpg", SERVIDOR+"viejo_pancho.mp3",
                Libro.G_S_XIX, true, true));
        libros.add(new Libro("Canción de Rolando", "Anónimo",
                SERVIDOR+"cancion_rolando.jpg", SERVIDOR+"cancion_rolando.mp3",
                Libro.G_EPICO, false, true));
        libros.add(new Libro("Matrimonio de sabuesos", "Agata Christie",
                SERVIDOR+"matrim_sabuesos.jpg",SERVIDOR+"matrim_sabuesos.mp3",
                Libro.G_SUSPENSE, false, true));
        libros.add(new Libro("La iliada", "Homero",
                SERVIDOR+"la_iliada.jpg", SERVIDOR+"la_iliada.mp3",
                Libro.G_EPICO, true, false));
        return libros;
    }

    public static class LibroBuilder {
        private String titulo = "";
        private String autor = "anónimo";
        private String urlImagen = "http://www.dcomg.upv.es/~jtomas/android/audiolibros/sin_portada.jpg";
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

        public LibroBuilder withUrlImagen(String urlImagen) {
            this.urlImagen = urlImagen;
            return this;
        }

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
            return new Libro(titulo, autor, urlImagen, urlAudio, genero, nuevo, leido);
        }
    }

    public boolean leidoPor(String userID) {
        if (this.leido != null) {
        return this.leido.keySet().contains(userID);
        } else {
        return false;
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Boolean getNovedad() {
        return novedad;
    }

    public void setNovedad(Boolean novedad) {
        this.novedad = novedad;
    }

    public Map<String, Boolean> getLeido() {
        return leido;
    }

    public void setLeido(Map<String, Boolean> leido) {
        this.leido = leido;
    }
}
