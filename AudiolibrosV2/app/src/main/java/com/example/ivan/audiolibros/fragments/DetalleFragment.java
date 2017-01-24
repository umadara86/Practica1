package com.example.ivan.audiolibros.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.audiolibros.Aplicacion;
import com.example.ivan.audiolibros.Libro;
import com.example.ivan.audiolibros.MainActivity;
import com.example.ivan.audiolibros.R;
import com.example.ivan.audiolibros.ZoomSeekBar;
import com.example.ivan.audiolibros.onConectarListener;

import java.io.IOException;

/**
 * Created by Ivan on 7/1/17.
 */

public class DetalleFragment extends Fragment implements View.OnTouchListener, MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl, onConectarListener {
    public static String ARG_ID_LIBRO = "id_libro";
    MediaPlayer mediaPlayer;
    MediaController mediaController;
    private View vista;


    @Override public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {

        View vista = inflador.inflate(R.layout.fragment_detalle, contenedor, false);
        this.vista=vista;
        ZoomSeekBar conectar1 = (ZoomSeekBar) vista.findViewById(R.id.seekbar1);
        ZoomSeekBar conectar2 = (ZoomSeekBar) vista.findViewById(R.id.seekbar2);
        conectar1.setOnConectarListener(this);
        conectar2.setOnConectarListener(this);

        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt(ARG_ID_LIBRO);
            ponInfoLibro(position, vista);
        } else {
            ponInfoLibro(0, vista);
        }
        return vista;
    }


    private void ponInfoLibro(int id, View vista) {
        Libro libro = ((Aplicacion) getActivity().getApplication()).getVectorLibros().elementAt(id);
        ((TextView) vista.findViewById(R.id.titulo)).setText(libro.titulo);
        ((TextView) vista.findViewById(R.id.autor)).setText(libro.autor);
        ((ImageView) vista.findViewById(R.id.portada)).setImageResource(libro.recursoImagen);
        vista.setOnTouchListener(this);
        if (mediaPlayer != null){
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaController = new MediaController(getActivity());
        Uri audio = Uri.parse(libro.urlAudio);
        try {
            mediaPlayer.setDataSource(getActivity(), audio);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("Audiolibros", "ERROR: No se puede reproducir "+audio,e);
        }
    }

    public void ponInfoLibro(int id) {
        ponInfoLibro(id, getView());
    }

    @Override public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("Audiolibros", "Entramos en onPrepared de MediaPlayer");
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (preferencias.getBoolean("pref_autoreproducir", true)) {
            mediaPlayer.start();
        }
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(getView().findViewById( R.id.fragment_detalle));
        mediaController.setEnabled(true);
        mediaController.show();
    }

    @Override public boolean onTouch(View vista, MotionEvent evento) {
        mediaController.show();
        return false;
    }

    @Override public void onStop() {
        mediaController.hide();
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        } catch (Exception e) {
            Log.d("Audiolibros", "Error en mediaPlayer.stop()");
        }
        super.onStop();
    }

    @Override public boolean canPause() {
        return true;
    }

    @Override public boolean canSeekBackward() {
        return true;
    }

    @Override public boolean canSeekForward() {
        return true;
    }

    @Override public int getBufferPercentage() {
        return 0;
    }

    @Override public int getCurrentPosition() {
        try {
        return mediaPlayer.getCurrentPosition();
        } catch (Exception e) {
        return 0;
        }
    }
    @Override public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override public void pause() {
        mediaPlayer.pause();
    }

    @Override public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override public void start() {
        mediaPlayer.start();
    }

    @Override public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onConectar(int valor) {
        //Toast.makeText(, "Conectando nuevo valor = " + valor, Toast.LENGTH_SHORT).show();
        Snackbar.make(vista, "conectando", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    @Override
    public void onConectado(String ip, int puerto) {

    }

    @Override
    public void onDesconectado() {
    }

    @Override
    public void onError(String mensage) {
        Snackbar.make(vista, "error", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override public void onResume(){
        DetalleFragment detalleFragment = (DetalleFragment) getFragmentManager().findFragmentById(R.id.detalle_fragment);
        if (detalleFragment == null ) {((MainActivity) getActivity()).mostrarElementos(false);
        }
        super.onResume();
    }


}
