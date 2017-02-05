package com.example.ivan.audiolibros;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ivan on 28/1/17.
 */

public class LibroSharedPreferenceStorage implements LibroStorage{
    public static final String PREF_AUDIOLIBROS = "com.example.audiolibros_internal";
    public static final String KEY_ULTIMO_LIBRO = "ultimo";
    private static LibroSharedPreferenceStorage instance;
    private final Context context;


    private LibroSharedPreferenceStorage(Context context) {
        this.context = context;
    }

    public boolean hasLastBook() {
        return getPreference().contains("ultimo");
    }

    private SharedPreferences getPreference() {
        return context.getSharedPreferences(PREF_AUDIOLIBROS, MODE_PRIVATE);
    }

    public int getLastBook() {
        return getPreference().getInt(KEY_ULTIMO_LIBRO, -1);
    }


    public static LibroStorage getInstance(Context context) {
        if(instance == null) {
            instance = new LibroSharedPreferenceStorage(context);
        }
        return instance;
    }

    public int saveLastBook(int id) {
        SharedPreferences pref = context.getSharedPreferences( "com.example.audiolibros_internal", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("ultimo", id);
        editor.commit();

        return id;
    }
}
