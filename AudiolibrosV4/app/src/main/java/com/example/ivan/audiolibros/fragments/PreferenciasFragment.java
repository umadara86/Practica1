package com.example.ivan.audiolibros.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.ivan.audiolibros.R;


/**
 * Created by Ivan on 8/1/17.
 */

public class PreferenciasFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.preferences);

    }
}
