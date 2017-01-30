package com.example.ivan.audiolibros;

import android.app.Activity;
import android.os.Bundle;

import com.example.ivan.audiolibros.fragments.PreferenciasFragment;

/**
 * Created by Ivan on 8/1/17.
 */

public class PreferenciasActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenciasFragment()).commit();
    }
}