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

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.Vector;

public class AdaptadorLibros extends RecyclerView.Adapter<AdaptadorLibros.ViewHolder> {
    private LayoutInflater inflador;
    private ClickAction clickAction = new EmptyClickAction();
    private LongClickAction longClickAction = new EmptyOnLongClickAction();

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

    public void setClickAction(ClickAction clickAction) {
        this.clickAction = clickAction;
    }
    public void setLongClickAction(LongClickAction clickAction) {
        this.longClickAction = clickAction;
    }


    // Creamos el ViewHolder con las vista de un elemento sin personalizar

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.elemento_selector, null);
        //v.setOnLongClickListener(onLongClickListener);
        return new ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int posicion) {
        Libro libro = vectorLibros.elementAt(posicion);
        holder.titulo.setText(libro.titulo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                 clickAction.execute(posicion);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                longClickAction.execute(v);
                return false;
            }
        });
        Aplicacion aplicacion = (Aplicacion) contexto.getApplicationContext();
        aplicacion.getLectorImagenes().get(libro.urlImagen, new ImageLoader.ImageListener() {
            @Override public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bitmap = response.getBitmap();
                holder.portada.setImageBitmap(bitmap);
            }
            @Override public void onErrorResponse(VolleyError error) {
                holder.portada.setImageResource(R.drawable.books);
            }
        });
    }


    // Indicamos el número de elementos de la lista

    @Override public int getItemCount() {
        return vectorLibros.size();
    }


}
