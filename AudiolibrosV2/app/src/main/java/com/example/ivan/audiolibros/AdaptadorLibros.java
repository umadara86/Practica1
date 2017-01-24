package com.example.ivan.audiolibros;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

public class AdaptadorLibros extends RecyclerView.Adapter<AdaptadorLibros.ViewHolder> {
    private LayoutInflater inflador;
    private View.OnClickListener onClickListener;

    //Crea Layouts a partir del XML
     protected Vector<Libro> vectorLibros;

    // Vector con libros a visualizar
    private Context contexto;

    private View.OnLongClickListener onLongClickListener;

    public AdaptadorLibros(Context contexto, Vector<Libro> vectorLibros) {
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.vectorLibros = vectorLibros;
        this.contexto = contexto;
    }

    public void setOnItemLongClickListener(View.OnLongClickListener onItemLongClickListener) {
        this.onLongClickListener = onItemLongClickListener;
    }

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView portada;
        public TextView titulo;
        public ViewHolder(View itemView) {
            super(itemView);
            portada = (ImageView) itemView.findViewById(R.id.portada);
            portada.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
        }
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    // Creamos el ViewHolder con las vista de un elemento sin personalizar

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.elemento_selector, null);
        v.setOnClickListener(onClickListener);
        v.setOnLongClickListener(onLongClickListener);
        return new ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos

    @Override
    public void onBindViewHolder(final ViewHolder holder, int posicion) {
        Libro libro = vectorLibros.elementAt(posicion);
        holder.portada.setImageResource(libro.recursoImagen);
        holder.titulo.setText(libro.titulo);
    }


    // Indicamos el número de elementos de la lista

    @Override public int getItemCount() {
        return vectorLibros.size();
    }


}
